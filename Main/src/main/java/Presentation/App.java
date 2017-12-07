package Presentation;

import services.login.classes.LoginManager;
import services.login.classes.LoginToken;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class App extends JFrame
{
    private LoginManager loginManager;

    private LoginPanel loginScreen;
    private LoginToken token;


    public App()
    {
        super("Acme");
        initSystems();

        createScreens();
        setSize(400, 500);
        setMinimumSize(new Dimension(400, 500));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }


    private void initSystems()
    {
        loginManager = new LoginManager("Data" + File.separator + "passwordList");

    }

    private void createScreens()
    {
        loginScreen = new LoginPanel(this, loginManager);
        present(loginScreen);
    }

    private void present(AcmePanel panel)
    {
        panel.reset();
        getContentPane().removeAll();
        getContentPane().add(panel, BorderLayout.CENTER);
        validate();
        repaint();
    }

    public void OnLoginSuccess(LoginToken token)
    {
        this.token = token;
        present(new CourseListPanel(this, token));
    }

    public void logOut()
    {
        loginManager.logout(token.getAccount().getUserName());
        present(loginScreen);
    }

    public static void main(String[] args)
    {
        App app = new App();
    }
}
