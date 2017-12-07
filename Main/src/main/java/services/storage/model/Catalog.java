package services.storage.model;

import model.accounts.classes.Account;
import model.accounts.interfaces.IAccount;
import model.courses.classes.Course;
import model.courses.interfaces.ICourse;
import services.storage.inters.AccountInter;
import services.storage.inters.CourseInter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Catalog
{
    public static Catalog catalog = new Catalog();

    public AccountCatalog accountCatalog;
    public CourseCatalog courseCatalog;

    public Catalog()
    {
        accountCatalog = LoadAccounts();
        courseCatalog = LoadCourses();
        accountCatalog.Sync(this);
        courseCatalog.Sync(this);


    }

    private static AccountCatalog LoadAccounts()
    {
        try (JsonReader reader = Json.createReader(new FileReader("Data/accounts.json")))
        {
            return new AccountCatalog(reader.readObject());
        } catch (FileNotFoundException e)
        {

        }


        return new AccountCatalog(Json.createObjectBuilder().build());
    }

    private static CourseCatalog LoadCourses()
    {
        try (JsonReader reader = Json.createReader(new FileReader("Data/courses.json")))
        {
            return new CourseCatalog(reader.readObject());
        } catch (FileNotFoundException e)
        {

        }


        return new CourseCatalog(Json.createObjectBuilder().build());
    }

    public Account getAccount(String name)
    {


        return accountCatalog.get(name);
    }

    public List<IAccount> getAccounts(List<String> names)
    {
        return names.stream().map(this::getAccount).collect(Collectors.toList());
    }

    public boolean hasAccount(String name)
    {
        return accountCatalog.has(name);
    }

    public ICourse getCourse(String name)
    {
        return courseCatalog.get(name);

    }

    public boolean hasCourse(String name)
    {
        return courseCatalog.has(name);

    }

    public void addAccount(String name, Account account)
    {
        accountCatalog.add(name, new AccountInter(account.toJson()));
    }

    public void addCourse(String name, Course course)
    {
        courseCatalog.add(name, new CourseInter(course.toJson()));
    }

    public void commit()
    {


        courseCatalog.Sync(catalog);


        accountCatalog.Sync(catalog);
        JsonObject courses = courseCatalog.toJson();
        JsonObject accounts = accountCatalog.toJson();
        write(courses, "Data/courses.json");

        write(accounts, "Data/accounts.json");
    }

    private static void write(JsonObject object, String path)
    {
        try (JsonWriter writer = Json.createWriter(new FileWriter(path)))
        {
            writer.write(object);
        } catch (IOException e)
        {

        }
    }

}
