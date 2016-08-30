package org.ssa.ironyard.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.mysql.cj.jdbc.MysqlDataSource;

public class CustomerDatabase implements CustomerDAO
{

    DataSource datasource;
    Connection connection;

    public CustomerDatabase(MysqlDataSource mysqlDataSource) throws SQLException
    {
        this.connection = mysqlDataSource.getConnection();
        this.datasource = mysqlDataSource;
    }

    public Customer insert(Customer customer)
    {
        try
        {
            PreparedStatement prepareStatement = this.connection
                    .prepareStatement("INSERT INTO customers(first, last) VALUE(?,?)", Statement.RETURN_GENERATED_KEYS);
            prepareStatement.setString(1, customer.getFirst());
            prepareStatement.setString(2, customer.getLast());
            prepareStatement.executeUpdate();
            ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
            generatedKeys.next();
            return new Customer(customer.getFirst(), customer.getLast(), generatedKeys.getInt(1));

        } catch (Exception e)
        {

        }
        return null;
    }

    public boolean delete(Customer toDelete)
    {
        try
        {
            PreparedStatement prepareStatement = this.connection
                    .prepareStatement("DELETE FROM customers WHERE first = ? AND last = ? AND id = ?");
            prepareStatement.setString(1, toDelete.getFirst());
            prepareStatement.setString(2, toDelete.getLast());
            prepareStatement.setInt(3, toDelete.getId());
            if (prepareStatement.executeUpdate() == 1)
                return true;
        } catch (Exception e)
        {
        }

        return false;
    }

    public Customer update(Customer customer)
    {
        try
        {
            PreparedStatement prepareStatement = this.connection
                    .prepareStatement("UPDATE customers SET first = ? AND last = ? WHERE id = ?");
            prepareStatement.setString(1, customer.getFirst());
            prepareStatement.setString(2, customer.getLast());
            prepareStatement.setInt(3, customer.getId());
            prepareStatement.executeUpdate();
        } catch (Exception e)
        {

        }
        return new Customer(customer.getFirst(), customer.getLast(), customer.getId());
    }

    public Customer read(int id)
    {
        try
        {
            PreparedStatement prepareStatement = this.connection
                    .prepareStatement("SELECT * FROM customers WHERE id = ?");
            prepareStatement.setInt(1, id);
            ResultSet results = prepareStatement.executeQuery();
            if (results.next())
                return new Customer(results.getString(2), results.getString(3), results.getInt(1));

        } catch (Exception e)
        {
        }
        return null;
    }

    public List<Customer> read()
    {
        List<Customer> customers = new ArrayList<Customer>();

        try
        {
            PreparedStatement prepareStatement = this.connection.prepareStatement("SELECT * FROM customers");
            ResultSet results = prepareStatement.executeQuery();
            while (results.next())
                customers
                        .add(new Customer(results.getString("first"), results.getString("last"), results.getInt("id")));

        } catch (Exception e)
        {
        }

        return customers;
    }

    public List<Customer> readFirstName(String firstName)
    {
        List<Customer> customers = new ArrayList<Customer>();

        try
        {
            PreparedStatement prepareStatement = this.connection
                    .prepareStatement("SELECT * FROM customers WHERE first = ?");
            prepareStatement.setString(1, firstName);
            ResultSet results = prepareStatement.executeQuery();
            while (results.next())
                customers
                        .add(new Customer(results.getString("first"), results.getString("last"), results.getInt("id")));

        } catch (Exception e)
        {
        }

        return customers;
    }

    public List<Customer> readLastName(String lastName)
    {

        List<Customer> customers = new ArrayList<Customer>();

        try
        {
            PreparedStatement prepareStatement = this.connection
                    .prepareStatement("SELECT * FROM customers WHERE last = ?");
            prepareStatement.setString(1, lastName);
            ResultSet results = prepareStatement.executeQuery();
            while (results.next())
                customers
                        .add(new Customer(results.getString("first"), results.getString("last"), results.getInt("id")));

        } catch (Exception e)
        {
        }

        return customers;
    }

    public int clear()
    {
        try
        {
            PreparedStatement prepareStatement = this.connection
                    .prepareStatement("DELETE FROM customers");
            return prepareStatement.executeUpdate();
        } catch (Exception e)
        {
        }
        return 0;
    }
}
