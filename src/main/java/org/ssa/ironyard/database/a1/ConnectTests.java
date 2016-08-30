package org.ssa.ironyard.database.a1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mysql.cj.api.mysqla.result.Resultset;
import com.mysql.cj.jdbc.MysqlDataSource;

public class ConnectTests
{
    public String URL = "jdbc:mysql://localhost/ssa_bank?user=root&password=root";
    
    DataSource datasource;
    Connection connection;
    
    @Before
    public void setupDB() throws SQLException{
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUrl(URL);
        Connection connection = mysqlDataSource.getConnection();
        this.datasource = mysqlDataSource;
        this.connection = connection;
    }
    
    @After
    public void teardown() throws SQLException{
        this.connection.close();
    }
    
    @Test
    public void datasource() throws SQLException{
        
        Statement sql = this.connection.createStatement();
        
        ResultSet results = sql.executeQuery("SELECT * FROM customers WHERE id = 1");
        assertTrue("", results.next());
        
        assertEquals("", 1, results.getInt(1));
        assertEquals("", "John", results.getString(2));
        assertEquals("", "Doe", results.getString(3));
    }
    
    @Test
    public void prepare() throws SQLException{
       PreparedStatement prepareStatement = this.connection.prepareStatement("SELECT * FROM customers WHERE id = ?");
       prepareStatement.setInt(1, 1);
       ResultSet results = prepareStatement.executeQuery();
       
       assertTrue("", results.next());
       
       assertEquals("", 1, results.getInt(1));
       assertEquals("", "John", results.getString(2));
       assertEquals("", "Doe", results.getString(3));
    
    }
    
    //@Test
    public void create() throws SQLException{
        PreparedStatement prepareStatement = this.connection
                .prepareStatement("INSERT INTO customers(first, last) VALUE(?,?)", Statement.RETURN_GENERATED_KEYS);
        prepareStatement.setString(1, "Joe");
        prepareStatement.setString(2, "Brown");
        assertEquals(1, prepareStatement.executeUpdate());
        ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
        assertTrue("", generatedKeys.next());
        System.err.println("inserted customer with id " + generatedKeys.getInt(1));
    }
    
   // @Test
    public void update() throws SQLException{
        PreparedStatement prepareStatement = this.connection.prepareStatement("UPDATE customers SET first = ? WHERE id = ?");
        prepareStatement.setString(1, "THURSTON");
        prepareStatement.setInt(2, 10);
        assertEquals(1, prepareStatement.executeUpdate());
        //ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
       // assertTrue("", generatedKeys.next());
       // System.err.println("updated customer with id " + generatedKeys.getInt(8));
    }
    
    @Test
    public void delete() throws SQLException{
        PreparedStatement prepareStatement = this.connection.prepareStatement("DELETE FROM customers WHERE first = ? AND last = ?");
        prepareStatement.setString(1, "THURSTON");
        prepareStatement.setString(2, "Brown");
        assertEquals(1, prepareStatement.executeUpdate());
    }
    
}
