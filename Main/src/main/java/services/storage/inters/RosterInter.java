package services.storage.inters;

import model.accounts.interfaces.IAccount;
import model.courses.classes.Roster;
import services.login.permissions.Permissions;
import services.storage.model.Catalog;

import javax.json.JsonObject;
import java.util.List;
import java.util.stream.Collectors;

public class RosterInter extends Roster implements JsonInter<Roster>
{

    private String prof;
    private String taName;
    private List<String> studentNameList;
    private boolean isSync = false;
    private CourseInter course;

    public RosterInter(CourseInter course, JsonObject object)
    {
        this.prof = object.getString("prof");
        this.taName = object.getString("ta");
        this.studentNameList = object.getJsonArray("students").stream().map(
                jsonValue -> jsonValue.toString().replace("\"", "")
        ).collect(Collectors.toList());

        this.course = course;

    }

    @Override
    public void sync(Catalog catalog)
    {

        professor = catalog.getAccount(prof);
        sync(catalog, professor);
        ta = catalog.getAccount(taName);
        sync(catalog, ta);
        students = catalog.getAccounts(studentNameList);
        students.forEach(iAccount -> sync(catalog, iAccount));
        isProfessorPerm = Permissions.accountIs(professor);
        isTaPerm = Permissions.accountIs(ta);
        isStudentPerm = Permissions.accountIsAny(students);
        isSync = true;
    }

    private void sync(Catalog catalog, IAccount account)
    {
        AccountInter accountInter = (AccountInter) account;
        if (!accountInter.hasCourse(course))
        {
            accountInter.addCourse(course);
            accountInter.sync(catalog);
        }

    }

    @Override
    public Roster convert()
    {
        if (!isSync) throw new RuntimeException("need to sync object before converting");
        return this;
    }
}
