package tests.provider;

import model.accounts.classes.Account;
import model.accounts.enums.AccountType;
import model.accounts.interfaces.IAccount;

import java.util.Iterator;

/**
 * syntax
 * name that contains student has type student
 * name that contains professor has type professor
 * name that contains ta has type ta
 * name that contains admin has type admin
 */
public class AccountProvider implements ISmartProvider<IAccount>
{
    public static AccountProvider provider = new AccountProvider();
    public static IAccount professor = provider.provideSingle("professor");
    public static IAccount ta = provider.provideSingle("ta");
    public static IAccount student = provider.provideSingle("student");
    public static IAccount admin = provider.provideSingle("admin");

    @Override
    public IAccount provideSingle(String name)
    {
        for (AccountType t : AccountType.values())
        {
            if (name.contains(t.name()))
                return new Account("", "", name, t, null);
        }
        throw new RuntimeException("cant determine type");
    }

}
