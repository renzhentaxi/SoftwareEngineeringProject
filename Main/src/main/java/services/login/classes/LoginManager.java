package services.login.classes;

import model.accounts.interfaces.IAccount;
import services.login.exceptions.AlreadyLoggedInException;
import services.login.exceptions.InvalidLoginException;
import services.login.exceptions.LoginExpiredException;
import services.login.interfaces.ILoginManager;
import services.storage.model.Catalog;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class LoginManager implements ILoginManager
{
    private Map<String, String> passwords;
    private Set<LoginToken> activeLogins;
    private Catalog catalog;

    public LoginManager(Path passwordList)
    {
        catalog = Catalog.catalog;
        passwords = readPasswordsFromFile(passwordList);
        activeLogins = new LinkedHashSet<>();
    }

    public LoginManager(String passwordlist)
    {
        this(Paths.get(passwordlist));
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
                String userName = split[0];
                String password = split[1];

                if (!catalog.hasAccount(userName))
                    throw new RuntimeException("no account with that UserName" + userName);
                map.put(userName, password);
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
    public LoginToken login(String userName, String password)
    {
        if (passwords.containsKey(userName) && passwords.get(userName).equals(password))
        {

            LoginToken t = new LoginToken(catalog.getAccount(userName));
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
