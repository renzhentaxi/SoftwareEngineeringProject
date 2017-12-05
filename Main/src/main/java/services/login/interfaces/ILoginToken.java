package services.login.interfaces;

import model.accounts.enums.AccountType;
import model.accounts.interfaces.IAccount;
import services.login.exceptions.InvalidLoginTokenException;
import services.login.exceptions.NotLoggedInException;

public interface ILoginToken {

    /**
     * @return the account type of the user who logged in
     * @throws InvalidLoginTokenException if this login token is invalid
     * @throws NotLoggedInException       when the requester is no longer logged in.
     */
    AccountType getAccountType();

    /**
     * @return the account of the user who logged in
     * @throws InvalidLoginTokenException if this login token is invalid
     * @throws NotLoggedInException       when the requester is no longer logged in.
     */
    IAccount getAccount();

    /**
     * whether the user is still logged in or not
     *
     * @return true if the user is still logged in, false otherwise
     * @throws InvalidLoginTokenException if this login token is invalid
     */
    boolean isLoggedIn();

    /**
     * whether the login was successful or not
     *
     * @return true if the user logs in, false otherwise
     */
    boolean isValid();

}
