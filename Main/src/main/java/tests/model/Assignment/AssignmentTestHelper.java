package tests.model.Assignment;

import model.courses.interfaces.ICourse;
import tests.StubFactory;

public class AssignmentTestHelper
{

    static AssignmentBuilder makeDefaultAssignment()
    {
        ICourse course = StubFactory.makeCourseWithDefaultRoster("course");
        return new AssignmentBuilder().setCourse(course);
    }
}
