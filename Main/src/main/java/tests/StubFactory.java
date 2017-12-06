package tests;

import model.accounts.enums.AccountType;
import model.accounts.interfaces.IAccount;
import model.assignments.interfaces.IAssignment;
import model.courses.classes.Roster;
import model.courses.interfaces.ICourse;
import services.login.interfaces.ILoginToken;
import tests.provider.AccountProvider;
import tests.provider.LoginTokenProvider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StubFactory
{
    /**
     * to make it easier to create ILoginToken stubs
     * this method will take an array of names and converts them into a list of tokens depending on the name
     * any name contains student will become student
     * any name contains teacher wll become teacher and etc...
     * when the name contains both student and teacher or another type, the first occurence will dictate the token's type
     *
     * @param names a list of names
     * @return a list of ILoginTokens
     */
    public static Iterator<ILoginToken> makeLoginTokenProvider(String... names)
    {
        List<ILoginToken> stubs = new ArrayList<>();
        for (String name : names)
        {
            stubs.add(makeLoginToken(name));
        }

        return stubs.iterator();
    }

    public static ILoginToken makeLoginToken(String name)
    {
        return LoginTokenProvider.provider.provideSingle(name);
    }

    public static IAccount makeAccount(String name)
    {
        name = name.toLowerCase();
        for (AccountType type : AccountType.values())
        {
            if (name.contains(type.name()))
            {
                return makeStubAccount(name, type);
            }
        }

        throw new RuntimeException("!!!! cant not figure out type");
    }

    /**
     * generates a stub account object that has getUserName, getAccountType methods
     *
     * @param userName    the userName that is returned by getUserName
     * @param accountType the accountType that is returned by getAccountType
     * @return a stub account object
     */
    public static IAccount makeStubAccount(String userName, AccountType accountType)
    {
        IAccount stubAccount = mock(IAccount.class);
        when(stubAccount.getAccountType()).thenReturn(accountType);
        when(stubAccount.getUserName()).thenReturn(userName);

        return stubAccount;
    }

    public static ILoginToken makeStubLoginToken(IAccount account)
    {
        AccountType accountType = account.getAccountType();
        String userName = account.getUserName();
        ILoginToken stubLoginToken = mock(ILoginToken.class);
        when(stubLoginToken.getAccount()).thenReturn(account);
        when(stubLoginToken.getAccountType()).thenReturn(accountType);
        when(stubLoginToken.toString()).thenReturn(userName + " : " + accountType.name());
        return stubLoginToken;
    }

    public static ILoginToken makeStubLoginToken(String userName, AccountType accountType)
    {
        IAccount stubAccount = makeStubAccount(userName, accountType);
        return makeStubLoginToken(stubAccount);
    }

    /**
     * valid requester = professor,ta,admin
     * IsProfessor returns true when userName != notCourseProfessor and type is professor
     * IsStudent returns true when userName != notCourseStudent and type is student
     * IsTa returns true when userName != notCourseTa and type is ta
     */
    public static Roster makeTestRoster()
    {
        Roster r = new Roster(AccountProvider.professor, AccountProvider.ta, AccountProvider.provider.provideMany("student, student a, student b, student c"));
        return r;
    }

    public static List<ICourse> makeDummyCourseList()
    {
        ICourse mockCourse = mock(ICourse.class);
        List<ICourse> courseList = new ArrayList<>();
        courseList.add(mockCourse);

        return courseList;
    }

    public static List<IAssignment> makeDummyAssignmentList()
    {
        List<IAssignment> assignmentList = new ArrayList<>();
        return assignmentList;
    }
}
