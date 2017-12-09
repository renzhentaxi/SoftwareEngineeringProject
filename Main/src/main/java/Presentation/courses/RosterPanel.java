package Presentation.courses;

import Presentation.base.App;
import Presentation.base.MainPanel;
import model.courses.classes.Roster;
import services.login.interfaces.ILoginToken;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RosterPanel extends MainPanel
{
    private List<Roster> rosters;

    public RosterPanel(App app, ILoginToken token, MainPanel prevPanel)
    {
        super(app, token, prevPanel);
        //this.rosters = userAccount.(token);
        assemble();
    }

    private GridBagConstraints constraint;

    @Override
    public void assemble()
    {
        super.assemble();
        clearMenu();
        constraint = new GridBagConstraints();
        setLayout(new GridBagLayout());

        //name field
        JLabel rosterLabel = new JLabel("List of rosters:");
        add(rosterLabel, 0, 0, 10);

        /*Description field
        JLabel assignDescLabel = new JLabel("Assignment Description:");
        assignmentDescTextField = new JTextArea(40,40);
        add(assignDescLabel, 0, 2, 2);
        add(assignmentDescTextField, 0, 3, 2);
        */
    }

    private void add(Component component, int x, int y, int width)
    {
        constraint.gridx = x;
        constraint.gridy = y;
        constraint.gridwidth = width;
        add(component, constraint);
    }
}
