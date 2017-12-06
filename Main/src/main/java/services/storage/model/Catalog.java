package services.storage.model;

import model.accounts.interfaces.IAccount;
import model.courses.interfaces.ICourse;
import model.exceptions.NoPermissionException;
import services.login.interfaces.ILoginToken;
import services.storage.exceptions.AccountDoesNotExistException;
import services.storage.exceptions.CourseDoesNotExistException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Catalog
{
    /**
     * finds and returns the course with the courseId
     *
     * @param requester the user who attempts to get the course
     * @param courseName  the id of the course
     * @return the course
     * @throws CourseDoesNotExistException if there is no course with the id in the system
     * @throws NoPermissionException       if the requester does not belong to the course
     */
    public static ICourse getCourse(ILoginToken requester, String courseName)
    {
        throw new NotImplementedException();
    }


    /**
     * finds and returns the account with the given accountId
     *
     * @param requester the user who attempts to get the account
     * @param userName the id of the account
     * @return the account
     * @throws AccountDoesNotExistException if there is no account with the id in the system
     * @throws NoPermissionException        if the requester is not a system account
     */
    public static IAccount getAccount(ILoginToken requester, String userName)
    {
        throw new NotImplementedException();
    }
}
