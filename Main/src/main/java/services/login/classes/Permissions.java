package services.login.classes;

import model.accounts.enums.AccountType;
import model.accounts.interfaces.IAccount;
import services.login.interfaces.IPermission;

import java.util.Arrays;
import java.util.List;

public class Permissions
{
    public static IPermission isAdmin = (requester) -> requester.getAccountType().equals(AccountType.admin);

    public static IPermission typeIs(AccountType type)
    {
        return (requester) -> requester.getAccountType().equals(type);
    }

    public static IPermission accountIs(IAccount account)
    {
        return (requester) -> requester.getAccount().equals(account);
    }

    public static IPermission accountIsAny(List<IAccount> account)
    {
        return (requester -> account.contains(requester.getAccount()));
    }

    public static IPermission and(IPermission... permissions)
    {
        return (requester) -> Arrays.stream(permissions).allMatch(permission -> permission.hasPermission(requester));
    }

    public static IPermission or(IPermission... permissions)
    {
        return (requester) -> Arrays.stream(permissions).anyMatch(permission -> permission.hasPermission(requester));
    }

}
