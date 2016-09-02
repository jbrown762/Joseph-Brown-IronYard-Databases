package org.ssa.ironyard.database.a1;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ssa.ironyard.database.dao.customer.CustomerDAO;
import org.ssa.ironyard.database.dao.customer.CustomerDAOImpl;
import org.ssa.ironyard.database.model.Customer;

import com.mysql.cj.jdbc.MysqlDataSource;

public class CustomerDatabaseTest
{
    CustomerDAO db;
    public String URL = "jdbc:mysql://localhost/ssa_bank?user=root&password=root" + "&userServerPrepStmts=true";

    @Before
    public void setupDB() throws SQLException
    {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUrl(URL);
        db = new CustomerDAOImpl(mysqlDataSource);
        db.clear();
    }

    @After
    public void teardown() throws SQLException
    {
        // db.clear();
    }


    @Test
    public void read() throws SQLException
    {
        Customer c1 = db.insert(new Customer("Thur", "Ston"));
        assertTrue(c1.getId() > 0);
        assertTrue(db.read(c1.getId()).getFirst().equals("Thur"));
        assertTrue(db.read(c1.getId()).getLast().equals("Ston"));
        System.out.println(db.read());

        assertTrue(db.delete(c1));
        assertEquals(null, db.read(c1.getId()));
    }

    @Test
    public void delete() throws SQLException
    {
        Customer c1 = db.insert(new Customer("Joe", "Brown"));
        assertTrue(db.delete(c1));
        assertEquals(null, db.read(c1.getId()));
    }

    @Test
    public void insert() throws SQLException
    {
        Customer c1 = db.insert(new Customer("Test", "Customer"));
        assertTrue(c1.getId() > 0);
        assertTrue(db.read(c1.getId()).getFirst().equals("Test"));
        assertTrue(db.read(c1.getId()).getLast().equals("Customer"));
    }

    @Test
    public void update() throws SQLException
    {
        Customer c1 = db.insert(new Customer("Test", "Customer"));
        assertTrue(c1.getId() > 0);
        Customer c1updated = db.update(new Customer("Updated", "Customer", c1.getId()));
        assertFalse(db.read(c1updated.getId()).equals(db.read(c1.getId())));
    }
}
