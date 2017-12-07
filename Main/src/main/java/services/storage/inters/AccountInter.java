package services.storage.inters;

import model.accounts.classes.Account;
import model.accounts.enums.AccountType;
import model.courses.interfaces.ICourse;
import services.storage.model.Catalog;

import javax.json.JsonObject;
import java.util.List;
import java.util.stream.Collectors;

public class AccountInter extends Account implements JsonInter<Account>
{
    private List<String> courseNameList;
    private boolean isSync = false;

    public AccountInter(JsonObject object)
    {
        this.firstName = object.getString("firstName");
        this.lastName = object.getString("lastName");
        this.userName = object.getString("userName");
        this.accountType = AccountType.valueOf(object.getString("accountType"));
        this.courseNameList = object.getJsonArray("courseList").stream().map(jsonValue -> jsonValue.toString().replace("\"", "")).collect(Collectors.toList());
    }


    @Override

    public void sync(Catalog catalog)
    {
        this.courseList = courseNameList.stream().map(catalog::getCourse).collect(Collectors.toList());
        isSync = true;
    }

    @Override
    public Account convert()
    {
        if (!isSync) throw new RuntimeException("need to sync object before converting");
        return this;
    }


    public boolean hasCourse(ICourse course)
    {
        return courseNameList.contains(course.getCourseName());
    }

    public void addCourse(ICourse course)
    {
        courseNameList.add(course.getCourseName());
    }
}
