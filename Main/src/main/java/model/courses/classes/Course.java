package model.courses.classes;

import model.assignments.interfaces.IAssignment;
import model.courses.exceptions.AssignmentAlreadyExistException;
import model.courses.exceptions.AssignmentDoesNotExistException;
import model.courses.exceptions.GradedAssignmentException;
import model.courses.interfaces.ICourse;
import model.courses.interfaces.IRoster;
import services.login.classes.Permissions;
import services.login.interfaces.ILoginToken;
import services.login.interfaces.IPermission;

import java.util.Collections;
import java.util.List;

public class Course implements ICourse
{
    private String courseName;
    private Roster roster;
    private List<IAssignment> assignmentList;

    public Course(String courseName, Roster roster, List<IAssignment> assignmentList)
    {
        this.courseName = courseName;
        this.roster = roster;
        this.assignmentList = assignmentList;

        getRosterPermission = Permissions.or(roster.isProfessorPerm, roster.isTaPerm);
        getAssignPermission = Permissions.or(roster.isTaPerm, roster.isProfessorPerm, roster.isStudentPerm);
    }

    protected Course()
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCourseName()
    {
        return courseName;
    }

    IPermission getAssignPermission;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IAssignment> getAssignments(ILoginToken requester)
    {
        getAssignPermission.or(Permissions.isAdmin).check(requester);
        return Collections.unmodifiableList(assignmentList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAssignment(ILoginToken requester, IAssignment newAssignment)
    {
        Permissions.or(roster.isProfessorPerm, Permissions.isAdmin).check(requester);
        String name = newAssignment.getName(requester);
        for (IAssignment assignment : assignmentList)
        {
            if (assignment.getName(requester).equals(name))
            {
                throw new AssignmentAlreadyExistException();
            }
        }
        assignmentList.add(newAssignment);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeAssignment(ILoginToken requester, IAssignment assignmentToRemove)
    {
        Permissions.or(roster.isProfessorPerm, Permissions.isAdmin).check(requester);
        String name = assignmentToRemove.getName(requester);
        for (int i = 0; i < assignmentList.size(); i++)
        {
            IAssignment assignment = assignmentList.get(i);
            if (assignment.getName(requester).equals(name))
            {
                if (assignment.isGradedAny(requester)) throw new GradedAssignmentException();

                assignmentList.remove(i);
                return;
            }
        }
        throw new AssignmentDoesNotExistException();
    }

    IPermission getRosterPermission;

    /**
     * {@inheritDoc}
     */
    @Override
    public IRoster getRoster(ILoginToken requester)
    {
        getRosterPermission.or(Permissions.isAdmin).check(requester);
        return roster;
    }
}
