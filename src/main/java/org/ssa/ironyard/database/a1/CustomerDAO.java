package org.ssa.ironyard.database.a1;

import java.util.List;

public interface CustomerDAO
{
    Customer insert(Customer customer);
    boolean delete(Customer toDelete);
    Customer update(Customer customer);
    Customer read(int id);
    List<Customer> read();
    List<Customer> readFirstName(String firstName);
    List<Customer> readLastName(String lastName);
    int clear();


}