package model.accounts.classes;

import model.accounts.enums.AccountType;
import model.accounts.interfaces.IAccount;
import model.courses.interfaces.ICourse;
import model.exceptions.NoPermissionException;
import services.login.interfaces.ILoginToken;

import java.util.Collections;
import java.util.List;

public class Account implements IAccount
{
    private String firstName;
    private String lastName;
    private String userName;
    private AccountType accountType;
    private List<ICourse> courseList;

    public Account(String firstName, String lastName, String userName, AccountType accountType, List<ICourse> courseList)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.accountType = accountType;
        this.courseList = courseList;
    }

    /**
     * returns the first name of the account
     *
     * @return the first name of the account
     */
    @Override
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * returns the last name of the account
     *
     * @return the last name of the account
     */
    @Override
    public String getLastName()
    {
        return lastName;
    }

    /**
     * returns the user name of the account
     *
     * @return the user name of the account
     */
    @Override
    public String getUserName()
    {
        return userName;
    }

    /**
     * returns a readonly list of the courses that are associated with the user
     *
     * @param requester the user who requests courseList
     * @return a readonly list of courses
     * @throws NoPermissionException if the user who requested does not have the permission to get the course list
     */
    @Override
    public List<ICourse> getCourseList(ILoginToken requester)
    {
        if (this.equals(requester.getAccount()) || requester.getAccountType() == AccountType.admin)
            return Collections.unmodifiableList(courseList);
        throw new NoPermissionException();
    }

    /**
     * returns the accountType of the account
     *
     * @return the accountType of the account
     */
    @Override
    public AccountType getAccountType()
    {
        return accountType;
    }

    /**
     * check if this account is equal to another object.
     *
     * @param o the other object
     * @return true if the other account has the same user name as this account, false otherwise
     */
    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Account)
        {
            Account other = (Account) o;
            return this.userName.equals(other.userName);
        }
        return false;
    }
}
