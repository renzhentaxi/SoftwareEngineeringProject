package services.storage.model;

import model.accounts.interfaces.IAccount;
import model.assignments.interfaces.IAssignment;
import model.courses.classes.Roster;
import model.courses.interfaces.ICourse;
import services.storage.interfaces.ICatalog;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.stream.Collectors;

public class Catalog
{
    public static Catalog catalog = new Catalog();

    private ICatalog<IAccount> accountCatalog;
    private ICatalog<ICourse> courseCatalog;
    private ICatalog<Roster> rosterCatalog;
    private ICatalog<IAssignment> assignmentCatalog;

    public Catalog()
    {
    }

    public IAccount getAccount(String name)
    {
        throw new NotImplementedException();
    }

    public List<IAccount> getAccounts(List<String> names)
    {
        return names.stream().map(this::getAccount).collect(Collectors.toList());
    }

    public boolean hasAccount(String name)
    {
        throw new NotImplementedException();
    }

    public ICourse getCourse(String name)
    {
        throw new NotImplementedException();

    }

    public boolean hasCouse(String name)
    {
        throw new NotImplementedException();

    }
}
