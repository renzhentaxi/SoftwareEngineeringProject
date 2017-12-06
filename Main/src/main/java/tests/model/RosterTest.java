package tests.model;

import model.accounts.interfaces.IAccount;
import model.courses.classes.Roster;
import model.exceptions.NoPermissionException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import services.login.interfaces.ILoginToken;
import tests.provider.AccountProvider;
import tests.provider.LoginTokenProvider;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RosterTest
{
    private List<IAccount> students = AccountProvider.provider.provideMany("student a, student b, student c");
    private Roster roster = new Roster(AccountProvider.professor, AccountProvider.ta, students);

    static Iterator<ILoginToken> getProfessor_valid_provider()
    {
        return LoginTokenProvider.provider.provide("admin,professor");
    }

    static Iterator<ILoginToken> getProfessor_invalid_provider()
    {
        return LoginTokenProvider.provider.provide("ta,student");
    }

    static Iterator<ILoginToken> getStudents_valid_provider()
    {
        return LoginTokenProvider.provider.provide("admin,professor,professor,ta");
    }

    static Iterator<ILoginToken> getStudents_invalid_provider()
    {
        return LoginTokenProvider.provider.provide("student other");
    }

    static Iterator<ILoginToken> getStudentsAsStudent_valid_provider()
    {
        return LoginTokenProvider.provider.provide("student a, student b, student c");
    }
    @ParameterizedTest
    @MethodSource(names = "getProfessor_valid_provider")
    void getProfessor_validRequester_returnsProfessor(ILoginToken requester)
    {

        assertEquals(AccountProvider.professor, roster.getProfessor(requester));
    }

    @ParameterizedTest
    @MethodSource(names = "getProfessor_invalid_provider")
    void isProfessor_invalidRequester_throwNoPermissionException(ILoginToken requester)
    {
        assertThrows(NoPermissionException.class, () -> roster.getProfessor(requester));
    }

    @ParameterizedTest
    @MethodSource(names = "getStudents_valid_provider")
    void getStudents_validRequester_returnsStudent(ILoginToken requester)
    {
        assertEquals(students, roster.getStudents(requester));
    }

    @ParameterizedTest
    @MethodSource(names = "getStudentsAsStudent_valid_provider")
    void getStudents_studentRequester_returnsStudent(ILoginToken requester)
    {
        assertEquals(Collections.singletonList(requester.getAccount()), roster.getStudents(requester));
    }

    @ParameterizedTest
    @MethodSource(names = "getStudents_invalid_provider")
    void isStudent_invalidRequester_throwsNoPermissionException(ILoginToken requester)
    {
        assertThrows(NoPermissionException.class, () -> roster.getStudents(requester));
    }

    //todo: getTa and inRoster
}