package tests.provider;

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
        return new LoginToken(AccountProvider.provider.provideSingle(name));
    }
}
