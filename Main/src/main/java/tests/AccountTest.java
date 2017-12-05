package tests;

import model.accounts.Account;
import model.accounts.IAccount;
import model.accounts.enums.AccountType;
import model.courses.ICourse;
import model.exceptions.NoPermissionException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import services.login.ILoginToken;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AccountTest
{


    @ParameterizedTest(name = "As {arguments}")
    @EnumSource(value = AccountType.class, names = {"student", "ta", "professor"})
    void getCourseList_requestAsNonOwner_throwsNoPermissionException(AccountType requesterAccountType)
    {
        Account owner = makeAccount("owner", AccountType.student, null);
        Account hacker = makeAccount("hacker", requesterAccountType, null);

        ILoginToken stubNonOwnerToken = makeStubLoginToken(hacker);

        assertThrows(NoPermissionException.class, () ->
        {
            owner.getCourseList(stubNonOwnerToken);
        });

    }

    @Test
    void getCourseList_requestAsOwner_returnsList()
    {
        List<ICourse> courseList = makeDummyCourseList();
        Account owner = makeAccount("owner", AccountType.student, courseList);
        ILoginToken stubOwnerToken = makeStubLoginToken(owner);

        assertEquals(courseList, owner.getCourseList(stubOwnerToken));
    }

    @Test
    void getCourseList_requestAsAdmin_returnsList()
    {
        List<ICourse> courseList = makeDummyCourseList();
        Account owner = makeAccount("owner", AccountType.student, courseList);
        Account admin = makeAccount("admin", AccountType.admin, null);

        ILoginToken stubAdminToken = makeStubLoginToken(admin);

        assertEquals(courseList, owner.getCourseList(stubAdminToken));
    }

    @Test
    void getCourseList_attemptsToModify_throwsReadonlyException()
    {
        List<ICourse> courseList = makeDummyCourseList();
        Account owner = makeAccount("owner", AccountType.student, courseList);
        ILoginToken stubOwnerToken = makeStubLoginToken(owner);

        assertThrows(UnsupportedOperationException.class, () ->
                {
                    owner.getCourseList(stubOwnerToken).add(null);
                }
        );
    }

    private Account makeAccount(String userName, AccountType accountType, List<ICourse> courseList)
    {
        return new Account("firstName", "lastName", userName, accountType, courseList);
    }

    private ILoginToken makeStubLoginToken(IAccount account)
    {
        ILoginToken stubLoginToken = mock(ILoginToken.class);
        when(stubLoginToken.getAccount()).thenReturn(account);
        when(stubLoginToken.getAccountType()).thenReturn(account.getAccountType());

        return stubLoginToken;
    }

    private List<ICourse> makeDummyCourseList()
    {
        ICourse mockCourse = mock(ICourse.class);
        List<ICourse> courseList = new ArrayList<>();
        courseList.add(mockCourse);

        return courseList;
    }
}