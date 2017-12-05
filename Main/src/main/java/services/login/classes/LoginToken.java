package services.login.classes;

import model.accounts.enums.AccountType;
import model.accounts.interfaces.IAccount;
import services.login.exceptions.InvalidLoginTokenException;
import services.login.exceptions.NotLoggedInException;
import services.login.interfaces.ILoginToken;

public class LoginToken implements ILoginToken
{
    /**
     * @return the account type of the user who logged in
     * @throws InvalidLoginTokenException if this login token is invalid
     * @throws NotLoggedInException       when the requester is no longer logged in.
     */
    @Override
    public AccountType getAccountType()
    {
        return null;
    }

    /**
     * @return the account of the user who logged in
     * @throws InvalidLoginTokenException if this login token is invalid
     * @throws NotLoggedInException       when the requester is no longer logged in.
     */
    @Override
    public IAccount getAccount()
    {
        return null;
    }

    /**
     * whether the user is still logged in or not
     *
     * @return true if the user is still logged in, false otherwise
     * @throws InvalidLoginTokenException if this login token is invalid
     */
    @Override
    public boolean isLoggedIn()
    {
        return false;
    }

    /**
     * whether the login was successful or not
     *
     * @return true if the user logs in, false otherwise
     */
    @Override
    public boolean isValid()
    {
        return false;
    }
}
