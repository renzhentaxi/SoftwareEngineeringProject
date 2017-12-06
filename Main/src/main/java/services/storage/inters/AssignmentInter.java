package services.storage.inters;

import model.assignments.classes.Assignment;
import model.courses.interfaces.ICourse;
import services.storage.model.Catalog;

import javax.json.JsonObject;
import java.util.Map;

public class AssignmentInter extends Assignment implements JsonInter<Assignment>
{
    private Map<String, Float> nameGrades;

    public AssignmentInter(ICourse course, JsonObject object)
    {
        this.name = object.getString("name");
        this.description = object.getString("desc");
        this.course = course;
    }

    @Override
    public void sync(Catalog catalog)
    {

    }

    @Override
    public Assignment convert()
    {
        return null;
    }
}
