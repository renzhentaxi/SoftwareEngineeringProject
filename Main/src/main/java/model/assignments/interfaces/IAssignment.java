package model.assignments.interfaces;

import model.accounts.interfaces.IAccount;
import model.assignments.exceptions.AlreadyGradedException;
import model.assignments.exceptions.NotAStudentException;
import model.assignments.exceptions.NotGradedException;
import model.exceptions.NoPermissionException;
import services.login.interfaces.ILoginToken;

import java.util.Dictionary;

//todo: edit description/edit name feature for the professor
public interface IAssignment {
    /**
     * The description of the assignment
     *
     * @param requester the user who requested the name
     * @return the name of the assignment
     * @throws NoPermissionException if the requester does not belong to the course.
     */
    String getDescription(ILoginToken requester);


    /**
     * The name of the assignment should be unique within a course.
     *
     * @param requester the user who requested the name
     * @return the name of the assignment
     * @throws NoPermissionException if the requester does not belong to the course.
     */
    String getName(ILoginToken requester);


    /**
     * enters a new grade for the student
     *
     * @param requester the user who enters the grade
     * @param student   the account whose grade for this assignment is being entered
     * @param grade     the grade to be entered
     * @throws NotAStudentException   if the student is not a student of this course.
     * @throws AlreadyGradedException if there is already a grade for the student
     * @throws NoPermissionException  if the requester is not a Ta or Professor of this course
     */
    void enterGrade(ILoginToken requester, IAccount student, float grade);

    /**
     * clears the grade for the student
     *
     * @param requester the user who clears the grade
     * @param student   the student whose grade is to be cleared
     * @throws NotAStudentException  if the student is not a student of this course
     * @throws NotGradedException    if the student does not have a grade to clear
     * @throws NoPermissionException if the requester is not a professor of this course
     */

    void clearGrade(ILoginToken requester, IAccount student);

    /**
     * modifies the grade for the student
     *
     * @param requester the user who modifies the grade
     * @param student   the student whose grade is to be modified
     * @param newGrade  the modified grade for the student
     * @throws NotAStudentException  if the student is not a student of this course
     * @throws NotGradedException    if the student does not have a grade to modify(use enterGrade instead)
     * @throws NoPermissionException if the requester is not a professor of this course
     */
    void modifyGrade(ILoginToken requester, IAccount student, float newGrade);

    /**
     * returns the grade of the student
     *
     * @param requester the user who requests the grade of the student
     * @param student   the student whose grade is being requested
     * @return the grade of the student or -1.f if it is not graded
     * @throws NoPermissionException if the requester is not the professor or ta of this course or if the requester is not the student who owns the grade.
     */
    float getGrade(ILoginToken requester, IAccount student);

    /**
     * returns all grades in this assignment
     * @param requester the user who requests the grades
     * @return a readonly dictionary of all the grades. -1.f is used to represent the grade of a student that hasn't been graded yet.
     * @throws NoPermissionException if the requester is not the professor or ta of this course
     */
    Dictionary<IAccount, Float> getGrades(ILoginToken requester);


    /**
     * check if the student has a grade for this assignment
     * @param requester the user who wants to check the grade
     * @param student the student of the grade that is being checked
     * @return true if the student has a grade for this assignment, false otherwise
     * @throws NoPermissionException if the requester is not the professor or ta of this course or if the requester is not the student who owns the grade
     */
    boolean isGraded(ILoginToken requester, IAccount student);
}
