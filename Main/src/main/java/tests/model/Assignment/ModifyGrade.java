package tests.model.Assignment;

import model.accounts.interfaces.IAccount;
import model.assignments.classes.Assignment;
import model.assignments.exceptions.BadGradeException;
import model.assignments.exceptions.NotCourseStudentException;
import model.assignments.exceptions.NotGradedException;
import model.courses.interfaces.IRoster;
import services.login.exceptions.NoPermissionException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import services.login.interfaces.ILoginToken;
import tests.StubFactory;
import tests.provider.AccountProvider;
import tests.provider.AssignmentProvider;
import tests.provider.LoginTokenProvider;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ModifyGrade
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
    void validRequester_gradeIsModified(ILoginToken validRequester)
    {
        Assignment assignment = AssignmentProvider.makeTestAssignment();
        IAccount student = AccountProvider.provider.provideSingle("student");
        float oldGrade = 10f;
        float newGrade = 20f;

        assignment.enterGrade(validRequester, student, oldGrade);
        //act
        assignment.modifyGrade(validRequester, student, newGrade);

        //assert
        assertEquals(newGrade, assignment.getGrade(validRequester, student));
    }


    @ParameterizedTest
    @MethodSource(names = "invalidRequester_provider")
    void invalidRequester_throwsNoPermissionException(ILoginToken invalidRequester)
    {
        Assignment assignment = AssignmentProvider.makeTestAssignment();
        IAccount student = AccountProvider.provider.provideSingle("student");
        ILoginToken validGrader = StubFactory.makeLoginToken("admin");
        float oldGrade = 10f;
        float newGrade = 20f;

        assignment.enterGrade(validGrader, student, oldGrade);
        //act && assert
        assertThrows(NoPermissionException.class, () -> assignment.modifyGrade(invalidRequester, student, newGrade));
    }

    @Test
    void studentNotInRoster_throwsNotAStudentException()
    {
        Assignment assignment = AssignmentProvider.makeTestAssignment();
        IAccount notInRosterStudent = AccountProvider.provider.provideSingle("notCourseStudent");
        // test
        ILoginToken admin = StubFactory.makeLoginToken("admin");
        IRoster roster = StubFactory.makeTestRoster();

        ILoginToken validRequester = StubFactory.makeLoginToken("admin");

        float newGrade = 20f;

        assertThrows(NotCourseStudentException.class, () -> assignment.modifyGrade(validRequester, notInRosterStudent, newGrade));

    }

    @Test
    void ungradedAssignment_throwsNotGradedException()
    {
        Assignment assignment = AssignmentProvider.makeTestAssignment();
        IAccount student = AccountProvider.provider.provideSingle("student");
        ILoginToken validRequester = StubFactory.makeLoginToken("admin");
        float newGrade = 20f;

        //act && assert
        assertThrows(NotGradedException.class, () -> assignment.modifyGrade(validRequester, student, newGrade));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1d, 101d})
    void badGrades_throwBadGradeException(double badGrade)
    {
        Assignment assignment = AssignmentProvider.makeTestAssignment();
        IAccount student = AccountProvider.provider.provideSingle("student");
        ILoginToken validRequester = StubFactory.makeLoginToken("admin");
        ILoginToken grader = StubFactory.makeLoginToken("admin");
        float grade = 10f;
        assignment.enterGrade(grader, student, grade);
        //act && assert
        assertThrows(BadGradeException.class, () -> assignment.modifyGrade(validRequester, student, (float) badGrade));
    }

}
