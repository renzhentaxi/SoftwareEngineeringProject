package Presentation.assignments;

import Presentation.base.App;
import Presentation.base.MainPanel;
import model.accounts.enums.AccountType;
import model.assignments.classes.Assignment;
import services.login.interfaces.ILoginToken;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * displays an assignment
 * depending on the user type the functionality of this window is changed
 * for a student, it will only display a grade if the assignment is graded + a view description button
 * for ta, they will have access to enter grade
 * for prof, they will have access to enter grade/modify grade/ clear grade
 */
public class AssignmentPanel extends MainPanel
{
    private JList<Grade> gradeListView;
    private DefaultListModel<Grade> gradeListModel;
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
            gradeListModel = new DefaultListModel<>();
            assignment.getGrades(token).entrySet().stream().map(Grade::new).forEach(gradeListModel::addElement);

            gradeListView = new JList<>(gradeListModel);
            add(gradeListView);
            JButton enterGradeButton = new JButton("Enter Grade");
            enterGradeButton.addActionListener(this::onEnter);
            addToMenu(enterGradeButton);
        }

        if (userType == AccountType.professor)
        {
            JButton modifyGradeButton = new JButton("Modify");
            modifyGradeButton.addActionListener(this::onModify);
            JButton clearGradeButton = new JButton("Clear");
            clearGradeButton.addActionListener(this::onClear);
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
        Grade grade = gradeListView.getSelectedValue();
        if (grade == null)
        {
            JOptionPane.showMessageDialog(this, "Please select a student first");
        } else if (!grade.isGraded())
        {
            JOptionPane.showMessageDialog(this, "It is not graded!! Use Enter Grade instead");
        } else
        {
            try
            {
                float newGrade = Float.valueOf(JOptionPane.showInputDialog(this, "Enter the new grade"));
                if (newGrade < 0 || newGrade > 100)
                {
                    JOptionPane.showMessageDialog(this, "Please enter a number between 0-100");
                } else
                {
                    assignment.modifyGrade(token, grade.account, newGrade);
                    reloadData();
                    System.out.println(userName + " modified the grade to " + newGrade + " for " + grade.account.getUserName() + " on assignment: " + assignment);
                }
            } catch (NumberFormatException e)
            {
                JOptionPane.showMessageDialog(this, "Please enter numbers only");
            } catch (NullPointerException ignored)
            {
            }
        }
    }

    private void onClear(ActionEvent event)
    {
        Grade grade = gradeListView.getSelectedValue();
        if (grade == null)
        {
            JOptionPane.showMessageDialog(this, "Please select a student first");
        } else if (!grade.isGraded())
        {
            JOptionPane.showMessageDialog(this, "Can not clear!! The student is not graded!!");
        } else if (JOptionPane.showConfirmDialog(this, "Are you sure want to clear the grade?") == 0)
        {
            assignment.clearGrade(token, grade.account);
            reloadData();
            System.out.println(userName + " cleared the grade for " + grade.account.getUserName() + " on assignment: " + assignment);
        }
    }

    private void reloadData()
    {
        gradeListModel.clear();
        assignment.getGrades(token).entrySet().stream().map(Grade::new).forEach(gradeListModel::addElement);
    }

    private void onEnter(ActionEvent event)
    {
        Grade grade = gradeListView.getSelectedValue();
        if (grade == null)
        {
            JOptionPane.showMessageDialog(this, "Please select a student first");
        } else if (grade.isGraded())
        {
            JOptionPane.showMessageDialog(this, "It is already graded!!");
        } else
        {
            try
            {
                float newGrade = Float.valueOf(JOptionPane.showInputDialog(this, "Enter the grade"));
                if (newGrade < 0 || newGrade > 100)
                {
                    JOptionPane.showMessageDialog(this, "Please enter a number between 0-100");
                } else
                {
                    assignment.enterGrade(token, grade.account, newGrade);
                    reloadData();
                    System.out.println(userName + " entered " + newGrade + " for " + grade.account.getUserName() + " on assignment: " + assignment);
                }
            } catch (NumberFormatException e)
            {
                JOptionPane.showMessageDialog(this, "Please enter numbers only");
            } catch (NullPointerException ignored)
            {
            }
        }
    }
}
