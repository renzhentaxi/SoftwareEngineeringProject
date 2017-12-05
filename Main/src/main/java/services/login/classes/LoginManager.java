package services.login.classes;

import services.login.interfaces.ILoginManager;
import services.login.interfaces.ILoginToken;

public class LoginManager implements ILoginManager
{
    /**
     * {@inheritDoc}
     */
    @Override
    public ILoginToken login(String userName, String password)
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logout(String userName)
    {

    }
}
