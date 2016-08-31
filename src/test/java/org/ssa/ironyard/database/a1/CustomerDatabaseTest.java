package org.ssa.ironyard.database.a1;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ssa.ironyard.database.a1.Customer;

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

    // @Test
    public void test() throws SQLException
    {
        Customer thurston = db.insert(new Customer("Thur", "Ston"));

        assertEquals(new Customer("Thur", "Ston", 35), thurston);

        assertTrue(db.delete(new Customer("Joe", "Brown", 28)));
        assertEquals(null, db.read(28));

        assertFalse(db.delete(new Customer("Joe", "Brown", 30)));

        assertEquals(new Customer("J", "B", 13), db.read(13));

        assertEquals(19, db.read().size());
        assertEquals(2, db.readLastName("Ston").size());
        assertEquals(16, db.readFirstName("Joe").size());

        System.out.println(db.read());
        System.out.println(db.readLastName("Ston"));
        System.out.println(db.readFirstName("Joe"));

        // !!! UNCOMMENT TO CLEAR DATABASE !!!
        //
        // db.clear();
        // assertEquals(0, db.read().size());

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
        //assertFalse(db.read(c1updated.getId()).equals(db.read(c1.getId())));
    }
}
