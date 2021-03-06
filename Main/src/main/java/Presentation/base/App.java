package Presentation.base;

import Presentation.LoginPanel;
import Presentation.accounts.CourseListPanel;
import services.login.classes.LoginManager;
import services.login.classes.LoginToken;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * the main entry point
 * displays login window first
 */
public class App extends JFrame
{
    private LoginManager loginManager;

    private LoginPanel loginScreen;
    private LoginToken token;

    private App()
    {
        super("Acme");
        initSystems();

        createScreens();
        setMinimumSize(new Dimension(800, 480));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public static void main(String[] args)
    {
        App app = new App();
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

    /**
     * allows the app to display the given panel
     * @param panel the panel to display
     */
    public void present(AcmePanel panel)
    {
        panel.reload();
        getContentPane().removeAll();
        getContentPane().add(panel, BorderLayout.CENTER);
        validate();
        repaint();
        pack();
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
}
