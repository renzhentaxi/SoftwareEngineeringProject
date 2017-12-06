package model.courses.interfaces;

import model.accounts.interfaces.IAccount;
import services.login.exceptions.NoPermissionException;
import services.login.interfaces.ILoginToken;

import java.util.List;

public interface IRoster {


    /**
     * returns the professor of the course that this roster belongs to
     *
     * @param requester the user who requests this information. Should be professor of the course or admin.
     * @return the account fo the professor of this course
     * @throws NoPermissionException if the requester does not have the permission to complete this request
     */
    IAccount getProfessor(ILoginToken requester);

    /**
     * returns the Ta of the course that this roster belongs to
     *
     * @param requester the user who requests this information. Requester be ta of the course or admin
     * @return the account fo the Ta of this course
     * @throws NoPermissionException if the requester does not have the permission to complete this request
     */
    IAccount getTa(ILoginToken requester);

    /**
     * returns a readonly list of students for this course
     * if requester is student, a list of only one IAccount is returned. the Iaccount being the student itself.
     * @param requester the user who requested the list of students. should be professor,ta,student of the course or admin
     * @return a readonly list of students for this course
     * @throws NoPermissionException if the requester does not have the permission to complete this request
     */
    List<IAccount> getStudents(ILoginToken requester);

    /**
     * check if the account is the professor of this course
     *
     * @param requester the user who requests this information. Requester should be the professor,ta of the course or admin
     * @param account the account that is being checked
     * @return true if the user is the professor of this course, false otherwise
     * @throws NoPermissionException if the requester does not have the permission to complete this request
     */
    boolean isProfessor(ILoginToken requester, IAccount account);

    /**
     * check if the account is the Ta of this course
     *
     * @param requester the user who requests this information. Requester should be the professor,ta of the course or admin
     * @param account the account that is being checked
     * @return true if the user is the Ta of this course, false otherwise
     * @throws NoPermissionException if the requester does not have the permission to complete this request
     */
    boolean isTa(ILoginToken requester, IAccount account);

    /**
     * check if the account is a student of this course
     *
     * @param requester the user who requests this information.   should be professor,ta,student of the course or admin
     * @param account the account that is being checked
     * @return true if the user is a student of this course, false otherwise
     * @throws NoPermissionException if the requester does not have the permission to complete this request
     */
    boolean isStudent(ILoginToken requester, IAccount account);


    /**
     * check if the account is a student, ta, or professor of this roster.
     * @param requester the user who requests this information. Should be in the roster or admin.
     * @param account the account that is being checked
     * @return true if the user is a student of this course, false otherwise
     */
    boolean isInRoster(ILoginToken requester, IAccount account);
}
