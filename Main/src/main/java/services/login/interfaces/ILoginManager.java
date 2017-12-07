package services.login.interfaces;

import services.login.classes.LoginToken;
import services.login.exceptions.AlreadyLoggedInException;
import services.login.exceptions.InvalidLoginException;
import services.login.exceptions.LoginExpiredException;

public interface ILoginManager {

    /**
     * logs in to the program
     * @param userName the user name of the user who wants to login
     * @param password the password of the user who wants to login
     * @return a loginToken that is used throughout the application to authenticate the user.
     * @throws InvalidLoginException if there is no account with the userName or if the userName+password does not match what is stored
     * @throws AlreadyLoggedInException if the account is already logged in
     */
    LoginToken login(String userName, String password);

    /**
     * logs the user out of the system.
     * @param userName the user name of the user who wants to logout
     * @throws LoginExpiredException if the user is already logged out
     */
    void logout(String userName);
}
