package org.ssa.ironyard.database.dao.customer;

import java.util.List;

import org.ssa.ironyard.database.model.Customer;

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