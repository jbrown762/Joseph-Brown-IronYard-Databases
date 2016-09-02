package org.ssa.ironyard.database.dao.customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.ssa.ironyard.database.model.Customer;

import com.mysql.cj.jdbc.MysqlDataSource;

public class CustomerDAOImpl implements CustomerDAO
{

    DataSource datasource;
    final CustomerORM orm = new CustomerORM()
    {
    };

    public CustomerDAOImpl(MysqlDataSource mysqlDataSource) throws SQLException
    {
        this.datasource = mysqlDataSource;
    }

    public Customer insert(Customer customer)
    {
        try
        {
            Connection connection = datasource.getConnection();

            PreparedStatement prepareStatement = connection.prepareStatement(orm.prepareInsert(),
                    Statement.RETURN_GENERATED_KEYS);
            prepareStatement.setString(1, customer.getFirst());
            prepareStatement.setString(2, customer.getLast());
            if (prepareStatement.executeUpdate() == 1)
            {
                ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
                if (generatedKeys.next())
                    return new Customer(customer.getFirst(), customer.getLast(), generatedKeys.getInt(1));
            }
        } catch (Exception e)
        {

        }
        return null;
    }

    public boolean delete(Customer toDelete)
    {
        try
        {
            Connection connection = datasource.getConnection();

            PreparedStatement prepareStatement = connection.prepareStatement(orm.prepareDelete());

            prepareStatement.setInt(1, toDelete.getId());
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
            Connection connection = datasource.getConnection();

            PreparedStatement prepareStatement = connection
                    .prepareStatement(orm.prepareUpdate());
            prepareStatement.setString(1, customer.getFirst());
            prepareStatement.setString(2, customer.getLast());
            prepareStatement.setInt(3, customer.getId());
            if (prepareStatement.executeUpdate() == 1)
                return new Customer(customer.getFirst(), customer.getLast(), customer.getId());
        } catch (Exception e)
        {
        }
        return null;
    }

    public Customer read(int id)
    {
        try
        {
            Connection connection = datasource.getConnection();

            PreparedStatement prepareStatement = connection.prepareStatement(orm.prepareRead());
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
            Connection connection = datasource.getConnection();

            PreparedStatement prepareStatement = connection.prepareStatement("SELECT first, last, id FROM customers");
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
            Connection connection = datasource.getConnection();

            PreparedStatement prepareStatement = connection.prepareStatement("SELECT * FROM customers WHERE first = ?");
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
            Connection connection = datasource.getConnection();

            PreparedStatement prepareStatement = connection.prepareStatement("SELECT * FROM customers WHERE last = ?");
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
            Connection connection = datasource.getConnection();

            PreparedStatement prepareStatement = connection.prepareStatement("DELETE FROM customers");
            return prepareStatement.executeUpdate();
        } catch (Exception e)
        {
        }
        return 0;
    }
}
