package services.login.interfaces;

import model.accounts.classes.Account;
import model.accounts.enums.AccountType;
import services.login.exceptions.LoginExpiredException;

public interface ILoginToken
{

    /**
     * @return the account type of the user who logged in
     * @throws LoginExpiredException when the requester is no longer logged in.
     */
    AccountType getAccountType();

    /**
     * @return the account of the user who logged in
     * @throws LoginExpiredException when the requester is no longer logged in.
     */
    Account getAccount();

    /**
     * whether the user is still logged in or not
     *
     * @return true if the user is still logged in, false otherwise
     */
    boolean stillLoggedIn();
}
