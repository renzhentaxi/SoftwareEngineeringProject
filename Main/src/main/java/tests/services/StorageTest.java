package tests.services;

import model.accounts.classes.Account;
import model.accounts.enums.AccountType;
import model.accounts.interfaces.IAccount;
import model.assignments.classes.Assignment;
import model.courses.classes.Course;
import model.courses.classes.Roster;
import model.courses.interfaces.ICourse;
import services.storage.model.Catalog;

import java.util.ArrayList;
import java.util.List;

public class StorageTest
{
    public static void main(String[] args)
    {
        Catalog.catalog.commit();
    }


    public static void makeTestObjects(String name)
    {
        List<ICourse> courseList = new ArrayList<>();

        Account professor = new Account("pro" + name + "first", "pro" + name + "last", name + "Professor", AccountType.professor, courseList);
        Account ta = new Account("ta" + name + "first", "ta" + name + "last", name + "Ta", AccountType.ta, courseList);
        List<IAccount> students = new ArrayList<>();
        Account s1 = new Account("s1" + name + "first", "s1" + name + "last", "s1" + name + "student", AccountType.student, courseList);
        Account s2 = new Account("s2" + name + "first", "s2" + name + "last", "s2" + name + "student", AccountType.student, courseList);
        Account s3 = new Account("s3" + name + "first", "s3" + name + "last", "s3" + name + "student", AccountType.student, courseList);

        students.add(s1);
        students.add(s2);
        students.add(s3);

        Roster roster = new Roster(professor, ta, students);

        List<Assignment> assignmentList = new ArrayList<>();
        Course course = new Course(name, roster, assignmentList);

        Assignment ass1 = new Assignment("assign1", "assign1" + name + "description", course);
        Assignment ass2 = new Assignment("assign2", "assign2" + name + "description", course);

        assignmentList.add(ass1);
        assignmentList.add(ass2);


        Catalog.catalog.addAccount(professor.getUserName(), professor);
        Catalog.catalog.addAccount(ta.getUserName(), ta);
        Catalog.catalog.addAccount(s1.getUserName(), s1);
        Catalog.catalog.addAccount(s2.getUserName(), s2);
        Catalog.catalog.addAccount(s3.getUserName(), s3);

        Catalog.catalog.addCourse(course.getCourseName(), course);

    }
}
