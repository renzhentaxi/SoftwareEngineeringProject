package model.courses.classes;

import model.accounts.interfaces.IAccount;
import model.courses.interfaces.IRoster;
import services.login.interfaces.ILoginToken;

import java.util.List;

public class Roster implements IRoster
{
    /**
     * {@inheritDoc}
     */
    @Override
    public IAccount getProfessor(ILoginToken requester)
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IAccount getTa(ILoginToken requester)
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IAccount> getStudents(ILoginToken requester)
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isProfessor(ILoginToken requester, IAccount account)
    {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTa(ILoginToken requester, IAccount account)
    {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isStudent(ILoginToken requester, IAccount account)
    {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInRoster(IAccount account)
    {
        return false;
    }
}
