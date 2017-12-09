package Presentation.courses;

import Presentation.accounts.CourseListPanel;
import Presentation.assignments.AssignmentPanel;
import Presentation.base.App;
import Presentation.base.MainPanel;
import model.accounts.enums.AccountType;
import model.assignments.classes.Assignment;
import model.courses.classes.Course;
import services.login.interfaces.ILoginToken;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class AssignmentListPanel extends MainPanel
{
    private Course course;

    public AssignmentListPanel(App app, ILoginToken token, CourseListPanel coursePanel, Course course)
    {
        super(app, token, coursePanel);
        this.course = course;
    }


    private JList<Assignment> assignmentJList;
    private DefaultListModel<Assignment> assignListModel;

    @Override
    public void assemble()
    {
        super.assemble();
        List<Assignment> assignmentList = course.getAssignments(token);
        //make assignment list model
        assignListModel = new DefaultListModel<>();
        assignmentList.forEach(assignListModel::addElement);
        //make assignment list view
        assignmentJList = new JList<>(assignListModel);
        assignmentJList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        add(assignmentJList);


        //a view assignment list view to view the assignment
        JButton viewAssignmentButton = new JButton("View Assignment");
        viewAssignmentButton.addActionListener(this::onViewAssignment);
        addToMenu(viewAssignmentButton);

        //if ta or professor add roster button
        if (userType == AccountType.professor || userType == AccountType.ta)
        {
            JButton viewRosterButton = new JButton("Roster");
            viewRosterButton.addActionListener(this::onViewRoster);
            addToMenu(viewRosterButton);
        }
        //if professor add add, remove assignment button
        if (userType == AccountType.professor)
        {
            //add assignment
            JButton addAssignButton = new JButton("Add Assignment");
            addAssignButton.addActionListener(this::onAddAssignment);
            addToMenu(addAssignButton);

            //remove assignment
            JButton delAssignButton = new JButton("Remove Assignment");
            delAssignButton.addActionListener(this::onDeleteAssignment);
            addToMenu(delAssignButton);
        }
    }

    private void onViewAssignment(ActionEvent event)
    {
        Assignment selectedAssignment = assignmentJList.getSelectedValue();

        if (selectedAssignment != null)
        {
            System.out.println(userName + " is viewing assignment " + selectedAssignment + " as " + userType);
            app.present(new AssignmentPanel(app, token, this, selectedAssignment));
        } else
        {
            JOptionPane.showMessageDialog(this, "Please select an assignment first");
        }
    }

    private void onViewRoster(ActionEvent event)
    {
        app.present(new RosterPanel(app, token, this));
        System.out.println(userName + " is viewing roster of " + course + "as " + userType);
    }

    private void onDeleteAssignment(ActionEvent event)
    {
        Assignment selectedAssignment = assignmentJList.getSelectedValue();

        if (selectedAssignment != null)
        {
            System.out.println(userName + " is attempting to remove assignment " + selectedAssignment + " as " + userType);
            if (selectedAssignment.isGradedAny(token))
            {
                JOptionPane.showMessageDialog(this, "Can not remove this assignment because it has been graded");
            } else
            {
                course.removeAssignment(token, selectedAssignment);
                System.out.println("Assignment is removed");
                assignListModel.removeElement(selectedAssignment);
            }
        } else
        {
            JOptionPane.showMessageDialog(this, "Please select an assignment first!!!");
        }
    }

    private void onAddAssignment(ActionEvent event)
    {
        System.out.println(userName + " is attempting to add a new assignment as " + userType);
        app.present(new AddAssignmentForm(app, token, this, course));
    }
}
