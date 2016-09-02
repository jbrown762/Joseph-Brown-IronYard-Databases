package org.ssa.ironyard.database.dao.account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.ssa.ironyard.database.model.DomainObject;

public abstract class AbstractDAO<T extends DomainObject>
{
    final DataSource datasource;
    final ORM<T> orm;

    protected AbstractDAO(DataSource datasource, ORM<T> orm)
    {
        this.datasource = datasource;
        this.orm = orm;
    }

    public abstract T insert(T domain);

    public boolean delete(int id)
    {

        Connection connection = null;
        PreparedStatement prepareStatement = null;
        try
        {
            connection = datasource.getConnection();
            prepareStatement = connection.prepareStatement(orm.prepareDelete());
            prepareStatement.setInt(1, id);
            if (prepareStatement.executeUpdate() == 1)
                return true;
        } catch (Exception e)
        {
        } finally
        {
            cleanup(connection, prepareStatement);
        }

        return false;
    }

    public abstract T update(T domain);

    public T read(int id)
    {
        Connection connection = null;
        PreparedStatement read = null;
        ResultSet results = null;
        try
        {
            connection = this.datasource.getConnection();
            read = connection.prepareStatement(this.orm.prepareRead());
            read.setInt(1, id);
            results = read.executeQuery();

            if (results.next())
                return this.orm.map(results);

        } catch (Exception ex)
        {

        } finally
        {
            cleanup(connection, read, results);
        }

        return null;
    }

    public int clear() throws UnsupportedOperationException
    {
        Connection conn = null;
        Statement clear = null;
        try
        {
            conn = this.datasource.getConnection();
            clear = conn.createStatement();
            return clear.executeUpdate("DELETE FROM " + this.orm.table() + ";");
        } catch (Exception e)
        {

        } finally
        {
            cleanup(conn, clear);
        }
        return 0;
    }

    static protected void cleanup(Connection c, Statement ps, ResultSet rs)
    {
        try
        {
            if (c != null)
                c.close();
            if (ps != null)
                ps.close();
            if (rs != null)
                rs.close();
        } catch (SQLException e)
        {
        }
    }

    static protected void cleanup(Connection c, Statement ps)
    {
        try
        {
            if (c != null)
                c.close();
            if (ps != null)
                ps.close();
        } catch (SQLException e)
        {
        }
    }

}
