package services.storage.inters;

import model.courses.classes.Course;
import services.storage.model.Catalog;

import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CourseInter extends Course implements JsonInter<Course>
{

    private RosterInter rosterInter;
    private List<AssignmentInter> assignmentIntersList;
    private boolean isSync = false;

    public CourseInter(JsonObject object)
    {
        this.courseName = object.getString("courseName");
        this.rosterInter = new RosterInter(this, object.getJsonObject("roster"));
        this.assignmentIntersList = object.getJsonArray("assignmentList").stream().map(jsonValue -> new AssignmentInter(this, jsonValue.asJsonObject())).collect(Collectors.toList());
    }

    @Override
    public void sync(Catalog catalog)
    {
        //convert roster
        rosterInter.sync(catalog);
        roster = rosterInter;
        //convert assignmentList
        assignmentList = new ArrayList<>();
        assignmentIntersList.forEach(assignmentInter ->
        {
            assignmentInter.sync(catalog);
            assignmentList.add(assignmentInter.convert());
        });

        isSync = true;
    }

    @Override
    public Course convert()
    {
        if (!isSync) throw new RuntimeException("need to sync object before converting");
        return this;
    }

    @Override
    public String toString()
    {
        return "inter: " + super.toString();
    }

}
