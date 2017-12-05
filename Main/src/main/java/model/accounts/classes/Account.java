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
     * {@inheritDoc}
     */
    @Override
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLastName()
    {
        return lastName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUserName()
    {
        return userName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ICourse> getCourseList(ILoginToken requester)
    {
        if (this.equals(requester.getAccount()) || requester.getAccountType() == AccountType.admin)
            return Collections.unmodifiableList(courseList);
        throw new NoPermissionException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountType getAccountType()
    {
        return accountType;
    }

    /**
     * {@inheritDoc}
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
