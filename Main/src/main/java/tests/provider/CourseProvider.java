package tests.provider;

import model.courses.classes.Course;
import tests.StubFactory;

import java.util.ArrayList;

/**
 * courseName
 */
public class CourseProvider implements ISmartProvider<Course>
{
    public static CourseProvider provider = new CourseProvider();

    @Override
    public Course provideSingle(String name)
    {
        return new Course(name, StubFactory.makeTestRoster(), new ArrayList<>());
    }
}
