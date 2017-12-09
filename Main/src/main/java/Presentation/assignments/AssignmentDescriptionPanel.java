package Presentation.assignments;

import Presentation.base.App;
import Presentation.base.MainPanel;
import services.login.interfaces.ILoginToken;

import javax.swing.*;

public class AssignmentDescriptionPanel extends MainPanel
{
    private String assignmentName;
    private String assignmentDesc;

    public AssignmentDescriptionPanel(App app, ILoginToken token, MainPanel prevPanel, String assignmentName, String assignmentDesc)
    {
        super(app, token, prevPanel);

        this.assignmentName = assignmentName;
        this.assignmentDesc = assignmentDesc;
    }

    @Override
    public void assemble()
    {
        super.assemble();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel name = new JLabel("Name: " + assignmentName);
        JTextArea description = new JTextArea(30, 10);
        description.setEditable(false);
        description.setText(assignmentDesc);

        add(name);
        add(description);
    }
}
