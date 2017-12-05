package tests.model.Assignment;

import model.accounts.interfaces.IAccount;
import model.assignments.classes.Assignment;
import model.assignments.exceptions.AlreadyGradedException;
import model.assignments.exceptions.BadGradeException;
import model.assignments.exceptions.NotAStudentException;
import model.exceptions.NoPermissionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import services.login.interfaces.ILoginToken;
import tests.StubFactory;

import java.util.Iterator;

@DisplayName(value = "Assignment.EnterGrade")
public class EnterGrade
{
    static Iterator<ILoginToken> validRequester_provider()
    {
        return StubFactory.makeLoginTokenProvider("professor", "admin");
    }

    static Iterator<ILoginToken> invalidRequester_provider()
    {
        return StubFactory.makeLoginTokenProvider("student", "ta", "notCourseProfessor");
    }

    @ParameterizedTest(name = "requesting as {arguments}")
    @MethodSource(names = "validRequester_provider")
    void validRequester_gradeEntered(ILoginToken validRequester)
    {
        Assignment assignment = AssignmentTestHelper.makeDefaultAssignment().build();
        IAccount student = StubFactory.makeAccount("student");
        float grade = 20f;
        //act
        assignment.enterGrade(validRequester, student, grade);

        //assert
        Assertions.assertEquals(grade, assignment.getGrade(validRequester, student));
    }


    @ParameterizedTest(name = "requesting as {arguments}")
    @MethodSource(names = "invalidRequester_provider")
    void invalidRequester_throwsNoPermissionException(ILoginToken invalidRequester)
    {
        Assignment assignment = AssignmentTestHelper.makeDefaultAssignment().build();
        IAccount student = StubFactory.makeAccount("student");
        float grade = 20f;

        //assert
        Assertions.assertThrows(NoPermissionException.class, () -> assignment.enterGrade(invalidRequester, student, grade));
    }

    @Test
    void studentNotInRoster_throwsNotAStudentException()
    {
        ILoginToken validRequester = StubFactory.makeLoginToken("admin");
        Assignment assignment = AssignmentTestHelper.makeDefaultAssignment().build();
        IAccount notAStudentOfThisCourse = StubFactory.makeAccount("notCourseStudent");
        float grade = 20f;

        //assert
        Assertions.assertThrows(NotAStudentException.class, () -> assignment.enterGrade(validRequester, notAStudentOfThisCourse, grade));
    }

    @Test
    void gradedAssignment_throwsAlreadyGradedException()
    {
        ILoginToken validRequester = StubFactory.makeLoginToken("admin");
        Assignment assignment = AssignmentTestHelper.makeDefaultAssignment().build();
        IAccount student = StubFactory.makeAccount("student");
        float grade = 20f;

        assignment.enterGrade(validRequester, student, grade);


        Assertions.assertThrows(AlreadyGradedException.class, () -> assignment.enterGrade(validRequester, student, grade));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1d, 101d})
    void badGrades_throwBadGrade(double badGrade)
    {
        ILoginToken validRequester = StubFactory.makeLoginToken("admin");
        Assignment assignment = AssignmentTestHelper.makeDefaultAssignment().build();
        IAccount student = StubFactory.makeAccount("student");

        Assertions.assertThrows(BadGradeException.class, () -> assignment.enterGrade(validRequester, student, (float)badGrade));
    }

}
