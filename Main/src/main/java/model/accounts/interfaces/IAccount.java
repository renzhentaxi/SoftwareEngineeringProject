package model.accounts.interfaces;

import model.accounts.enums.AccountType;
import model.courses.classes.Course;
import services.login.exceptions.NoPermissionException;
import services.login.interfaces.ILoginToken;

import java.util.List;

/**
 * represents an account in the system
 * Each account is uniquely identified by its userName
 */
public interface IAccount
{

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
     * @param requester the user who requests courseList. The requester needs to be either the owner of the account or an admin
     * @return a readonly list of courses
     * @throws NoPermissionException if the user who requested does not have the permission to get the course list
     */
    List<Course> getCourseList(ILoginToken requester);

    /**
     * returns the accountType of the account
     *
     * @return the accountType of the account
     */
    AccountType getAccountType();

}
