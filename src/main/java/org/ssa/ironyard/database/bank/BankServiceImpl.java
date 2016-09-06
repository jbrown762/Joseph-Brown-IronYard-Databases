package org.ssa.ironyard.database.bank;

import java.math.BigDecimal;

import org.ssa.ironyard.database.dao.account.AccountDAO;
import org.ssa.ironyard.database.model.Account;

public class BankServiceImpl implements BankService
{
    AccountDAO accounts;

    public BankServiceImpl(AccountDAO accounts)
    {
        this.accounts = accounts;
    }

    @Override
    public Account withdraw(int account, BigDecimal amount) throws IllegalArgumentException
    {
        Account a = accounts.read(account);
        if (a.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) > -1)
        {
            a.setBalance(a.getBalance().subtract(amount));
            return accounts.update(a);
        }
        throw new IllegalArgumentException("Overdraft");
    }

    @Override
    public Account deposit(int account, BigDecimal amount)
    {
        Account a = accounts.read(account);
        a.setBalance(a.getBalance().add(amount));
        a.setLoaded(true);
        return accounts.update(a);
    }

    @Override
    public boolean transfer(int from, int to, BigDecimal amount) throws IllegalArgumentException
    {
        if (withdraw(from, amount) != null && deposit(to, amount) != null)
            return true;
        throw new IllegalArgumentException("Exception");
    }
}
