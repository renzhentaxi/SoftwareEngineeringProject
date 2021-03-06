package Presentation.base;

import model.accounts.classes.Account;
import model.accounts.enums.AccountType;
import services.login.interfaces.ILoginToken;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * a basic class that has builtin logout and back functionality
 */
public abstract class MainPanel extends AcmePanel
{

    protected ILoginToken token;
    protected Account userAccount;
    protected AccountType userType;
    protected String userName;

    private MainPanel prevPanel;
    private JPanel menuPanel;

    protected MainPanel(App app, ILoginToken token, MainPanel prevPanel)
    {
        super(app);
        this.token = token;
        this.userAccount = token.getAccount();
        this.userType = token.getAccountType();
        this.userName = token.getAccount().getUserName();

        this.prevPanel = prevPanel;
    }

    @Override
    public void assemble()
    {
        setLayout(new BorderLayout());
        //create menu
        menuPanel = new JPanel();
        menuPanel.setLayout(new FlowLayout());
        add(menuPanel, BorderLayout.NORTH);

        //create logout Button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(actionEvent -> app.logOut());
        addToMenu(logoutButton);

        //create a back button if there is a prevPanel
        if (prevPanel != null)
        {
            JButton backButton = new JButton("Back");
            backButton.addActionListener(this::goBack);
            addToMenu(backButton);
        }
    }

    protected void goBack(ActionEvent event)
    {
        app.present(prevPanel);
    }

    /**
     * add a button to the menu
     * @param component the button
     */
    protected void addToMenu(Component component)
    {
        menuPanel.add(component);
    }

    /**
     * clear the menu
     */
    protected void clearMenu()
    {
        menuPanel.removeAll();
    }

}
