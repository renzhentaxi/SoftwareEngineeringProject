package Presentation;

import model.accounts.classes.Account;
import model.accounts.enums.AccountType;
import services.login.interfaces.ILoginToken;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public abstract class MainPanel extends AcmePanel
{

    protected ILoginToken token;
    protected Account userAccount;
    protected AccountType userType;
    protected String userName;

    private MainPanel prevPanel;

    public MainPanel(App app, ILoginToken token, MainPanel prevPanel)
    {
        super(app);
        this.token = token;
        this.userAccount = token.getAccount();
        this.userType = token.getAccountType();
        this.userName = token.getAccount().getUserName();

        this.prevPanel = prevPanel;
    }

    private JPanel menuPanel;

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
            backButton.addActionListener(this::onBack);
            addToMenu(backButton);
        }
    }

    private void onBack(ActionEvent event)
    {
        app.present(prevPanel);
    }

    protected void addToMenu(Component component)
    {
        menuPanel.add(component);
    }

}
