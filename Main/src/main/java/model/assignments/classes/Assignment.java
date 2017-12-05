package model.assignments.classes;

import model.accounts.enums.AccountType;
import model.accounts.interfaces.IAccount;
import model.assignments.interfaces.IAssignment;
import model.exceptions.NoPermissionException;
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
        return this.description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName(ILoginToken requester)
    {
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void enterGrade(ILoginToken requester, IAccount student, float grade)
    {
    	if (requester.getAccountType() == AccountType.professor || requester.getAccountType() == AccountType.ta)
    		grades.put(student, grade);
    	throw new NoPermissionException();
    	
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void clearGrade(ILoginToken requester, IAccount student)
    {
    	if (!(isGraded(requester, student)))
    		grades.remove(student);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void modifyGrade(ILoginToken requester, IAccount student, float newGrade)
    {
    	if (requester.getAccountType() == AccountType.professor)
    		if (this.isGraded(requester, student))
    			grades.put(student, newGrade);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getGrade(ILoginToken requester, IAccount student)
    {
    	float theGrade = -5;
    	if (requester.getAccountType() == AccountType.professor || requester.getAccountType() == AccountType.ta)
    		theGrade = grades.get(student);
        return theGrade;
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
    	boolean result = false;
    	if(requester.getAccountType() == AccountType.professor || requester.getAccountType() == AccountType.ta)
    		if (grades.containsKey(student))
    			result = true;
    	
        return result;
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
