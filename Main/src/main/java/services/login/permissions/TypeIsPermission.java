package services.login.permissions;

import model.accounts.enums.AccountType;
import services.login.interfaces.ILoginToken;

public class TypeIsPermission implements IPermission
{
    private AccountType type;

    public TypeIsPermission(AccountType type)
    {
        this.type = type;
    }

    @Override
    public boolean hasPermission(ILoginToken requester)
    {
        return requester.getAccountType().equals(type);
    }

    @Override
    public String toString()
    {
        return "\nAccountType is: " + type;
    }
}
