package Presentation.assignments;

import Presentation.base.App;
import Presentation.base.MainPanel;
import model.assignments.classes.Assignment;
import model.courses.classes.Course;
import services.login.interfaces.ILoginToken;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.stream.Collectors;

public class AddAssignmentForm extends MainPanel
{
    private List<String> existingAssignmentNames;
    private Course course;

    public AddAssignmentForm(App app, ILoginToken token, MainPanel prevPanel, Course course)
    {
        super(app, token, prevPanel);
        this.existingAssignmentNames = course.getAssignments(token).stream().map(Object::toString).collect(Collectors.toList());
        this.course = course;
    }


    private JTextField assignmentNameTextField;
    private JTextArea assignmentDescTextField;
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
        assignmentDescTextField = new JTextArea(40, 40);
        add(assignDescLabel, 0, 2, 2);
        add(assignmentDescTextField, 0, 3, 2);

        //submit button
        JButton submitButton = new JButton("Submit");`
        submitButton.addActionListener(this::onSubmit);
        add(submitButton, 0, 4, 2);
        //cancel button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this::goBack);
        add(cancelButton, 0, 5, 2);

    }

    private void add(Component component, int x, int y, int width)
    {
        constraint.gridx = x;
        constraint.gridy = y;
        constraint.gridwidth = width;
        add(component, constraint);
    }

    private void onSubmit(ActionEvent event)
    {
        String name = assignmentNameTextField.getText();
        if (name.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Assignment Name can not be empty");
        } else if (existingAssignmentNames.contains(name))
        {
            JOptionPane.showMessageDialog(this, "Assignment Name: " + name + " already exist");
        } else
        {
            String desc = assignmentDescTextField.getText();
            Assignment assignment = new Assignment(name, desc, course);
            course.addAssignment(token, assignment);
            goBack(null);
        }

    }

}
