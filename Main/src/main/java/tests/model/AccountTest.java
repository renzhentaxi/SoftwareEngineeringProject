package tests.model;

import model.accounts.classes.Account;
import model.accounts.enums.AccountType;
import model.courses.classes.Course;
import model.courses.interfaces.ICourse;
import services.login.exceptions.NoPermissionException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import services.login.interfaces.ILoginToken;
import tests.provider.AccountProvider;

import javax.json.JsonObject;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static tests.StubFactory.makeDummyCourseList;
import static tests.StubFactory.makeStubLoginToken;

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
                owner.getCourseList(stubNonOwnerToken));
    }

    @Test
    void getCourseList_requestAsOwner_returnsList()
    {
        List<Course> courseList = makeDummyCourseList();
        Account owner = makeAccount("owner", AccountType.student, courseList);
        ILoginToken stubOwnerToken = makeStubLoginToken(owner);

        assertEquals(courseList, owner.getCourseList(stubOwnerToken));
    }

    @Test
    void getCourseList_requestAsAdmin_returnsList()
    {
        List<Course> courseList = makeDummyCourseList();
        Account owner = makeAccount("owner", AccountType.student, courseList);
        Account admin = makeAccount("admin", AccountType.admin, null);

        ILoginToken stubAdminToken = makeStubLoginToken(admin);

        assertEquals(courseList, owner.getCourseList(stubAdminToken));
    }

    @Test
    void getCourseList_attemptsToModify_throwsUnsupportedOperationException()
    {
        List<Course> courseList = makeDummyCourseList();
        Account owner = makeAccount("owner", AccountType.student, courseList);
        ILoginToken stubOwnerToken = makeStubLoginToken(owner);

        assertThrows(UnsupportedOperationException.class, () ->
                owner.getCourseList(stubOwnerToken).add(null));
    }

    private Account makeAccount(String userName, AccountType accountType, List<Course> courseList)
    {
        return new Account("firstName", "lastName", userName, accountType, courseList);
    }

    @Test
    void toJson()
    {
        Account a = (Account) AccountProvider.professor;

        JsonObject json = a.toJson();
        assertEquals("professor", json.getString("userName"));
        assertEquals(AccountType.professor, AccountType.valueOf(json.getString("accountType")));

    }
}