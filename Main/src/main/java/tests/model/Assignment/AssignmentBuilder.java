package tests.model.Assignment;

import model.accounts.interfaces.IAccount;
import model.assignments.classes.Assignment;
import model.courses.interfaces.ICourse;
import tests.model.IBuilder;

import java.util.HashMap;
import java.util.Map;

public class AssignmentBuilder implements IBuilder<Assignment>
{
    private String name = "AssignmentName";
    private String description = "AssignmentDescription";
    private ICourse course = null;

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

    @Override
    public Assignment build()
    {
        return new Assignment(name, description, course);
    }
}
