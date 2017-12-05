package model.accounts;

import services.login.ILoginToken;
import model.courses.ICourse;
import model.accounts.enums.AccountType;

import java.util.List;

public interface IAccount {

    /**
     * returns the first name of the account
     *
     * @return the first name of the account
     */
    String getFirstName();

    /**
     * returns the last name of the account
     *
     * @return the last name of the account
     */
    String getLastName();

    /**
     * returns the user name of the account
     *
     * @return the user name of the account
     */
    String getUserName();

    /**
     * returns a readonly list of the courses that are associated with the user
     *
     * @param requester the user who requests courselist
     * @return a readonly list of courses
     * @throws model.exceptions.NoPermissionException if the user who requested does not have the permission to get the course list
     */
    List<ICourse> getCourseList(ILoginToken requester);

    /**
     * returns the accountType of the account
     *
     * @return the accountType of the account
     */
    AccountType getAccountType();

}
