package tests.model.Account;

import model.accounts.classes.Account;
import model.accounts.enums.AccountType;
import model.accounts.interfaces.IAccount;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import tests.provider.AccountProvider;

import javax.json.JsonObject;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
@Tag("account")
public class toJsonTest
{

    static Iterator<IAccount> accounts()
    {
        return AccountProvider.provider.provide("student, professor, ta");
    }

    @ParameterizedTest
    @MethodSource(names = "accounts")
    void toJson(IAccount account)
    {
        Account a = (Account) account;

        JsonObject json = a.toJson();
        assertEquals(account.getUserName(), json.getString("userName"));
        assertEquals(account.getFirstName(), json.getString("firstName"));
        assertEquals(account.getLastName(), json.getString("lastName"));
        assertEquals(account.getAccountType(), AccountType.valueOf(json.getString("accountType")));

    }
}
