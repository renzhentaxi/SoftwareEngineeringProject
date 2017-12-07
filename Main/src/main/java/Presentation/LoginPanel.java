package Presentation;

import services.login.classes.LoginManager;
import services.login.exceptions.InvalidLoginException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LoginPanel extends JPanel
{

    private LoginManager loginManager;
    private App app;

    private JTextField userNameField;
    private JPasswordField passwordField;
    private JLabel userNameLabel;
    private JLabel passwordLabel;
    private JButton loginButton;
    private JButton quitButton;
    GridBagConstraints constraint;

    public LoginPanel(App app, LoginManager loginManager)
    {
        super(new GridBagLayout());
        this.app = app;
        this.loginManager = loginManager;

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

    private void reset()
    {
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
        System.out.println(loginManager);
        try
        {
            loginManager.login(getUserName(), getPassword());
            System.out.println("good login");

        } catch (InvalidLoginException exceptions)
        {
            System.out.println("bad Login");
        }
    }

    private void OnQuit(ActionEvent e)
    {
        app.dispose();
    }
}
