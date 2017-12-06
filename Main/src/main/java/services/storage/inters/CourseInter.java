package services.storage.inters;

import model.courses.classes.Course;
import services.storage.model.Catalog;

import javax.json.JsonObject;

public class CourseInter extends Course implements JsonInter<Course>
{


    @Override
    public void sync(Catalog catalog)
    {

    }

    @Override
    public Course convert()
    {
        return null;
    }

    @Override
    public JsonObject toJson()
    {
        return null;
    }
}
