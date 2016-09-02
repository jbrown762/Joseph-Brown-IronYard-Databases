package org.ssa.ironyard.database.model;

public interface DomainObject
{

    default Integer getId()
    {
        return null;
    }

    default boolean isLoaded()
    {
        return false;
    }

    default boolean deeplyEquals(Object obj)
    {
        
        
        return false;

    }
}
