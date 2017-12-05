package model.assignments.classes;

import model.accounts.interfaces.IAccount;
import model.assignments.interfaces.IAssignment;
import model.courses.interfaces.ICourse;
import services.login.interfaces.ILoginToken;

import java.util.Dictionary;
import java.util.Map;

public class Assignment implements IAssignment
{
    private String name;
    private String description;
    private ICourse course;
    private Map<IAccount,Float> grades;

    /**
     * @param name the name of the assignment. This should be unique within a course
     * @param description the description of the assignment
     * @param course the course that contains this assignment.
     */
    public Assignment(String name, String description, ICourse course)
    {
        this.name = name;
        this.description = description;
        this.course = course;
    }

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
