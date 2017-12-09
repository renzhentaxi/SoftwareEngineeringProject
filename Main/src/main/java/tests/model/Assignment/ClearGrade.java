package tests.model.Assignment;

import model.accounts.interfaces.IAccount;
import model.assignments.classes.Assignment;
import model.assignments.exceptions.NotCourseStudentException;
import model.assignments.exceptions.NotGradedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import services.login.exceptions.NoPermissionException;
import services.login.interfaces.ILoginToken;
import tests.StubFactory;
import tests.provider.AccountProvider;
import tests.provider.AssignmentProvider;
import tests.provider.LoginTokenProvider;

import java.util.Iterator;


public class ClearGrade
{


    static Iterator<ILoginToken> validRequester_provider()
    {
        return LoginTokenProvider.provider.provide("professor", "admin");
    }

    static Iterator<ILoginToken> invalidRequester_provider()
    {
        return LoginTokenProvider.provider.provide("notCourseProfessor", "ta", "student");

    }

    @ParameterizedTest
    @MethodSource(names = "validRequester_provider")
    void validRequester_clearsGrade(ILoginToken validRequester)
    {
        IAccount student = AccountProvider.student;
        Assignment assignment = AssignmentProvider.provider.provideSingle("assign1");
        assignment.enterGrade(validRequester, student, 10);

        //act
        assignment.clearGrade(validRequester, student);

        //assert
        Assertions.assertFalse(assignment.isGraded(validRequester, student));
    }


    @ParameterizedTest
    @MethodSource(names = "invalidRequester_provider")
    void invalidRequester_throwsNoPermissionException(ILoginToken invalidRequester)
    {
        IAccount student = StubFactory.makeAccount("student");
        Assignment assignment = AssignmentProvider.makeTestAssignment();
        Assertions.assertThrows(NoPermissionException.class, () -> assignment.clearGrade(invalidRequester, student));
    }

    @Test
    void studentNotInRoster_throwsNotAStudentException()
    {
        ILoginToken admin = StubFactory.makeLoginToken("admin");
        IAccount notCourseStudent = StubFactory.makeAccount("notCourseStudent");
        Assignment assignment = AssignmentProvider.makeTestAssignment();

        Assertions.assertThrows(NotCourseStudentException.class, () -> assignment.clearGrade(admin, notCourseStudent));

    }

    @Test
    void ungradedAssignment_throwsNotGradedException()
    {

        ILoginToken admin = LoginTokenProvider.admin;
        IAccount student = AccountProvider.student;

        Assignment assignment = AssignmentProvider.makeTestAssignment();

        Assertions.assertThrows(NotGradedException.class, () -> assignment.clearGrade(admin, student));

    }
}
