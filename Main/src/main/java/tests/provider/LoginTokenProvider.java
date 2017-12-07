package tests.provider;

import model.accounts.classes.Account;
import services.login.classes.LoginToken;
import services.login.interfaces.ILoginToken;

public class LoginTokenProvider implements ISmartProvider<ILoginToken>
{
    public static LoginTokenProvider provider = new LoginTokenProvider();
    public static ILoginToken admin = provider.provideSingle("admin");
    public static ILoginToken professor = provider.provideSingle("professor");
    public static ILoginToken student = provider.provideSingle("student");

    @Override
    public ILoginToken provideSingle(String name)
    {
        return new LoginToken((Account) AccountProvider.provider.provideSingle(name));
    }
}
