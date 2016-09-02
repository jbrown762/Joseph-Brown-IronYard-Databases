package org.ssa.ironyard.database.dao.account;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.ssa.ironyard.database.model.DomainObject;

public interface ORM<T extends DomainObject>
{
    String projection();

    String table();

    T map(ResultSet results) throws SQLException;

    String prepareInsert();

    String prepareUpdate();

    String prepareRead();

    String prepareDelete();

}