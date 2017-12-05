package tests.model.Assignment;

import model.accounts.interfaces.IAccount;
import model.assignments.classes.Assignment;
import model.courses.interfaces.ICourse;
import tests.model.IBuilder;

import java.util.Map;

public class AssignmentBuilder implements IBuilder<Assignment>
{
    private String name = "AssignmentName";
    private String description = "AssignmentDescription";
    private ICourse course = null;
    private Map<IAccount, Float> grades = null;

    public AssignmentBuilder setName(String name)
    {
        this.name = name;
        return this;
    }

    public AssignmentBuilder setDescription(String description)
    {
        this.description = description;
        return this;
    }

    public AssignmentBuilder setCourse(ICourse course)
    {
        this.course = course;
        return this;
    }

    public AssignmentBuilder setGrades(Map<IAccount, Float> grades)
    {
        this.grades = grades;
        return this;
    }

    @Override
    public Assignment build()
    {
        return new Assignment(name, description, course, grades);
    }
}
