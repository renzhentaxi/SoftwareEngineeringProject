package Presentation;

import model.accounts.classes.Account;
import model.assignments.classes.Assignment;
import model.courses.classes.Course;
import services.login.interfaces.ILoginToken;

import javax.swing.*;
import java.util.List;

public class AssignmentListPanel extends MainPanel
{
    List<Assignment> assignmentList;
    Account account;
    String name;

    public AssignmentListPanel(App app, ILoginToken token, Course course)
    {
        super(app, token);
        this.account = token.getAccount();
        this.name = account.getUserName();
        this.assignmentList = course.getAssignments(token);

        assemble();
    }

    private DefaultListModel<String> listModel;

    private JList<String> assignmentListModel;

    @Override
    public void assemble()
    {
        listModel = new DefaultListModel<>();
        assignmentList.forEach(course -> listModel.addElement(course.toString()));
        assignmentListModel = new JList<>(listModel);
        add(assignmentListModel);

        setVisible(true);

    }

    @Override
    public void reset()
    {

    }
}
