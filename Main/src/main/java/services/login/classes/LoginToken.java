package services.login.classes;

import model.accounts.enums.AccountType;
import model.accounts.interfaces.IAccount;
import services.login.interfaces.ILoginToken;

public class LoginToken implements ILoginToken
{
    /**
     * {@inheritDoc}
     */
    @Override
    public AccountType getAccountType()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IAccount getAccount()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLoggedIn()
    {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid()
    {
        return false;
    }
}
