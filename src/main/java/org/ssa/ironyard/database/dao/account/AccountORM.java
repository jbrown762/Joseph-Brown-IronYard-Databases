package org.ssa.ironyard.database.dao.account;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.ssa.ironyard.database.model.Account;
import org.ssa.ironyard.database.model.Customer;
import org.ssa.ironyard.database.model.Account.TYPE;

public interface AccountORM extends ORM<Account>
{

    @Override
    default String table()
    {
        return "accounts";
    }

    @Override
    default String prepareDelete()
    {
        return "DELETE FROM " + table() + " WHERE id = ?";
    }

    @Override
    default String prepareInsert()
    {
        return "INSERT INTO " + table() + "(customer, type, balance) VALUES(?, ?, ?)"; 
    }

    @Override
    default String prepareRead()
    {
        return "SELECT " + projection() + " FROM " + table() + " INNER JOIN customers ON accounts.customer=customers.id WHERE accounts.id = ?";
    }

    @Override
    default String projection()
    {
        return "account.id, customer, type, balance, first, last ";
    }

    @Override
    default String prepareUpdate()
    {
        return "UPDATE " + table() + " SET customer = ?, type = ?, balance = ? " +
               "WHERE id = ?";
    }

    @Override
    default Account map(ResultSet results) throws SQLException
    {
        Account account = new Account();
        account.setBalance(results.getBigDecimal("balance"));
        account.setId(results.getInt("id"));
        account.setType(TYPE.getInstance(results.getString("type")));
        Customer customer = new Customer();
        account.setCustomer(customer);
        customer.setId(results.getInt("customer"));
        customer.setFirst(results.getString("first"));
        customer.setLast(results.getString("last"));

        
        account.setLoaded(true);
        return account;
    }



}