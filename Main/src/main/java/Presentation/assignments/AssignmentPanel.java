package Presentation.assignments;

import Presentation.base.App;
import Presentation.base.MainPanel;
import model.accounts.enums.AccountType;
import model.assignments.classes.Assignment;
import services.login.interfaces.ILoginToken;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Map;

public class AssignmentPanel extends MainPanel
{
    private Assignment assignment;
    private String assignmentName;
    private String assignmentDesc;

    public AssignmentPanel(App app, ILoginToken token, MainPanel prevPanel, Assignment assignment)
    {
        super(app, token, prevPanel);
        this.assignment = assignment;
        this.assignmentName = assignment.getName(token);
        this.assignmentDesc = assignment.getDescription(token);
    }

    @Override
    public void assemble()
    {
        super.assemble();

        //view description
        JButton viewDescription = new JButton("Description");
        viewDescription.addActionListener(this::onViewDescription);
        addToMenu(viewDescription);

        //if student
        if (userType == AccountType.student)
        {
            if (assignment.isGraded(token, userAccount))
            {
                JPanel p = new JPanel();
                p.setLayout(new FlowLayout());

                float grade = assignment.getGrade(token, userAccount);
                JLabel gradeLabel = new JLabel("Grade: ");
                p.add(gradeLabel);
                JLabel gradeField = new JLabel(Float.toString(grade));
                p.add(gradeField);
                add(p, BorderLayout.CENTER);
            } else
            {
                JPanel p = new JPanel();
                p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
                JLabel notGraded = new JLabel("Assignment is not graded yet");
                notGraded.setAlignmentX(CENTER_ALIGNMENT);
                notGraded.setAlignmentY(CENTER_ALIGNMENT);
                p.add(notGraded);
                add(p, BorderLayout.CENTER);
            }
        }

        if (userType == AccountType.professor || userType == AccountType.ta)
        {
            ListModel<Map.Entry<String, Float>> gradeListModel = new DefaultListModel<>();
            JList<Map.Entry<String,Float>> gradeListView = new JList<>();

            JButton enterGradeButton = new JButton("Enter Grade");
            addToMenu(enterGradeButton);
        }

        if (userType == AccountType.professor)
        {
            JButton modifyGradeButton = new JButton("Modify");
            JButton clearGradeButton = new JButton("Clear");

            addToMenu(modifyGradeButton);
            addToMenu(clearGradeButton);
        }
    }

    private void onViewDescription(ActionEvent event)
    {
        app.present(new AssignmentDescriptionPanel(app, token, this, assignmentName, assignmentDesc));
    }

    private void onModify(ActionEvent event)
    {

    }

    private void onClear(ActionEvent event)
    {

    }

    private void onEnter(ActionEvent event)
    {

    }
}
