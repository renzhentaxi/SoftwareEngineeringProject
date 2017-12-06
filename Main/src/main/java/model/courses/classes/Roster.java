package model.courses.classes;

import model.accounts.interfaces.IAccount;
import model.courses.interfaces.IRoster;
import services.login.exceptions.NoPermissionException;
import services.login.classes.Permissions;
import services.login.interfaces.ILoginToken;
import services.login.interfaces.IPermission;
import services.storage.interfaces.IJsonable;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.Collections;
import java.util.List;

public class Roster implements IRoster, IJsonable
{
    protected IAccount professor;
    protected IAccount ta;
    protected List<IAccount> students;

    public IPermission isProfessorPerm;
    public IPermission isTaPerm;
    public IPermission isStudentPerm;

    public Roster(IAccount professor, IAccount ta, List<IAccount> students)
    {
        this.professor = professor;
        this.ta = ta;
        this.students = students;
        isProfessorPerm = Permissions.accountIs(professor);
        isTaPerm = Permissions.accountIs(ta);
        isStudentPerm = Permissions.accountIsAny(students);

    }

    protected Roster()
    {
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public IAccount getProfessor(ILoginToken requester)
    {
        Permissions.or(isProfessorPerm, Permissions.isAdmin).check(requester);

        return professor;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public IAccount getTa(ILoginToken requester)
    {
        Permissions.or(isTaPerm, Permissions.isAdmin).check(requester);
        return ta;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IAccount> getStudents(ILoginToken requester)
    {
        if (Permissions.or(isTaPerm, isProfessorPerm, Permissions.isAdmin).hasPermission(requester))
        {
            return Collections.unmodifiableList(students);
        } else
        {
            isStudentPerm.check(requester);
            return Collections.singletonList(requester.getAccount());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isProfessor(ILoginToken requester, IAccount account)
    {
        return getProfessor(requester).equals(account);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTa(ILoginToken requester, IAccount account)
    {
        return getTa(requester).equals(account);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isStudent(ILoginToken requester, IAccount account)
    {
        return getStudents(requester).contains(account);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInRoster(ILoginToken requester, IAccount account)
    {
        try
        {
            if (isProfessor(requester, account)) return true;
        } catch (NoPermissionException ignored)
        {
        }
        try
        {
            if (isTa(requester, account)) return true;
        } catch (NoPermissionException ignored)
        {
        }
        try
        {
            if (isStudent(requester, account)) return true;
        } catch (NoPermissionException ignored)
        {
        }

        return false;
    }

    //
    //    private IAccount professor;
    //    private IAccount ta;
    //    private List<IAccount> students;
    @Override
    public JsonObject toJson()
    {
        JsonArrayBuilder studentsBuilder = Json.createArrayBuilder();
        for (IAccount student : students)
        {
            studentsBuilder.add(student.getUserName());
        }
        JsonArray students = studentsBuilder.build();

        return Json.createObjectBuilder()
                .add("prof", professor.getUserName())
                .add("ta", ta.getUserName())
                .add("students", students)
                .build();
    }
}
