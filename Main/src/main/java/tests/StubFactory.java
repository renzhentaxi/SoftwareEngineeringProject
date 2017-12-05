package tests;

import model.accounts.enums.AccountType;
import model.accounts.interfaces.IAccount;
import model.assignments.interfaces.IAssignment;
import model.courses.interfaces.ICourse;
import model.courses.interfaces.IRoster;
import org.mockito.ArgumentMatcher;
import services.login.interfaces.ILoginToken;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StubFactory
{

    public static ILoginToken makeStubAdminLoginToken()
    {
        return makeStubLoginToken("admin", AccountType.admin);
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

        ILoginToken stubLoginToken = mock(ILoginToken.class);
        when(stubLoginToken.getAccount()).thenReturn(account);
        when(stubLoginToken.getAccountType()).thenReturn(accountType);

        return stubLoginToken;
    }

    public static ILoginToken makeStubLoginToken(String userName, AccountType accountType)
    {
        IAccount stubAccount = makeStubAccount(userName, accountType);
        return makeStubLoginToken(stubAccount);
    }

    /**
     * valid requester = professor,ta,admin
     * IsProfessor returns true when userName = professor
     * IsStudent returns true when userName = student
     * IsTa returns true when userName = ta
     */
    public static IRoster makeStubRoster()
    {
        IRoster stubRoster = mock(IRoster.class);

        ArgumentMatcher<ILoginToken> validRequester = (argument) ->
        {
            AccountType type = argument.getAccountType();
            String name = argument.getAccount().getUserName();
            return (type.equals(AccountType.professor) && name.equals("professor")) || (type.equals(AccountType.ta) && name.equals("ta") || type.equals(AccountType.admin));
        };
        ArgumentMatcher<IAccount> studentAccount = argument -> argument.getUserName().equals("student");
        ArgumentMatcher<IAccount> professorAccount = argument -> argument.getUserName().equals("professor");
        ArgumentMatcher<IAccount> taAccount = argument -> argument.getUserName().equals("ta");

        when(stubRoster.isStudent(argThat(validRequester), argThat(studentAccount))).thenReturn(true);
        when(stubRoster.isProfessor(argThat(validRequester), argThat(professorAccount))).thenReturn(true);
        when(stubRoster.isTa(argThat(validRequester), argThat(taAccount))).thenReturn(true);

        return stubRoster;
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
        IAssignment mockAssignment = mock(IAssignment.class);
        List<IAssignment> assignmentList = new ArrayList<>();
        assignmentList.add(mockAssignment);
        return assignmentList;
    }
}
