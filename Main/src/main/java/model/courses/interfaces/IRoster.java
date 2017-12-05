package model.courses.interfaces;

import model.accounts.interfaces.IAccount;
import model.exceptions.NoPermissionException;
import services.login.interfaces.ILoginToken;

import java.util.List;

public interface IRoster {


    /**
     * returns the professor of the course that this roster belongs to
     *
     * @param requester the user who requests this information.
     * @return the account fo the professor of this course
     * @throws NoPermissionException if the requester is not the professor or the Ta of the course
     */
    IAccount getProfessor(ILoginToken requester);

    /**
     * returns the Ta of the course that this roster belongs to
     *
     * @param requester the user who requests this information.
     * @return the account fo the Ta of this course
     * @throws NoPermissionException if the requester is not the professor or the Ta of the course
     */
    IAccount getTa(ILoginToken requester);

    /**
     * returns a readonly list of students for this course
     * @param requester the user who requested the list of students
     * @return a readonly list of students for this course
     * @throws NoPermissionException if the requester is not the professor or the Ta of the course
     */
    List<IAccount> getStudents(ILoginToken requester);

    /**
     * check if the account is the professor of this course
     *
     * @param requester the user who requests this information.
     * @param account the account that is being checked
     * @return true if the user is the professor of this course, false otherwise
     * @throws NoPermissionException if the requester is not the professor or the Ta of the course
     */
    boolean isProfessor(ILoginToken requester, IAccount account);

    /**
     * check if the account is the Ta of this course
     *
     * @param requester the user who requests this information.
     * @param account the account that is being checked
     * @return true if the user is the Ta of this course, false otherwise
     * @throws NoPermissionException if the requester is not the professor or the Ta of the course
     */
    boolean isTa(ILoginToken requester, IAccount account);

    /**
     * check if the account is a student of this course
     *
     * @param requester the user who requests this information.
     * @param account the account that is being checked
     * @return true if the user is a student of this course, false otherwise
     * @throws NoPermissionException if the requester is not the professor or the Ta of the course
     */
    boolean isStudent(ILoginToken requester, IAccount account);


    /**
     * check if the account is a student, ta, or professor of this roster.
     * @param account the account that is being checked
     * @return true if the user is a student of this course, false otherwise
     */
    boolean isInRoster(IAccount account);
}
