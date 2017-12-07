package services.storage.inters;

import model.accounts.classes.Account;
import model.assignments.classes.Assignment;
import model.courses.interfaces.ICourse;
import services.storage.model.Catalog;

import javax.json.JsonObject;
import java.util.HashMap;
import java.util.Map;

public class AssignmentInter extends Assignment implements JsonInter<Assignment>
{
    private Map<String, Float> nameGrades;
    private boolean isSync = false;

    public AssignmentInter(ICourse course, JsonObject object)
    {
        this.name = object.getString("name");
        this.description = object.getString("description");
        this.nameGrades = JsonHelper.toMap(object.getJsonObject("grades"), Float::valueOf);
        this.course = course;
    }

    @Override
    public void sync(Catalog catalog)
    {
        grades = new HashMap<>();
        for (Map.Entry<String, Float> nameGrade : nameGrades.entrySet())
        {
            Account account = catalog.getAccount(nameGrade.getKey());
            Float grade = nameGrade.getValue();
            grades.put(account, grade);
        }
        isSync = true;
    }

    @Override
    public Assignment convert()
    {
        if (!isSync) throw new RuntimeException("need to sync object before converting");
        return this;
    }
}
