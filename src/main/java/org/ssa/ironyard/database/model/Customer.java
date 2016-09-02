package org.ssa.ironyard.database.model;

public class Customer implements DomainObject
{

    String first;
    String last;
    int id;

    public Customer()
    {
        id = -1;
    }

    public Customer(String first, String last)
    {
        this.first = first;
        this.last = last;
        this.id = -1;
    }

    public Customer(String first, String last, int id)
    {
        this.first = first;
        this.last = last;
        this.id = id;
    }

    public String getFirst()
    {
        return first;
    }

    public String getLast()
    {
        return last;
    }

    public Integer getId()
    {
        return id;
    }

    public void setFirst(String first)
    {
        this.first = first;
    }

    public void setLast(String last)
    {
        this.last = last;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((first == null) ? 0 : first.hashCode());
        result = prime * result + id;
        result = prime * result + ((last == null) ? 0 : last.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Customer other = (Customer) obj;

        if (id != other.id)
            return false;

        return true;
    }
    
   

    @Override
    public boolean deeplyEquals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    Customer other = (Customer) obj;
    if (first == null) {
        if (other.first != null)
        return false;
    } else if (!first.equals(other.first))
        return false;
    if (id != other.id)
        return false;
    if (last == null) {
        if (other.last != null)
        return false;
    } else if (!last.equals(other.last))
        return false;
    return true;
    }

    
    @Override
    public String toString()
    {
        return "Customer [first=" + first + ", last=" + last + ", id=" + id + "]";
    }

    @Override
    public Customer clone() throws CloneNotSupportedException
    {
        try
        {
            Customer copy = (Customer) super.clone();
            return copy;
        } catch (CloneNotSupportedException ex)
        {
            return null;
        }
    }

}
