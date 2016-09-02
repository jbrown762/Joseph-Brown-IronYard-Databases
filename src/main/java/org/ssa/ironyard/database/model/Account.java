package org.ssa.ironyard.database.model;

import java.math.BigDecimal;

public class Account implements DomainObject
{
    TYPE type;
    Customer customer;
    Integer id = 0;
    BigDecimal balance;
    boolean isLoaded;

    public TYPE getType()
    {
        return this.type;
    }

    public Customer getCustomer()
    {
        return this.customer;
    }

    @Override
    public Integer getId()
    {
        return this.id;
    }

    public BigDecimal getBalance()
    {
        return this.balance;
    }

    public void setType(TYPE type)
    {
        this.type = type;
    }

    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public void setBalance(BigDecimal bigDecimal)
    {
        this.balance = bigDecimal;
    }

    public void setLoaded(boolean isLoaded)
    {
        this.isLoaded = isLoaded;
    }

    public boolean isLoaded()
    {
        return isLoaded;
    }

    public enum TYPE
    {
        CHECKING("CH"), SAVINGS("SA");
        public final String abbrev;

        private TYPE(String abbrev)
        {
            this.abbrev = abbrev;
        }

        public static TYPE getInstance(String abbrev)
        {
            for (TYPE type : values())
            {
                if (type.abbrev.equals(abbrev))
                    return type;
            }
            return null;
        }
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((balance == null) ? 0 : balance.hashCode());
        result = prime * result + ((customer == null) ? 0 : customer.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        Account other = (Account) obj;
        if (balance == null)
        {
            if (other.balance != null)
                return false;
        } else if (!balance.equals(other.balance))
            return false;
        if (customer == null)
        {
            if (other.customer != null)
                return false;
        } else if (!customer.equals(other.customer))
            return false;
        if (id == null)
        {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (type != other.type)
            return false;
        return true;
    }

    @Override
    public boolean deeplyEquals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Account other = (Account) obj;

        if (balance == null)
        {
            if (other.balance != null)
                return false;
        } else if (!(balance.compareTo(other.balance) == 0))
            return false;
        if (customer == null)
        {
            if (other.customer != null)
                return false;
        } else if (!customer.equals(other.customer))
            return false;

        if (!id.equals(other.id))
            return false;

        if (type != other.type)
            return false;

        return true;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException
    {
        try
        {
            Account copy = (Account) super.clone();
            copy.setCustomer(this.customer.clone());
        } catch (Exception e)
        {

        }
        return null;
    }

    @Override
    public String toString()
    {
        return "Account [type=" + type + ", customer=" + customer + ", id=" + id + ", balance=" + balance + "]";
    }

}