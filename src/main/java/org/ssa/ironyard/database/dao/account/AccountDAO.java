package org.ssa.ironyard.database.dao.account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.ssa.ironyard.database.model.Account;
import org.ssa.ironyard.database.model.Customer;

public class AccountDAO extends AbstractDAO<Account>
{

    public AccountDAO(DataSource datasource, ORM<Account> orm)
    {
        super(datasource, orm);
    }

    @Override
    public Account insert(Account domain)
    {
        Connection connection = null;
        PreparedStatement prepareStatement = null;
        ResultSet generatedKeys = null;
        try
        {
            connection = datasource.getConnection();

            prepareStatement = connection.prepareStatement(
                    orm.prepareInsert(), Statement.RETURN_GENERATED_KEYS);

            prepareStatement.setInt(1, domain.getCustomer().getId());
            prepareStatement.setString(2, domain.getType().abbrev);
            prepareStatement.setBigDecimal(3, domain.getBalance());

            if (prepareStatement.executeUpdate() > 0)
            {
                generatedKeys = prepareStatement.getGeneratedKeys();
                generatedKeys.next();
                
                {
                    Account a = new Account();
                    a.setCustomer(domain.getCustomer());
                    a.setBalance(domain.getBalance());
                    a.setId(generatedKeys.getInt(1));
                    a.setType(domain.getType());
                    a.setLoaded(true);
                    return a;
                }
            }
        } catch (Exception e)
        {
        } finally
        {
            cleanup(connection, prepareStatement, generatedKeys);
        }

        return null;
    }

    @Override
    public Account update(Account domain)
    {
        Connection connection;
        PreparedStatement prepareStatement;
        try
        {
            connection = datasource.getConnection();

            prepareStatement = connection.prepareStatement(orm.prepareUpdate());

            prepareStatement.setInt(1, domain.getCustomer().getId());
            prepareStatement.setString(2, domain.getType().abbrev);
            prepareStatement.setBigDecimal(3, domain.getBalance());
            prepareStatement.setInt(4, domain.getId());

            if (prepareStatement.executeUpdate() == 1)
            {

                Account a = new Account();
                a.setCustomer(domain.getCustomer());
                a.setBalance(domain.getBalance());
                a.setId(domain.getId());
                a.setType(domain.getType());
                a.setLoaded(true);
                return a;
            }
        } catch (Exception e)
        {
        }
        return null;
    }

    /**
     *
     * @param user
     * @return all the accounts for given user
     */
    public List<Account> readUser(int user)
    {
        Connection connection = null;
        PreparedStatement prepareStatement = null;
        ResultSet results = null;
        List<Account> accountsList = new ArrayList<>();
        try
        {
            connection = datasource.getConnection();
            prepareStatement = connection.prepareStatement(
                    "Select " + orm.projection() + " from " + orm.table() + " where customer=" + user);
            prepareStatement.setInt(1, user);
            results = prepareStatement.executeQuery();
            while (results.next())
                accountsList.add(orm.map(results));
        } catch (Exception e)
        {
        } finally
        {
            cleanup(connection, prepareStatement, results);
        }
        return accountsList;
    }

    /**
     *
     * @return those accounts that have a negative balance - never null
     */
    public List<Account> readUnderwater()
    {
        Connection connection = null;
        PreparedStatement prepareStatement = null;
        ResultSet results = null;
        List<Account> accountsList = new ArrayList<>();
        try
        {
            connection = datasource.getConnection();
            prepareStatement = connection
                    .prepareStatement("Select " + orm.projection() + " from " + orm.table() + " where balance < 0");
            results = prepareStatement.executeQuery();
            while (results.next())
                accountsList.add(orm.map(results));
        } catch (Exception e)
        {
        } finally
        {
            cleanup(connection, prepareStatement, results);
        }
        return accountsList;
    }

    /*
     * Implement a method readType that returns a List<Account> and takes in an argument indicating whether interested
     * in checking or savings accounts
     */
    public List<Account> readType(Account.TYPE type)
    {
        Connection connection = null;
        PreparedStatement prepareStatement = null;
        ResultSet results = null;
        List<Account> accounts = new ArrayList<>();
        try
        {
            connection = datasource.getConnection();
            prepareStatement = connection
                    .prepareStatement("Select " + orm.projection() + " from " + orm.table() + " where type=?");
            prepareStatement.setString(1, type.toString());
            results = prepareStatement.executeQuery();
            while (results.next())
                accounts.add(orm.map(results));
        } catch (Exception e)
        {
        } finally
        {
            cleanup(connection, prepareStatement, results);
        }
        return accounts;

    }
}