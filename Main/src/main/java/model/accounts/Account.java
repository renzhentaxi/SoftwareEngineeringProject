package model.accounts;

import model.accounts.enums.AccountType;
import model.courses.ICourse;
import model.exceptions.NoPermissionException;
import services.login.ILoginToken;

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

    @Override
    public String getFirstName()
    {
        return firstName;
    }

    @Override
    public String getLastName()
    {
        return lastName;
    }

    @Override
    public String getUserName()
    {
        return userName;
    }

    @Override
    public List<ICourse> getCourseList(ILoginToken requester)
    {

        if (this.equals(requester.getAccount()) || requester.getAccountType() == AccountType.admin)
            return Collections.unmodifiableList(courseList);
        throw new NoPermissionException();
    }

    @Override
    public AccountType getAccountType()
    {
        return accountType;
    }

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
