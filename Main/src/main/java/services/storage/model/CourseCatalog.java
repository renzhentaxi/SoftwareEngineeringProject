package services.storage.model;

import model.courses.interfaces.ICourse;
import services.storage.interfaces.AbstractCatalog;

import java.util.Map;

public class CourseCatalog extends AbstractCatalog<ICourse>
{
    public CourseCatalog(Map<String, ICourse> data)
    {
        super(data);
    }
}