package model.assignments.classes;

import model.accounts.classes.Account;
import model.accounts.enums.AccountType;
import model.accounts.interfaces.IAccount;
import model.assignments.exceptions.AlreadyGradedException;
import model.assignments.exceptions.BadGradeException;
import model.assignments.exceptions.NotCourseStudentException;
import model.assignments.exceptions.NotGradedException;
import model.assignments.interfaces.IAssignment;
import model.courses.interfaces.ICourse;
import model.courses.interfaces.IRoster;
import model.exceptions.NoPermissionException;
import services.login.interfaces.ILoginToken;
import services.storage.interfaces.IJsonable;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.HashMap;
import java.util.Map;

public class Assignment implements IAssignment, IJsonable
{
    private String name;
    private String description;
    private ICourse course;
    private Map<IAccount, Float> grades;

    /**
     * @param name        the name of the assignment. This should be unique within a course
     * @param description the description of the assignment
     * @param course      the course that contains this assignment.
     */
    public Assignment(String name, String description, ICourse course)
    {
        this.name = name;
        this.description = description;
        this.course = course;
        this.grades = new HashMap<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription(ILoginToken requester)
    {
        IRoster roster = course.getRoster(requester);
        IAccount requesterAccount = requester.getAccount();
        if (requester.getAccountType() != AccountType.admin &&
                !roster.isInRoster(requester, requesterAccount)) throw new NoPermissionException();
        return this.description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName(ILoginToken requester)
    {
        IRoster roster = course.getRoster(requester);
        IAccount requesterAccount = requester.getAccount();
        if (requester.getAccountType() != AccountType.admin &&
                !roster.isInRoster(requester,requesterAccount)) throw new NoPermissionException();

        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void enterGrade(ILoginToken requester, IAccount student, float grade)
    {
        IRoster roster = course.getRoster(requester);
        IAccount requesterAccount = requester.getAccount();

        if (requester.getAccountType() != AccountType.admin &&
                !roster.isProfessor(requester, requesterAccount) &&
                !roster.isTa(requester, requesterAccount))
            throw new NoPermissionException();

        if (!roster.isStudent(requester, student)) throw new NotCourseStudentException();
        if (isGraded(requester, student)) throw new AlreadyGradedException();
        if (grade < 0 || grade > 100) throw new BadGradeException();
        grades.put(student, grade);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearGrade(ILoginToken requester, IAccount student)
    {
        IRoster roster = course.getRoster(requester);
        IAccount requesterAccount = requester.getAccount();

        if (requester.getAccountType() != AccountType.admin &&
                !roster.isProfessor(requester, requesterAccount))
            throw new NoPermissionException();
        if (!roster.isStudent(requester, student)) throw new NotCourseStudentException();
        if (!isGraded(requester, student)) throw new NotGradedException();

        grades.put(student, -1f);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void modifyGrade(ILoginToken requester, IAccount student, float newGrade)
    {
        IRoster roster = course.getRoster(requester);
        if (requester.getAccountType() != AccountType.admin && !roster.isProfessor(requester, requester.getAccount()))
            throw new NoPermissionException();
        if (!roster.isStudent(requester, student)) throw new NotCourseStudentException();
        if (!isGraded(requester, student)) throw new NotGradedException();
        if (newGrade < 0 || newGrade > 100) throw new BadGradeException();
        grades.put(student, newGrade);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getGrade(ILoginToken requester, IAccount student)
    {
        IRoster roster = course.getRoster(requester);
        IAccount requesterAccount = requester.getAccount();

        if (requester.getAccountType() != AccountType.admin &&
                !roster.isProfessor(requester, requesterAccount) &&
                !roster.isTa(requester, requesterAccount) &&
                !(roster.isStudent(requester, requesterAccount) && requesterAccount.equals(student)))
            throw new NoPermissionException();
        return grades.get(student);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<IAccount, Float> getGrades(ILoginToken requester)
    {
        throw new NotImplementedException();
        // return Collections.unmodifiableMap(grades);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isGraded(ILoginToken requester, IAccount student)
    {
        IRoster roster = course.getRoster(requester);
        IAccount requesterAccount = requester.getAccount();

        if (requester.getAccountType() != AccountType.admin &&
                !roster.isProfessor(requester, requesterAccount) &&
                !roster.isTa(requester, requesterAccount) &&
                !(roster.isStudent(requester, requesterAccount) && requesterAccount.equals(student)))
            throw new NoPermissionException();
        if (!roster.isStudent(requester, student)) throw new NotCourseStudentException();

        return grades.containsKey(student) && grades.get(student) >= 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isGradedAny(ILoginToken requester)
    {
        for (IAccount student : grades.keySet())
        {
            if (isGraded(requester, student)) return true;
        }
        return false;
    }

    @Override
    public JsonObject toJson()
    {
        JsonObjectBuilder gradeBuilder = Json.createObjectBuilder();

        for(IAccount student: grades.keySet())
        {
            gradeBuilder.add(student.getUserName(), grades.get(student));
        }
        
        JsonObject grade = gradeBuilder.build();
        return Json.createObjectBuilder()
                .add("name", name)
                .add("course", course.getCourseName())
                .add("desc", description)
                .add("grades", grade)
                .build();


    }
}
