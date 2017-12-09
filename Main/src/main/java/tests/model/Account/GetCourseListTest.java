package tests.model.Account;

import model.accounts.classes.Account;
import model.accounts.enums.AccountType;
import model.accounts.interfaces.IAccount;
import model.courses.classes.Course;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import services.login.classes.LoginToken;
import services.login.exceptions.NoPermissionException;
import services.login.interfaces.ILoginToken;
import tests.provider.AccountProvider;
import tests.provider.LoginTokenProvider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GetCourseListTest
{
    static Iterator<ILoginToken> validRequesters()
    {
        return LoginTokenProvider.provider.provide("student account, admin");
    }

    @ParameterizedTest
    @MethodSource(names = "validRequesters")
    void valid_requester_returnsCourseList(ILoginToken token)
    {
        List<Course> courseList = new ArrayList<>();
        IAccount account = new Account("fName", "lName", "student account", AccountType.student, courseList);

        assertEquals(courseList, account.getCourseList(token));
    }

    static Iterator<ILoginToken> invalidRequesters()
    {
        return LoginTokenProvider.provider.provide("student", "professor", "ta");
    }

    @ParameterizedTest
    @MethodSource(names = "invalidRequesters")
    void invalid_requester_throwsNoPermissionException(ILoginToken token)
    {
        List<Course> courseList = new ArrayList<>();
        IAccount account = new Account("fName", "lName", "student account", AccountType.student, courseList);

        assertThrows(NoPermissionException.class, () -> account.getCourseList(token));
    }

    @Test
    void attemptsToModify_throwsUnsupportedOperationException()
    {
        List<Course> courseList = new ArrayList<>();
        Account owner = (Account) AccountProvider.student;
        ILoginToken ownerToken = new LoginToken(owner);

        assertThrows(UnsupportedOperationException.class, () ->
                owner.getCourseList(ownerToken).add(null));
    }
}
