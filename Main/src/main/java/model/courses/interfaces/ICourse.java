package model.courses.interfaces;

import model.assignments.classes.Assignment;
import model.assignments.interfaces.IAssignment;
import model.courses.exceptions.AssignmentAlreadyExistException;
import model.courses.exceptions.AssignmentDoesNotExistException;
import model.courses.exceptions.GradedAssignmentException;
import services.login.exceptions.NoPermissionException;
import services.login.interfaces.ILoginToken;

import java.util.List;

public interface ICourse {

    /**
     * returns the course name of this course.
     * the course name is unique within a catalog
     * @return the course name of this course.
     */
    String getCourseName();

    /**
     * returns all assignments for the course
     *
     * @param requester the user who requests the assignments. The user should be part of the roster(either as a student, professor, ta) or admin.
     * @return a readonly list of all assignments for the course.
     * @throws NoPermissionException when the requester does belong to the course.
     */
    List<Assignment> getAssignments(ILoginToken requester);

    /**
     * adds a new assignment to the course
     *
     * @param requester     the user who requests to add the assignment
     * @param newAssignment the assignment to be added
     * @throws AssignmentAlreadyExistException if there is another assignment of the same name in the course already.
     * @throws NoPermissionException           if the requester is not the professor of the course or Admin.
     */
    void addAssignment(ILoginToken requester, IAssignment newAssignment);


    /**
     * removes the target assignment from the assignment list
     *
     * @param requester          the user who requests to remove the assignment.
     * @param assignmentToRemove the assignment to be removed
     * @throws AssignmentDoesNotExistException if the assignment does not exist in the course
     * @throws GradedAssignmentException       if the assignment is graded
     * @throws NoPermissionException           if the requester is not the professor of the course or admin
     */
    void removeAssignment(ILoginToken requester, IAssignment assignmentToRemove);

    /**
     * returns the roster of the class
     *
     * @param requester the user who requests the roster. The user should be either the professor or ta of the course.
     * @return the roster of the class
     * @throws NoPermissionException when the requester is not the professor or ta of the course or admin.
     */
    IRoster getRoster(ILoginToken requester);

}
