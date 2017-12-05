package tests.model.Assignment;

import model.accounts.enums.AccountType;
import model.accounts.interfaces.IAccount;
import model.assignments.classes.Assignment;
import model.assignments.exceptions.AlreadyGradedException;
import model.assignments.exceptions.NotAStudentException;
import model.courses.interfaces.ICourse;
import model.courses.interfaces.IRoster;
import model.exceptions.NoPermissionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import services.login.interfaces.ILoginToken;
import tests.StubFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName(value = "Assignment.EnterGrade")
public class EnterGrade
{
    static Iterator<ILoginToken> validRequester_provider()
    {
        return StubFactory.makeLoginTokenStubProvider("professor", "admin");
    }

    @ParameterizedTest(name = "requesting as {arguments}")
    @MethodSource(names = "validRequester_provider")
    void validRequester_gradeEntered(ILoginToken validRequester)
    {
        IRoster stubRoster = StubFactory.makeStubRoster();
        ICourse stubCourse = mock(ICourse.class);
        IAccount stubStudent = StubFactory.makeStubAccount("student", AccountType.student);

        when(stubCourse.getRoster(validRequester)).thenReturn(stubRoster);
        Map<IAccount, Float> grades = new HashMap<>();
        grades.put(stubStudent, -1f);

        Assignment assignment = new AssignmentBuilder()
                .setCourse(stubCourse)
                .setGrades(grades)
                .build();


        float grade = 20f;
        assignment.enterGrade(validRequester, stubStudent, grade);

        Assertions.assertEquals(grade, assignment.getGrade(validRequester, stubStudent));
    }


    static Iterator<ILoginToken> invalidRequester_provider()
    {
        return StubFactory.makeLoginTokenStubProvider("student", "ta", "notCourseProfessor");
    }

    @ParameterizedTest(name = "requesting as {arguments}")
    @MethodSource(names = "invalidRequester_provider")
    void invalidRequester_throwsNoPermissionException(ILoginToken invalidRequester)
    {
        IRoster stubRoster = StubFactory.makeStubRoster();
        ICourse stubCourse = mock(ICourse.class);
        when(stubCourse.getRoster(invalidRequester)).thenReturn(stubRoster);
        Assignment assignment = new AssignmentBuilder()
                .setCourse(stubCourse)
                .build();

        Assertions.assertThrows(NoPermissionException.class, () -> assignment.enterGrade(invalidRequester, null, 0));
    }

    @Test
    void studentNotInRoster_throwsNotAStudentException()
    {
        ILoginToken validRequester = StubFactory.makeLoginTokenStub("admin");

        IRoster stubRoster = StubFactory.makeStubRoster();
        ICourse stubCourse = mock(ICourse.class);
        when(stubCourse.getRoster(validRequester)).thenReturn(stubRoster);


        IAccount notAStudentOfThisCourse = StubFactory.makeStubAccount("not a student", AccountType.student);


        Assignment assignment = new AssignmentBuilder()
                .setCourse(stubCourse)
                .build();

        Assertions.assertThrows(NotAStudentException.class, ()-> assignment.enterGrade(validRequester, notAStudentOfThisCourse, 0f));
    }

    @Test
    void gradedAssignment_throwsAlreadyGradedException()
    {
        ILoginToken validRequester = StubFactory.makeLoginTokenStub("admin");

        IRoster stubRoster = StubFactory.makeStubRoster();
        ICourse stubCourse = mock(ICourse.class);
        when(stubCourse.getRoster(validRequester)).thenReturn(stubRoster);


        IAccount student = StubFactory.makeStubAccount("student", AccountType.student);


        Assignment assignment = new AssignmentBuilder()
                .setCourse(stubCourse)
                .build();
        assignment.enterGrade(validRequester,student,20);


        Assertions.assertThrows(AlreadyGradedException.class, ()-> assignment.enterGrade(validRequester, student, 0f));
    }
}
