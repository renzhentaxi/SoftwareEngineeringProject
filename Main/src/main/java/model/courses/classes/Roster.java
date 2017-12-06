package model.courses.classes;

import model.accounts.interfaces.IAccount;
import model.courses.interfaces.IRoster;
import model.exceptions.NoPermissionException;
import services.login.interfaces.IPermission;
import services.login.classes.Permissions;
import services.login.interfaces.ILoginToken;

import java.util.Collections;
import java.util.List;

public class Roster implements IRoster
{
    private IAccount professor;
    private IAccount ta;
    private List<IAccount> students;

    private IPermission isProfessorPerm;
    private IPermission isTaPerm;
    private IPermission isStudentPerm;

    public Roster(IAccount professor, IAccount ta, List<IAccount> students)
    {
        this.professor = professor;
        this.ta = ta;
        this.students = students;
        isProfessorPerm = Permissions.accountIs(professor);
        isTaPerm = Permissions.accountIs(ta);
        isStudentPerm = Permissions.accountIsAny(students);

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
}
