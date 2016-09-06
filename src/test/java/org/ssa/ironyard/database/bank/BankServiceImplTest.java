package org.ssa.ironyard.database.bank;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.ssa.ironyard.database.dao.account.AccountDAO;
import org.ssa.ironyard.database.dao.account.AccountORM;
import org.ssa.ironyard.database.dao.customer.CustomerDAOImpl;
import org.ssa.ironyard.database.model.Account;
import org.ssa.ironyard.database.model.Customer;

import com.mysql.cj.jdbc.MysqlDataSource;

public class BankServiceImplTest
{

    static String URL = "jdbc:mysql://localhost/ssa_bank?" + "user=root&password=root" + "&useServerPrepStmt=true";
    BankServiceImpl bank;
    AccountDAO accounts;
    CustomerDAOImpl customers;

    @Before
    public void setup() throws SQLException
    {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUrl(URL);
        accounts = new AccountDAO(mysqlDataSource, new AccountORM()
        {
        });
        bank = new BankServiceImpl(accounts);
        customers = new CustomerDAOImpl(mysqlDataSource);
        accounts.clear();
        customers.clear();
    }

    @Test
    public void withdraw()
    {
        Account a = new Account();
        a.setCustomer(customers.insert(new Customer("Dave", "E")));
        a.setType(Account.TYPE.SAVINGS);
        a.setBalance(new BigDecimal("1000"));
        assertTrue((a = accounts.insert(a)).isLoaded());

        a = bank.withdraw(a.getId(), new BigDecimal("100"));
        assertTrue(a.getBalance().equals(new BigDecimal("900.00")));

    }

    @Test(expected = IllegalArgumentException.class)
    public void overdraft()
    {
        Account a = new Account();
        a.setCustomer(customers.insert(new Customer("Joe", "Brown")));
        a.setType(Account.TYPE.SAVINGS);
        a.setBalance(new BigDecimal("1000"));
        assertTrue((a = accounts.insert(a)).isLoaded());

        a = bank.withdraw(a.getId(), new BigDecimal("10000"));
    }
    
    @Test
    public void deposit()
    {
        Account a = new Account();
        a.setCustomer(customers.insert(new Customer("Thur", "Ston")));
        a.setType(Account.TYPE.CHECKING);
        a.setBalance(new BigDecimal("500"));
        assertTrue((a = accounts.insert(a)).isLoaded());

        a = bank.deposit(a.getId(), new BigDecimal("1000"));
        assertTrue(a.getBalance().equals(new BigDecimal("1500.00")));

    }
    
    @Test
    public void transfer()
    {
        Account from = new Account();
        from.setCustomer(customers.insert(new Customer("Thur", "Ston")));
        from.setType(Account.TYPE.CHECKING);
        from.setBalance(new BigDecimal("1500"));
        assertTrue((from = accounts.insert(from)).isLoaded());
        
        Account to = new Account();
        to.setCustomer(customers.insert(new Customer("Thur", "Ston")));
        to.setType(Account.TYPE.CHECKING);
        to.setBalance(new BigDecimal("500"));
        assertTrue((to = accounts.insert(to)).isLoaded());

        assertTrue(bank.transfer(from.getId(), to.getId(), new BigDecimal("1000")));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void transferOverdraft()
    {
        Account from = new Account();
        from.setCustomer(customers.insert(new Customer("Thur", "Ston")));
        from.setType(Account.TYPE.CHECKING);
        from.setBalance(new BigDecimal("500"));
        assertTrue((from = accounts.insert(from)).isLoaded());
        
        Account to = new Account();
        to.setCustomer(customers.insert(new Customer("Thur", "Ston")));
        to.setType(Account.TYPE.CHECKING);
        to.setBalance(new BigDecimal("500"));
        assertTrue((to = accounts.insert(to)).isLoaded());

        bank.transfer(from.getId(), to.getId(), new BigDecimal("1000"));
    }
}
