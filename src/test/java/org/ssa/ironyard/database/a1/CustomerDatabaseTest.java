package org.ssa.ironyard.database.a1;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ssa.ironyard.database.a1.Customer;
import org.ssa.ironyard.database.a1.CustomerDatabase;

import com.mysql.cj.jdbc.MysqlDataSource;

public class CustomerDatabaseTest
{
    CustomerDatabase db;
    public String URL = "jdbc:mysql://localhost/ssa_bank?user=root&password=root";

    @Before
    public void setupDB() throws SQLException{
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUrl(URL);
        db = new CustomerDatabase(mysqlDataSource);
    }
    
    @After
    public void teardown() throws SQLException{
        db.connection.close();
    }
    
    @Test
    public void test() throws SQLException
    {
        Customer thurston = db.insert(new Customer("Thur", "Ston"));
        
        assertEquals(new Customer("Thur", "Ston", 35), thurston);
        
        assertTrue(db.delete(new Customer("Joe", "Brown", 28)));
        assertEquals(null, db.read(28));
        
        assertFalse(db.delete(new Customer("Joe", "Brown", 30)));
        
        assertEquals(new Customer("J", "B", 13), db.read(13));
        
       System.out.println(db.read());
       System.out.println(db.readLastName("Ston"));
       System.out.println(db.readFirstName("Joe"));
       //db.clear();
    }

}
