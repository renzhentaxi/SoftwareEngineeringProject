package tests;

import model.accounts.classes.Account;
import model.accounts.enums.AccountType;
import model.courses.classes.Course;
import model.courses.interfaces.ICourse;

import java.util.List;

public class AccountBuilder implements IBuilder<Account>
{
    private String userName = "userName";
    private String lastName = "lastName";
    private String firstName = "firstName";
    private List<Course> courseList = null;
    private AccountType accountType = null;

    public AccountBuilder setFirstName(String firstName)
    {
        this.firstName = firstName;
        return this;
    }

    public AccountBuilder setLastName(String lastName)
    {
        this.lastName = lastName;
        return this;
    }


    public AccountBuilder setUserName(String userName)
    {
        this.userName = userName;
        return this;
    }

    public AccountBuilder setAccountType(AccountType accountType)
    {
        this.accountType = accountType;
        return this;
    }

    public AccountBuilder setCourseList(List<Course> courseList)
    {
        this.courseList = courseList;
        return this;
    }

    @Override
    public Account build()
    {
        return new Account(firstName, lastName, userName, accountType, courseList);
    }
}
