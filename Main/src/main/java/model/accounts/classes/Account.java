package model.accounts.classes;

import model.accounts.enums.AccountType;
import model.accounts.interfaces.IAccount;
import model.courses.classes.Course;
import model.courses.interfaces.ICourse;
import services.login.exceptions.NoPermissionException;
import services.login.interfaces.ILoginToken;
import services.storage.interfaces.IJsonable;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Account implements IAccount, IJsonable, Comparable<IAccount>
{
    protected String firstName;
    protected String lastName;
    protected String userName;
    protected AccountType accountType;
    protected List<Course> courseList;

    public Account(String firstName, String lastName, String userName, AccountType accountType, List<Course> courseList)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.accountType = accountType;
        this.courseList = courseList;
    }

    /**
     * a default construct used by AccountInter to create an account object before all courses are loaded to the system.
     */
    protected Account()
    {
        courseList = new ArrayList<>();
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
    public List<Course> getCourseList(ILoginToken requester)
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

    @Override
    public int hashCode()
    {
        return userName.hashCode();
    }

    /**
     * converts an account object into a json object that is used by the storage services to store the account
     * @return
     */
    @Override
    public JsonObject toJson()
    {
        JsonArrayBuilder courseListBuilder = Json.createArrayBuilder();
        for (ICourse course : this.courseList)
        {
            courseListBuilder.add(course.getCourseName());
        }

        JsonArray courseList = courseListBuilder.build();
        return Json.createObjectBuilder()
                .add("userName", userName)
                .add("accountType", accountType.name())
                .add("firstName", firstName)
                .add("lastName", lastName)
                .add("courseList", courseList)
                .build();
    }

    @Override
    public String toString()
    {
        return accountType + ": " + userName;
    }


    @Override
    public int compareTo(IAccount iAccount)
    {
        return iAccount.getFirstName().compareTo(lastName);
    }
}
