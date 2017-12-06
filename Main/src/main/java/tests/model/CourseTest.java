package tests.model;

import model.accounts.enums.AccountType;
import model.accounts.interfaces.IAccount;
import model.assignments.classes.Assignment;
import model.assignments.interfaces.IAssignment;
import model.courses.classes.Course;
import model.courses.classes.Roster;
import model.courses.exceptions.AssignmentAlreadyExistException;
import model.courses.exceptions.AssignmentDoesNotExistException;
import model.courses.exceptions.GradedAssignmentException;
import services.login.exceptions.NoPermissionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import services.login.interfaces.ILoginToken;
import tests.StubFactory;
import tests.provider.AccountProvider;
import tests.provider.LoginTokenProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.mockito.Mockito.mock;

class CourseTest
{
    static Iterator<ILoginToken> getAssignments_validRequester_provider()
    {
        return LoginTokenProvider.provider.provide("admin,student, professor, ta");
    }

    @ParameterizedTest
    @MethodSource(names = "getAssignments_validRequester_provider")
    void getAssignments_validRequester_returnsAssignments(ILoginToken validRequester)
    {
        List<IAssignment> assignmentList = StubFactory.makeDummyAssignmentList();

        Course course = new Course("soft", StubFactory.makeTestRoster(), assignmentList);

        List<IAssignment> actual = course.getAssignments(validRequester);

        Assertions.assertEquals(assignmentList, actual);
    }

    static Iterator<ILoginToken> getAssignments_invalidRequester_provider()
    {
        ILoginToken notCourseStudent = StubFactory.makeStubLoginToken("notCourseStudent", AccountType.student);
        ILoginToken notCourseProfessor = StubFactory.makeStubLoginToken("notCourseProfessor", AccountType.professor);
        ILoginToken notCourseTa = StubFactory.makeStubLoginToken("notCourseTa", AccountType.ta);

        List<ILoginToken> invalidRequesters = new ArrayList<>();
        invalidRequesters.add(notCourseProfessor);
        invalidRequesters.add(notCourseStudent);
        invalidRequesters.add(notCourseTa);

        return invalidRequesters.iterator();
    }

    @ParameterizedTest
    @MethodSource(names = "getAssignments_invalidRequester_provider")
    void getAssignments_invalidRequester_throwsNoPermissionException(ILoginToken invalidRequester)
    {
        Course course = new Course("soft", StubFactory.makeTestRoster(), null);

        Assertions.assertThrows(NoPermissionException.class, () -> course.getAssignments(invalidRequester));
    }

    @Test
    void getAssignments_attemptsToModify_throwsUnsupportedOperation()
    {
        Course course = new Course("soft", StubFactory.makeTestRoster(), StubFactory.makeDummyAssignmentList());

        ILoginToken admin = StubFactory.makeStubLoginToken("admin", AccountType.admin);

        List<IAssignment> assignmentList = course.getAssignments(admin);

        Assertions.assertThrows(UnsupportedOperationException.class, () -> assignmentList.add(null));

    }

    static Iterator<ILoginToken> addAssignments_validRequester_provider()
    {
        return LoginTokenProvider.provider.provide("admin,professor");
    }

    @ParameterizedTest
    @MethodSource(names = "addAssignments_validRequester_provider")
    void addAssignment_validRequester_assignmentIsAdded(ILoginToken validRequester)
    {
        Course course = new Course("soft", StubFactory.makeTestRoster(), StubFactory.makeDummyAssignmentList());
        Assignment assignment = new Assignment("assignment", "", course);
        course.addAssignment(validRequester, assignment);
        Assertions.assertTrue(course.getAssignments(validRequester).contains(assignment));
    }

    static Iterator<ILoginToken> addAssignment_invalidRequester_provider()
    {
        ILoginToken notCourseProfessor = StubFactory.makeStubLoginToken("notCourseProfessor", AccountType.professor);
        ILoginToken student = StubFactory.makeStubLoginToken("student", AccountType.student);
        ILoginToken ta = StubFactory.makeStubLoginToken("ta", AccountType.ta);

        return Arrays.asList(new ILoginToken[]{notCourseProfessor, student, ta}).iterator();
    }

    @ParameterizedTest
    @MethodSource(names = "addAssignment_invalidRequester_provider")
    void addAssignment_invalidRequester_throwsNoPermissionException(ILoginToken invalidRequester)
    {
        Course course = new Course("soft", StubFactory.makeTestRoster(), null);

        Assertions.assertThrows(NoPermissionException.class, () -> course.addAssignment(invalidRequester, null));
    }

    //addAssignment functionality tests
    @Test
    void addAssignment_assignmentAlreadyExist_throwsAssignmentAlreadyExistException()
    {
        Course course = new Course("soft", StubFactory.makeTestRoster(), StubFactory.makeDummyAssignmentList());

        ILoginToken admin = StubFactory.makeStubLoginToken("admin", AccountType.admin);
        IAssignment assignment = new Assignment("assignment", "", course);

        Assertions.assertThrows(AssignmentAlreadyExistException.class, () ->
        {
            course.addAssignment(admin, assignment);
            course.addAssignment(admin, assignment);
        });
    }


    static Iterator<ILoginToken> removeAssignment_validRequester_provider()
    {
        ILoginToken admin = StubFactory.makeStubLoginToken("admin", AccountType.admin);
        ILoginToken professor = StubFactory.makeStubLoginToken("professor", AccountType.professor);

        return LoginTokenProvider.provider.provide("professor,admin");
    }

    @ParameterizedTest
    @MethodSource(names = "removeAssignment_validRequester_provider")
    void removeAssignment_validRequester_assignmentIsRemoved(ILoginToken validRequester)
    {
        Course course = new Course("soft", StubFactory.makeTestRoster(), StubFactory.makeDummyAssignmentList());

        IAssignment assignmentToRemove = new Assignment("assignment", "", course);
        course.addAssignment(validRequester, assignmentToRemove);
        course.removeAssignment(validRequester, assignmentToRemove);

        List<IAssignment> assignmentList = course.getAssignments(validRequester);

        Assertions.assertFalse(assignmentList.contains(assignmentToRemove));
    }

    static Iterator<ILoginToken> removeAssignment_invalidRequester_provider()
    {
        ILoginToken notCourseProfessor = StubFactory.makeStubLoginToken("notCourseProfessor", AccountType.professor);
        ILoginToken student = StubFactory.makeStubLoginToken("student", AccountType.student);
        ILoginToken ta = StubFactory.makeStubLoginToken("ta", AccountType.ta);

        return Arrays.asList(new ILoginToken[]{notCourseProfessor, student, ta}).iterator();
    }

    @ParameterizedTest
    @MethodSource(names = "removeAssignment_invalidRequester_provider")
    void removeAssignment_invalidRequester_throwsNoPermissionException(ILoginToken invalidRequester)
    {
        Course course = new Course("soft", StubFactory.makeTestRoster(), null);

        Assertions.assertThrows(NoPermissionException.class, () -> course.removeAssignment(invalidRequester, null));
    }

    //removeAssignment functionality tests
    @Test
    void removeAssignment_assignmentDoesNotExist_throwsAssignmentDoesNotExistException()
    {
        ILoginToken admin = StubFactory.makeLoginToken("admin");

        Course course = new Course("soft", StubFactory.makeTestRoster(), new ArrayList<>());

        Assertions.assertThrows(AssignmentDoesNotExistException.class, () -> course.removeAssignment(admin, mock(IAssignment.class)));
    }

    @Test
    void removeAssignment_assignmentIsGraded_throwsGradedAssignmentException()
    {
        ILoginToken admin = StubFactory.makeLoginToken("admin");
        IAccount student = AccountProvider.provider.provideSingle("student");
        Course course = new Course("soft", StubFactory.makeTestRoster(), new ArrayList<>());

        IAssignment graded = new Assignment("assignment", "", course);
        course.addAssignment(admin,graded);
        graded.enterGrade(admin,student,4f);

        Assertions.assertThrows(GradedAssignmentException.class, () -> course.removeAssignment(admin, graded));
    }


    @ParameterizedTest
    @MethodSource(names = "getRosterValidRequesterProvider")
    void getRoster_requesterHasPermission_rosterIsReturned(ILoginToken validRequester)
    {
        Roster roster = StubFactory.makeTestRoster();
        Course course = new Course("software", roster, null);
        Assertions.assertEquals(roster, course.getRoster(validRequester));
    }

    static Iterator<ILoginToken> getRosterValidRequesterProvider()
    {
        return LoginTokenProvider.provider.provide("admin, professor, ta");
    }


    static Iterator<ILoginToken> getRosterInvalidRequesterProvider()
    {
        List<ILoginToken> invalidRequesters = new ArrayList<>();


        ILoginToken notCourseProfessor = StubFactory.makeStubLoginToken("notCourseProfessor", AccountType.professor);
        ILoginToken notCourseTa = StubFactory.makeStubLoginToken("notCourseTa", AccountType.ta);
        ILoginToken student = StubFactory.makeStubLoginToken("student", AccountType.student);


        invalidRequesters.add(notCourseProfessor);
        invalidRequesters.add(notCourseTa);
        invalidRequesters.add(student);
        return invalidRequesters.iterator();
    }

    @ParameterizedTest
    @MethodSource(names = "getRosterInvalidRequesterProvider")
    void getRoster_requesterHasNoPermission_throwsNoPermissionException(ILoginToken invalidRequester)
    {
        Roster roster = StubFactory.makeTestRoster();
        Course course = new Course("software", roster, null);
        Assertions.assertThrows(NoPermissionException.class, () -> course.getRoster(invalidRequester));
    }
}