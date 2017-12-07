package Presentation;

import model.accounts.classes.Account;
import model.courses.classes.Course;
import services.login.interfaces.ILoginToken;

import javax.swing.*;
import java.util.List;

public class CourseListPanel extends MainPanel
{
    List<Course> courses;
    Account account;
    String name;

    public CourseListPanel(App app, ILoginToken token)
    {
        super(app, token);
        this.account = token.getAccount();
        this.name = account.getUserName();
        this.courses = account.getCourseList(token);
        assemble();
    }

    private DefaultListModel<String> listModel;

    @Override
    public void assemble()
    {
        listModel = new DefaultListModel<>();
        courses.forEach(course -> listModel.addElement(course.toString()));
        JList<String> courseJList = new JList<>(listModel);
        add(courseJList);
        courseJList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
    }

    @Override
    public void reset()
    {

    }
}
