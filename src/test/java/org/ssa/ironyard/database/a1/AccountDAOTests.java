package org.ssa.ironyard.database.a1;

import java.math.BigDecimal;
import java.sql.SQLException;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.ssa.ironyard.database.dao.account.AccountDAO;
import org.ssa.ironyard.database.dao.account.AccountORM;
import org.ssa.ironyard.database.dao.customer.CustomerDAOImpl;
import org.ssa.ironyard.database.model.Account;
import org.ssa.ironyard.database.model.Account.TYPE;
import org.ssa.ironyard.database.model.Customer;

import com.mysql.cj.jdbc.MysqlDataSource;

public class AccountDAOTests
{
    static String URL = "jdbc:mysql://localhost/ssa_bank?" + "user=root&password=root" + "&useServerPrepStmt=true";
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
        customers = new CustomerDAOImpl(mysqlDataSource);
        accounts.clear();
        customers.clear();
    }

    @Test
    public void insert() throws SQLException
    {
        Account a = new Account();
        a.setCustomer(customers.insert(new Customer("Dave", "E")));
        a.setType(Account.TYPE.SAVINGS);
        a.setBalance(new BigDecimal("100000"));
        assertTrue((a = accounts.insert(a)).isLoaded());

        assertTrue(a.deeplyEquals(accounts.read(a.getId())));
        
        assertTrue(a.getId() > 0);
        assertTrue(a.getCustomer().getId().equals(accounts.read(a.getId()).getCustomer().getId()));
        assertTrue(a.getType().equals(accounts.read(a.getId()).getType()));
        assertTrue(a.getBalance().compareTo(accounts.read(a.getId()).getBalance()) == 0);
        
        assertNotNull(accounts.read(a.getId()));
    }

    @Test
    public void delete() throws SQLException
    {
        assertNull(accounts.read(5));

        Account a = new Account();
        a.setBalance(new BigDecimal("555.00"));
        a.setCustomer(customers.insert(new Customer("Dave", "E")));
        a.setType(Account.TYPE.CHECKING);
        a = accounts.insert(a);
        assertNotNull(accounts.read(a.getId()));
        assertEquals(a, accounts.read(a.getId()));
        accounts.delete(a.getId());
        assertNull(accounts.read(a.getId()));
    }

    @Test
    public void update() throws SQLException
    {
        Customer c = customers.insert(new Customer("Eli", "Real"));

        Account a = new Account();
        a.setBalance(new BigDecimal("1200.00"));
        a.setCustomer(customers.insert(c));
        a.setType(Account.TYPE.CHECKING);
        assertTrue((a = accounts.insert(a)).isLoaded());

        a.setBalance(new BigDecimal("800.00"));
        a.setType(TYPE.SAVINGS);
        assertTrue((a = accounts.update(a)).isLoaded());

        assertTrue(a.getBalance().compareTo(new BigDecimal("800.00")) == 0);
        assertTrue(accounts.read(a.getId()).getBalance().compareTo(new BigDecimal("800.00")) == 0);
        assertTrue(accounts.read(a.getId()).getType().equals(TYPE.SAVINGS));

    }

}
