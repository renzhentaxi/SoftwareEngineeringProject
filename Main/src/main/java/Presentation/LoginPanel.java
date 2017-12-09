package Presentation;

import Presentation.base.AcmePanel;
import Presentation.base.App;
import services.login.classes.LoginManager;
import services.login.exceptions.InvalidLoginException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * first screen displayed
 * allows user to login
 */
public class LoginPanel extends AcmePanel
{

    private GridBagConstraints constraint;
    private LoginManager loginManager;
    private JTextField userNameField;
    private JPasswordField passwordField;
    private JLabel userNameLabel;
    private JLabel passwordLabel;
    private JButton loginButton;
    private JButton quitButton;

    public LoginPanel(App app, LoginManager loginManager)
    {
        super(app, new GridBagLayout());

        this.loginManager = loginManager;
        assemble();
    }

    @Override
    public void assemble()
    {

        constraint = new GridBagConstraints();
        constraint.fill = GridBagConstraints.HORIZONTAL;

        userNameLabel = new JLabel("Username: ");
        add(userNameLabel, 0, 0, 1);

        userNameField = new JTextField(20);
        add(userNameField, 1, 0, 2);

        passwordLabel = new JLabel("password: ");
        add(passwordLabel, 0, 1, 1);

        passwordField = new JPasswordField(20);
        add(passwordField, 1, 1, 2);

        loginButton = new JButton("Login");
        add(loginButton, 0, 2, 3);
        loginButton.addActionListener(this::OnLogin);

        quitButton = new JButton("Quit");
        add(quitButton, 0, 3, 3);
        quitButton.addActionListener(this::OnQuit);
    }

    private void add(Component component, int x, int y, int width)
    {
        constraint.gridx = x;
        constraint.gridy = y;
        constraint.gridwidth = width;
        add(component, constraint);
    }

    private String getUserName()
    {
        return userNameField.getText();
    }

    private String getPassword()
    {
        return String.valueOf(passwordField.getPassword());
    }

    private void OnLogin(ActionEvent e)
    {
        String userName = getUserName();
        String password = getPassword();
        if (userName.equals(""))
        {
            JOptionPane.showMessageDialog(this, "UserName cant not be empty");
        } else if (password.equals(""))
        {
            JOptionPane.showMessageDialog(this, "Password cant not be empty");
        } else
        {
            try
            {
                app.OnLoginSuccess(loginManager.login(userName, password));
                System.out.println("good Login");

            } catch (InvalidLoginException exceptions)
            {
                System.out.println("bad Login");
                passwordField.setText("");
                JOptionPane.showMessageDialog(this, "Login Failed!!!! \nReasons could be: \n1. An Account with the name " + userName + " does not exist\n2. Wrong Password");
            }
        }
    }

    private void OnQuit(ActionEvent e)
    {
        app.dispose();
    }
}
