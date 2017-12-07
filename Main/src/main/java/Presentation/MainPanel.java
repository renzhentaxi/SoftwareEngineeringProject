package Presentation;

import services.login.interfaces.ILoginToken;

import javax.swing.*;
import java.awt.*;

public abstract class MainPanel extends AcmePanel
{

    protected ILoginToken token;
    private JPanel menuPanel;

    public MainPanel(App app, ILoginToken token)
    {
        super(app);
        this.token = token;
        selfAssemble();
    }

    private void selfAssemble()
    {
        setLayout(new BorderLayout());
        menuPanel = new JPanel();
        menuPanel.setLayout(new FlowLayout());

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(actionEvent -> app.logOut());
        add(menuPanel, BorderLayout.NORTH);
        addToMenu(logoutButton);
    }


    public void addToMenu(Component component)
    {
        menuPanel.add(component);
    }

}
