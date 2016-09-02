package org.ssa.ironyard.database.dao.customer;

import javax.sql.DataSource;

import com.mysql.cj.jdbc.MysqlDataSource;

public class DAOUtils
{
    
    static String DATABASE_URL = "jdbc:mysql://localhost/ssa_bank?user=root&password=root" + "&userServerPrepStmts=true";

    private DAOUtils()
    {
    }

    public static DataSource testDataSource()
    {
        MysqlDataSource dataSource = new MysqlDataSource();
        
        
        return dataSource;
    }
    
    
}
