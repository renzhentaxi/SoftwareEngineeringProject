package services.login.classes;

import model.accounts.classes.Account;
import model.accounts.enums.AccountType;
import model.accounts.interfaces.IAccount;
import services.login.exceptions.AlreadyLoggedInException;
import services.login.exceptions.InvalidLoginException;
import services.login.exceptions.LoginExpiredException;
import services.login.interfaces.ILoginManager;
import services.login.interfaces.ILoginToken;
import services.storage.model.Catalog;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class LoginManager implements ILoginManager
{
    private Map<String, String> passwords;

    private Set<LoginToken> activeLogins;
    private Account loginAdmin = new Account("login", "system", "$loginSystem", AccountType.admin, null);
    private LoginToken loginAdminToken = new LoginToken(loginAdmin);

    public LoginManager(Path passwordList)
    {
        passwords = readPasswordsFromFile(passwordList);
        activeLogins = new LinkedHashSet<>();

    }

    private Map<String, String> readPasswordsFromFile(Path file)
    {
        Map<String, String> map = new HashMap<>();
        try (BufferedReader reader = Files.newBufferedReader(file))
        {
            String currentLine = null;
            while ((currentLine = reader.readLine()) != null)
            {
                String[] split = currentLine.split(" ");
                if (split.length != 2) throw new RuntimeException("bad password list format!");
                map.put(split[0], split[1]);
            }
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }

        return map;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ILoginToken login(String userName, String password)
    {
        if (passwords.containsKey(userName) && passwords.get(userName).equals(password))
        {

            LoginToken t = new LoginToken(Catalog.getAccount(loginAdminToken, userName));
            if (activeLogins.contains(t)) throw new AlreadyLoggedInException();
            else activeLogins.add(t);
            return t;
        }
        throw new InvalidLoginException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logout(String userName)
    {

        LoginToken t = null;
        for (LoginToken token : activeLogins)
        {
            IAccount account = token.getAccount();
            if (account.getUserName().equals(userName))
            {
                t = token;
                break;
            }
        }

        if (t != null)
        {
            activeLogins.remove(t);
            t.logout();
        } else throw new LoginExpiredException();
    }
}
