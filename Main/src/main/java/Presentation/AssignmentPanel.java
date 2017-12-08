package Presentation;

import model.assignments.classes.Assignment;
import services.login.interfaces.ILoginToken;

public class AssignmentPanel extends MainPanel
{
    public AssignmentPanel(App app, ILoginToken token, MainPanel prevPanel, Assignment assignment)
    {
        super(app, token, prevPanel);
    }
}
