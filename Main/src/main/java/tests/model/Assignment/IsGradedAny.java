package tests.model.Assignment;

import model.accounts.interfaces.IAccount;
import model.assignments.classes.Assignment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import services.login.exceptions.NoPermissionException;
import services.login.interfaces.ILoginToken;
import tests.StubFactory;
import tests.provider.AccountProvider;
import tests.provider.AssignmentProvider;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertThrows;


class IsGradedAny
{
    static Iterator<ILoginToken> validRequester_provider()
    {
        return StubFactory.makeLoginTokenProvider("admin", "professor", "ta");
    }

    static Iterator<ILoginToken> invalidRequester_provider()
    {
        return StubFactory.makeLoginTokenProvider("notCourseProfessor", "notCourseTa");
    }

    @ParameterizedTest
    @MethodSource(names = "validRequester_provider")
    void validRequester_returnsBoolean(ILoginToken validRequester)
    {
        Assignment assignment = AssignmentProvider.makeTestAssignment();
        ILoginToken validGrader = StubFactory.makeLoginToken("admin");
        IAccount student = AccountProvider.provider.provideSingle("student");
        float grade = 20f;

        assignment.enterGrade(validGrader, student, grade);
        Assertions.assertTrue(assignment.isGradedAny(validRequester));

    }


    @ParameterizedTest
    @MethodSource(names = "invalidRequester_provider")
    void invalidRequester_throwsNoPermissionException(ILoginToken invalidRequester)
    {
        Assignment assignment = AssignmentProvider.makeTestAssignment();
        ILoginToken validGrader = StubFactory.makeLoginToken("admin");
        IAccount student = AccountProvider.provider.provideSingle("student");
        float grade = 20f;

        assignment.enterGrade(validGrader, student, grade);

        assertThrows(NoPermissionException.class, () -> assignment.isGradedAny(invalidRequester));
    }

    @Test
    void atleastOneStudentIsGraded_returnsTrue()
    {
        Assignment assignment = AssignmentProvider.makeTestAssignment();
        ILoginToken validGrader = StubFactory.makeLoginToken("admin");
        ILoginToken validRequester = StubFactory.makeLoginToken("admin");
        IAccount student = AccountProvider.provider.provideSingle("student");
        float grade = 20f;

        assignment.enterGrade(validGrader, student, grade);

        Assertions.assertTrue(assignment.isGradedAny(validRequester));

    }

    @Test
    void allStudentsNotGraded_returnsFalse()
    {
        Assignment assignment = AssignmentProvider.makeTestAssignment();
        ILoginToken validRequester = StubFactory.makeLoginToken("admin");


        Assertions.assertFalse(assignment.isGradedAny(validRequester));
    }
}
