package services.login.classes;

import model.accounts.classes.Account;
import model.accounts.enums.AccountType;
import model.accounts.interfaces.IAccount;
import services.login.interfaces.IPermission;

import java.util.List;

public class Permissions
{
    public static IPermission isAdmin = typeIs(AccountType.admin);

    public static IPermission typeIs(AccountType type)
    {
        return new TypeIsPermission(type);
    }

    public static IPermission accountIs(IAccount account)
    {
        return new AccountIsPermission(account);
    }

    public static IPermission accountIsAny(List<IAccount> account)
    {

        return new AnyPermission(account
                .stream()
                .map(Permissions::accountIs)
                .toArray(IPermission[]::new));
    }

    public static IPermission and(IPermission... permissions)
    {
        return new AllPermission(permissions);
    }

    public static IPermission or(IPermission... permissions)
    {
        return new AnyPermission(permissions);
    }

}
