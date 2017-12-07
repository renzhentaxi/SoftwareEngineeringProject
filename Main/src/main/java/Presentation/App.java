package Presentation;

import services.login.classes.LoginManager;

import javax.swing.*;
import java.io.File;

public class App extends JFrame
{
    private LoginManager loginManager;

    private LoginPanel panel;

    public App()
    {
        initSystems();
        
        panel = new LoginPanel(this, loginManager);
        add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setVisible(true);
    }


    private void initSystems()
    {
        loginManager = new LoginManager("Data" + File.separator + "passwordList");

    }
    public static void main(String[] args)
    {
        App app = new App();
    }
}
