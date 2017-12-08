package Presentation;

import model.accounts.classes.Account;
import model.accounts.enums.AccountType;
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

        JButton button = new JButton("View Selected Course");
        button.addActionListener(actionEvent ->
        {
            int index = courseJList.getSelectedIndex();
            if (index != -1)
            {
                Course course = courses.get(index);
                AccountType type = token.getAccountType();
                switch (type)
                {
                    case admin:
                    case professor:
                    case student:
                    case ta:
                        app.present(new AssignmentListPanel(app, token, course));
                        break;
                }
                System.out.println("Viewing " + course + " as " + type);
            } else
            {
                System.out.println("You havent selected anything");
            }
        });

        addToMenu(button);
    }

    @Override
    public void reset()
    {

    }
}
