package tests.provider;

import model.assignments.classes.Assignment;
import model.courses.classes.Course;

/**
 * syntax
 * assignmentName [courseName]
 */
public class AssignmentProvider implements ISmartProvider<Assignment>
{

    public static AssignmentProvider provider = new AssignmentProvider();

    public static Assignment makeTestAssignment()
    {
        return provider.provideSingle("assignment");
    }

    @Override
    public Assignment provideSingle(String name)
    {
        String[] split = name.split(" ");
        String assignName = split[0];
        String courseName = "course";
        if (split.length == 2)
        {
            courseName = split[1];
        }
        Course course = CourseProvider.provider.provideSingle(courseName);
        return new Assignment(assignName, "", course);
    }
}
