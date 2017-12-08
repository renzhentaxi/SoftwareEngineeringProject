package Presentation.assignments;

import Presentation.base.App;
import Presentation.base.MainPanel;
import services.login.interfaces.ILoginToken;

import javax.swing.*;
import java.awt.*;

public class AddAssignmentForm extends MainPanel
{

    public AddAssignmentForm(App app, ILoginToken token, MainPanel prevPanel)
    {
        super(app, token, prevPanel);
    }


    private JTextField assignmentNameTextField;
    private JTextField assignmentDescTextField;
    private GridBagConstraints constraint;

    @Override
    public void assemble()
    {
        super.assemble();
        clearMenu();
        constraint = new GridBagConstraints();
        setLayout(new GridBagLayout());

        //name field
        JLabel assignNameLabel = new JLabel("Assignment Name:");
        assignmentNameTextField = new JTextField(20);
        add(assignNameLabel, 0, 0, 2);
        add(assignmentNameTextField, 0, 1, 2);


        //description field
        JLabel assignDescLabel = new JLabel("Assignment Description:");
        assignmentDescTextField = new JTextField(20);
        add(assignDescLabel, 0, 2, 2);
        add(assignmentDescTextField, 0, 3, 2);

        //submit button
        JButton submitButton = new JButton("Submit");
        add(submitButton, 0,4,1);
        //cancel button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this::goBack);
        add(cancelButton, 1, 4, 1);

    }

    private void add(Component component, int x, int y, int width)
    {
        constraint.gridx = x;
        constraint.gridy = y;
        constraint.gridwidth = width;
        add(component, constraint);
    }
}
