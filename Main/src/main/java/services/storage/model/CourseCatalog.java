package services.storage.model;

import services.storage.interfaces.AbstractCatalog;
import services.storage.inters.CourseInter;

import javax.json.JsonObject;
import java.util.Map;

public class CourseCatalog extends AbstractCatalog<CourseInter>
{
    public CourseCatalog(JsonObject data)
    {
        super(data);
    }

    @Override
    public CourseInter convert(JsonObject object)
    {
        return new CourseInter(object);
    }
}