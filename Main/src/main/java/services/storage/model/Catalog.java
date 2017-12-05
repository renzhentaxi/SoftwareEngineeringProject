package services.storage.model;

import model.accounts.interfaces.IAccount;
import model.courses.interfaces.ICourse;
import model.exceptions.NoPermissionException;
import services.login.interfaces.ILoginToken;
import services.storage.exceptions.AccountDoesNotExistException;
import services.storage.exceptions.CourseDoesNotExistException;
import services.storage.interfaces.ICatalog;

public class Catalog implements ICatalog
{
    /**
     * {@inheritDoc}
     */
    @Override
    public ICourse getCourse(ILoginToken requester, int courseId)
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IAccount getAccount(ILoginToken requester, int accountId)
    {
        return null;
    }
}
