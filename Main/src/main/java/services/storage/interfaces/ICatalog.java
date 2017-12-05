package services.storage.interfaces;

import model.accounts.interfaces.IAccount;
import model.courses.interfaces.ICourse;
import model.exceptions.NoPermissionException;
import services.login.interfaces.ILoginToken;
import services.storage.exceptions.AccountDoesNotExistException;
import services.storage.exceptions.CourseDoesNotExistException;

public interface ICatalog {


    /**
     * finds and returns the course with the courseId
     * @param requester the user who attempts to get the course
     * @param courseId the id of the course
     * @return the course
     * @throws CourseDoesNotExistException if there is no course with the id in the system
     * @throws NoPermissionException if the requester does not belong to the course
     *
     */
    ICourse getCourse(ILoginToken requester, int courseId);

    /**
     * finds and returns the account with the given accountId
     * @param requester the user who attempts to get the account
     * @param accountId the id of the account
     * @return the account
     * @throws AccountDoesNotExistException if there is no account with the id in the system
     * @throws NoPermissionException if the requester is not a system account
     */
    IAccount getAccount(ILoginToken requester, int accountId);

}
