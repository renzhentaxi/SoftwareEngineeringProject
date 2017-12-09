package Presentation.courses;

import Presentation.base.App;
import Presentation.base.MainPanel;
import model.accounts.interfaces.IAccount;
import model.courses.classes.Course;
import model.courses.interfaces.IRoster;
import services.login.interfaces.ILoginToken;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * displays the roster
 */
public class RosterPanel extends MainPanel
{
    private IRoster roster;
    private String professor;
    private String ta;
    private List<String> students;

    public RosterPanel(App app, ILoginToken token, MainPanel prevPanel, Course course)
    {
        super(app, token, prevPanel);
        this.roster = course.getRoster(token);

        IAccount prof = roster.getProfessor(token);
        IAccount ta = roster.getTa(token);
        professor = prof.getFirstName() + " " + prof.getLastName();
        this.ta = ta.getFirstName() + " " + ta.getLastName();
        this.students = roster.getStudents(token).stream().map(iAccount -> iAccount.getFirstName() + " " + iAccount.getLastName()).collect(Collectors.toList());

        assemble();
    }

    @Override
    public void assemble()
    {
        super.assemble();

        JTextArea area = new JTextArea();
        area.setEditable(false);
        StringBuilder builder = new StringBuilder();
        builder.append("Professor: ").append(professor).append("\n");
        builder.append("Ta:").append(ta).append("\n");
        builder.append("Students:").append("\n");

        for (String stud : students)
        {
            builder.append(stud).append("\n");
        }

        area.setText(builder.toString());
        add(area, BorderLayout.CENTER);
    }

}
