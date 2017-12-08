package tests.services;

import org.junit.jupiter.api.Test;
import services.login.classes.LoginManager;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LoginManagerTest
{

    @Test
    void login()
    {

        Path p = Paths.get("Data" + File.separator + "passwordList");
        LoginManager manager = new LoginManager(p);
    }
}
