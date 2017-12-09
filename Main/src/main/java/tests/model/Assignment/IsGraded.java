package tests.model.Assignment;

import model.accounts.interfaces.IAccount;
import model.assignments.classes.Assignment;
import model.assignments.exceptions.NotCourseStudentException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import services.login.exceptions.NoPermissionException;
import services.login.interfaces.ILoginToken;
import tests.StubFactory;
import tests.provider.AccountProvider;
import tests.provider.AssignmentProvider;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class IsGraded
{
    static Iterator<ILoginToken> validRequester_provider()
    {
        return StubFactory.makeLoginTokenProvider("admin", "ta", "professor");
    }

    static Iterator<ILoginToken> invalidRequester_provider()
    {
        return StubFactory.makeLoginTokenProvider("notCourseProfessor", "notCourseTa", "diffStudent");
    }

    @ParameterizedTest
    @MethodSource(names = "validRequester_provider")
    void validRequester_returnsResult(ILoginToken validRequester)
    {
        Assignment assignment = AssignmentProvider.makeTestAssignment();
        ILoginToken grader = StubFactory.makeLoginToken("admin");
        IAccount student = AccountProvider.provider.provideSingle("student");
        float grade = 20;

        assignment.enterGrade(grader, student, grade);

        assertTrue(assignment.isGraded(validRequester, student));
    }


    @ParameterizedTest
    @MethodSource(names = "invalidRequester_provider")
    void invalidRequester_throwsNoPermissionException(ILoginToken invalidRequester)
    {
        Assignment assignment = AssignmentProvider.makeTestAssignment();
        ILoginToken grader = StubFactory.makeLoginToken("admin");
        IAccount student = AccountProvider.provider.provideSingle("student");
        float grade = 20;

        assignment.enterGrade(grader, student, grade);

        assertThrows(NoPermissionException.class, () -> assignment.isGraded(invalidRequester, student));
    }

    @Test
    void studentNotInRoster_throwsNotAStudentException()
    {
        Assignment assignment = AssignmentProvider.makeTestAssignment();
        ILoginToken validRequester = StubFactory.makeLoginToken("admin");
        IAccount notCourseStudent = AccountProvider.provider.provideSingle("notCourseStudent");

        assertThrows(NotCourseStudentException.class, () -> assignment.isGraded(validRequester, notCourseStudent));
    }

    @Test
    void ungradedAssignment_returnsFalse()
    {
        Assignment assignment = AssignmentProvider.makeTestAssignment();
        ILoginToken validRequester = StubFactory.makeLoginToken("admin");
        IAccount student = AccountProvider.provider.provideSingle("student");
        IAccount student2 = AccountProvider.provider.provideSingle("student a");

        assertFalse(assignment.isGraded(validRequester, student));
        assertFalse(assignment.isGraded(validRequester, student2));

    }

    @Test
    void gradedAssignment_returnsTrue()
    {
        Assignment assignment = AssignmentProvider.makeTestAssignment();
        ILoginToken grader = StubFactory.makeLoginToken("admin");
        ILoginToken validRequester = StubFactory.makeLoginToken("admin");
        IAccount student = AccountProvider.provider.provideSingle("student");
        IAccount student2 = AccountProvider.provider.provideSingle("student a");
        float grade = 20;

        assignment.enterGrade(grader, student, grade);
        assignment.enterGrade(grader, student2, grade);

        assertTrue(assignment.isGraded(validRequester, student));
        assertTrue(assignment.isGraded(validRequester, student2));
    }
}
