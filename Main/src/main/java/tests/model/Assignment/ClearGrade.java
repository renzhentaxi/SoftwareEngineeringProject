package tests.model.Assignment;

import model.accounts.interfaces.IAccount;
import model.assignments.classes.Assignment;
import model.assignments.exceptions.NotAStudentException;
import model.assignments.exceptions.NotGradedException;
import model.exceptions.NoPermissionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import services.login.interfaces.ILoginToken;
import tests.StubFactory;

import java.util.Iterator;


public class ClearGrade
{


    static Iterator<ILoginToken> validRequester_provider()
    {
        return StubFactory.makeLoginTokenProvider("professor", "admin");
    }

    static Iterator<ILoginToken> invalidRequester_provider()
    {
        return StubFactory.makeLoginTokenProvider("notCourseProfessor", "ta", "student");

    }

    @ParameterizedTest
    @MethodSource(names = "validRequester_provider")
    void validRequester_clearsGrade(ILoginToken validRequester)
    {
        IAccount student = StubFactory.makeAccount("student");
        Assignment assignment = AssignmentTestHelper.makeDefaultAssignment().build();
        assignment.enterGrade(validRequester,student,10);

        //act
        assignment.clearGrade(validRequester, student);

        //assert
        Assertions.assertFalse(assignment.isGraded(validRequester,student));
    }


    @ParameterizedTest
    @MethodSource(names = "invalidRequester_provider")
    void invalidRequester_throwsNoPermissionException(ILoginToken invalidRequester)
    {
        IAccount student = StubFactory.makeAccount("student");
        Assignment assignment = AssignmentTestHelper.makeDefaultAssignment().build();
        Assertions.assertThrows(NoPermissionException.class, () -> assignment.clearGrade(invalidRequester, student));
    }

    @Test
    void studentNotInRoster_throwsNotAStudentException()
    {
        ILoginToken admin = StubFactory.makeLoginToken("admin");
        IAccount notCourseStudent = StubFactory.makeAccount("notCourseStudent");
        Assignment assignment = AssignmentTestHelper.makeDefaultAssignment().build();

        Assertions.assertThrows(NotAStudentException.class, () -> assignment.clearGrade(admin, notCourseStudent));

    }

    @Test
    void ungradedAssignment_throwsNotGradedException()
    {

        ILoginToken admin = StubFactory.makeLoginToken("admin");
        IAccount student = StubFactory.makeAccount("student");
        Assignment assignment = AssignmentTestHelper.makeDefaultAssignment().build();

        Assertions.assertThrows(NotGradedException.class, () -> assignment.clearGrade(admin, student));

    }
}
