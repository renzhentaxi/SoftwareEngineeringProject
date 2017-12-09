package Presentation.courses;

import Presentation.base.App;
import Presentation.base.MainPanel;
import model.accounts.classes.Account;
import model.accounts.interfaces.IAccount;
import model.courses.classes.Course;
import model.courses.interfaces.IRoster;
import services.login.interfaces.ILoginToken;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class RosterPanel extends MainPanel
{
    private IRoster roster;
    private String professor;
    private  String ta;
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

    private GridBagConstraints constraint;

    @Override
    public void assemble()
    {
        super.assemble();
        constraint = new GridBagConstraints();
        content = new JPanel();
        add(content);
        content.setLayout(new GridBagLayout());

        //name field
        JLabel professorLabel = new JLabel("Professor:");

        JLabel professorNameLabel = new JLabel(professor);
        JLabel taLabel = new JLabel("Ta: ");
        JLabel studentsLabel = new JLabel("Students: ");

        add(professorLabel, 0, 0, 1);
        add(professorNameLabel, 1, 0, 1);
        add(taLabel, 0, 1, 1);

        add(studentsLabel, 0, 2, 0);

    }

    JPanel content;

    private void add(Component component, int x, int y, int width)
    {
        constraint.gridx = x;
        constraint.gridy = y;
        constraint.gridwidth = width;
        content.add(component, constraint);
    }
}
