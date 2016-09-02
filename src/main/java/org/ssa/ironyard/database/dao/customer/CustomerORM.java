package org.ssa.ironyard.database.dao.customer;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.ssa.ironyard.database.model.Customer;

public interface CustomerORM
{

    default String projection()
    {
        return "id, first, last";
    }

    default String table()
    {
        return "customers";
    }

    default Customer map(ResultSet results) throws SQLException
    {
        Customer c = new Customer();
        c.setFirst(results.getString("first"));
        c.setLast(results.getString("last"));
        c.setId(results.getInt("id"));
        return c;
    }

    default String prepareInsert()
    {
        return "INSERT INTO " + table() + "(first, last) VALUES(?, ?) ";
    }

    default String prepareUpdate()
    {
        return "UPDATE " + table() + " SET first = ? AND last = ? WHERE id = ?";
    }

    default String prepareRead(){
        return "SELECT " + projection() + " FROM " + table() + " WHERE id = ?";
    }

    default String prepareDelete(){
        return "DELETE FROM " + table() + " WHERE id = ?";
    }

}
