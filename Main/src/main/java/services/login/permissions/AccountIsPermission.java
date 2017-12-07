package services.login.permissions;

import model.accounts.interfaces.IAccount;
import services.login.interfaces.ILoginToken;

public class AccountIsPermission implements IPermission
{
    private IAccount account;

    public AccountIsPermission(IAccount type)
    {
        this.account = type;
    }

    @Override
    public boolean hasPermission(ILoginToken requester)
    {
        return requester.getAccount().equals(account);
    }

    @Override
    public String toString()
    {
        return "\nAccount name is: " + account.getUserName();
    }
}
