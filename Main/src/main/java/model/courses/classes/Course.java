package model.courses.classes;

import model.assignments.classes.Assignment;
import model.assignments.interfaces.IAssignment;
import model.courses.exceptions.AssignmentAlreadyExistException;
import model.courses.exceptions.AssignmentDoesNotExistException;
import model.courses.exceptions.GradedAssignmentException;
import model.courses.interfaces.ICourse;
import model.courses.interfaces.IRoster;
import services.login.exceptions.NoPermissionException;
import services.login.interfaces.ILoginToken;
import services.login.permissions.IPermission;
import services.login.permissions.Permissions;
import services.storage.interfaces.IJsonable;
import services.storage.model.Catalog;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.Collections;
import java.util.List;

public class Course implements ICourse, IJsonable
{
    protected String courseName;
    protected Roster roster;
    protected List<Assignment> assignmentList;
    private IPermission getRosterPermission;

    public Course(String courseName, Roster roster, List<Assignment> assignmentList)
    {
        this.courseName = courseName;
        this.roster = roster;
        this.assignmentList = assignmentList;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Assignment> getAssignments(ILoginToken requester)
    {
        Permissions.or(roster.isTaPerm, roster.isProfessorPerm, roster.isStudentPerm, Permissions.isAdmin).check(requester);
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
        assignmentList.add((Assignment) newAssignment);
        Catalog.catalog.commit();
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
                Catalog.catalog.commit();
                return;
            }
        }
        throw new AssignmentDoesNotExistException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IRoster getRoster(ILoginToken requester)
    {
        if (roster.isInRoster(requester, requester.getAccount()) || Permissions.isAdmin.hasPermission(requester))
        {
            return roster;
        }
        throw new NoPermissionException();
    }

    @Override
    public JsonObject toJson()
    {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (Assignment assignment : assignmentList)
        {
            builder.add(assignment.toJson());
        }
        JsonArray assignmentList = builder.build();

        return Json.createObjectBuilder()
                .add("courseName", courseName)
                .add("roster", roster.toJson())
                .add("assignmentList", assignmentList)
                .build();
    }

    @Override
    public String toString()
    {
        return courseName;
    }
}
