package model.courses.classes;

import model.accounts.enums.AccountType;
import model.assignments.interfaces.IAssignment;
import model.courses.interfaces.ICourse;
import model.courses.interfaces.IRoster;
import model.exceptions.NoPermissionException;
import services.login.interfaces.ILoginToken;

import java.util.List;

public class Course implements ICourse
{
    private String courseName;
    private IRoster roster;
    private List<IAssignment> assignmentList;

    public Course(String courseName, IRoster roster, List<IAssignment> assignmentList)
    {
        this.courseName = courseName;
        this.roster = roster;
        this.assignmentList = assignmentList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCourseName()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IAssignment> getAssignments(ILoginToken requester)
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAssignment(ILoginToken requester, IAssignment newAssignment)
    {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeAssignment(ILoginToken requester, IAssignment assignmentToRemove)
    {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IRoster getRoster(ILoginToken requester)
    {
        if (requester.getAccountType().equals(AccountType.admin) || roster.isProfessor(requester,requester.getAccount()) || roster.isTa(requester,requester.getAccount()))
        {
            return roster;
        }
        throw new NoPermissionException();
    }
}
