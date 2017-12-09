package tests.services;

import model.accounts.classes.Account;
import model.accounts.enums.AccountType;
import services.storage.model.Catalog;

import java.util.ArrayList;
import java.util.Random;

class StorageTest
{
    private static String[] firstNames = new String[]{"james", "john", "robert", "michael", "mary", "les"};
    static String[] students = new String[]{"taxi", "charles", "han", "melissa", "kaylee", "bruh", "taxco", "adam", "siri"};
    static String[] professors = new String[]{"baerde", "beach", "yoo"};
    static String[] tas = new String[]{"don", "juan", "dylee"};
    static String[] courses = new String[]{"math", "sci", "chem", "calc", "art", "music"};
    static String[] assignments = new String[]{"hw1", "hello world", "homewoooooork", "assssignment", "proooject"};
    private static Random random = new Random(0);

    public static void main(String[] args)
    {
//        makeTestAccounts(students, AccountType.student);
//        makeTestAccounts(professors, AccountType.professor);
//        makeTestAccounts(tas, AccountType.ta);
//        Catalog.catalog.addAccount("admin", new Account("admin", "admin", "admin", AccountType.admin, new ArrayList<>()));
//        Catalog.catalog.addAccount("student", new Account("student", "student", "student", AccountType.student, new ArrayList<>()));
//        Catalog.catalog.addAccount("ta", new Account("ta", "ta", "ta", AccountType.ta, new ArrayList<>()));
//        Catalog.catalog.addAccount("professor", new Account("professor", "professor", "professor", AccountType.professor, new ArrayList<>()));
        Catalog.catalog.commit();
    }

    public static void makeTestAccounts(String[] userNames, AccountType type)
    {
        for (String name : userNames)
        {
            String firstName = firstNames[random.nextInt(firstNames.length)];
            Account acc = new Account(firstName, name, name, type, new ArrayList<>());
            Catalog.catalog.addAccount(acc.getUserName(), acc);
        }
    }
}
