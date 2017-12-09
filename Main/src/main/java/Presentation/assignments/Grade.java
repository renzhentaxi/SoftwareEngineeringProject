package Presentation.assignments;

import model.accounts.interfaces.IAccount;

import java.util.Map;

/**
 * a simply class that "wraps" a map.entry object to allow custom toString method that is used by JList
 */
public class Grade
{
    public float grade;
    public IAccount account;

    public Grade(Map.Entry<IAccount, Float> grade)
    {
        this.account = grade.getKey();
        this.grade = grade.getValue();
    }

    @Override
    public String toString()
    {
        return account.getFirstName() + " " + account.getLastName() + " : " + (isGraded()? grade: "ungraded");
    }

    public boolean isGraded()
    {
        return grade >= 0;
    }
}

