package services.login;

import services.login.exceptions.NotLoggedInException;

public interface ILoginManager {

    /**
     * logs in to the program
     * @param userName the user name of the user who wants to login
     * @param password the password of the user who wants to login
     * @return a loginToken that is used throughout the application to authenticate the user.
     * The loginToken will be invalid if an account with the userName does not exist or if the password does not match the user's password
     */
    ILoginToken login(String userName, String password);

    /**
     * logs the user out of the system.
     * @param userName the user name of the user who wants to logout
     * @throws NotLoggedInException if the user is already logged out
     */
    void logout(String userName);
}
