package services.login.classes;

import model.accounts.enums.AccountType;
import model.accounts.interfaces.IAccount;
import services.login.exceptions.LoginExpiredException;
import services.login.interfaces.ILoginToken;

public class LoginToken implements ILoginToken
{
    private IAccount account;
    private boolean isLoggedIn;

    public LoginToken(IAccount account)
    {
        this.account = account;
        isLoggedIn = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountType getAccountType()
    {
        if (!isLoggedIn) throw new LoginExpiredException();
        return account.getAccountType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IAccount getAccount()
    {
        if (!isLoggedIn) throw new LoginExpiredException();
        return account;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean stillLoggedIn()
    {
        return isLoggedIn;
    }


    void logout()
    {
        isLoggedIn = true;
    }

    @Override
    public int hashCode()
    {
        return account.hashCode();
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof LoginToken)
        {
            LoginToken other = (LoginToken) o;
            return other.account.equals(this.account);
        }
        return false;
    }
}
