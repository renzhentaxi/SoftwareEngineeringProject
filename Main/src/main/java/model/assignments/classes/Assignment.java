package model.assignments.classes;

import model.accounts.interfaces.IAccount;
import model.assignments.interfaces.IAssignment;
import services.login.interfaces.ILoginToken;

import java.util.Dictionary;

public class Assignment implements IAssignment
{
    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription(ILoginToken requester)
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName(ILoginToken requester)
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void enterGrade(ILoginToken requester, IAccount student, float grade)
    {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearGrade(ILoginToken requester, IAccount student)
    {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void modifyGrade(ILoginToken requester, IAccount student, float newGrade)
    {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getGrade(ILoginToken requester, IAccount student)
    {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dictionary<IAccount, Float> getGrades(ILoginToken requester)
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isGraded(ILoginToken requester, IAccount student)
    {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isGradedAny(ILoginToken requester)
    {
        return false;
    }
}
