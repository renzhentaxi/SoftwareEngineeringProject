package Presentation.accounts;

import Presentation.base.App;
import Presentation.base.MainPanel;
import Presentation.courses.AssignmentListPanel;
import model.courses.classes.Course;
import services.login.interfaces.ILoginToken;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * displays course list
 */
public class CourseListPanel extends MainPanel
{
    private List<Course> courses;
    private JList<Course> courseJList;


    public CourseListPanel(App app, ILoginToken token)
    {
        super(app, token, null);
        this.courses = userAccount.getCourseList(token);
        assemble();
    }

    @Override
    public void assemble()
    {
        //tell super to assemble too
        super.assemble();

        // create the list model
        DefaultListModel<Course> listModel = new DefaultListModel<>();
        courses.forEach(listModel::addElement);
        //create the list view
        courseJList = new JList<>(listModel);
        courseJList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        add(courseJList);


        //adding view course button
        JButton viewCourse = new JButton("View Selected Course");
        viewCourse.addActionListener(this::OnViewCourse);
        addToMenu(viewCourse);
    }

    private void OnViewCourse(ActionEvent event)
    {
        Course selectedCourse = courseJList.getSelectedValue();

        if (selectedCourse != null)
        {
            app.present(new AssignmentListPanel(app, token, this, selectedCourse));
            System.out.println(userName + " is viewing course " + selectedCourse + " as " + userType);
        } else
        {
            JOptionPane.showMessageDialog(this, "Please select a course first");
        }
    }
}
