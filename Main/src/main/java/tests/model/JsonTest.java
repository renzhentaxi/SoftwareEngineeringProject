package tests.model;

import model.accounts.classes.Account;
import model.courses.classes.Roster;
import org.junit.jupiter.api.Test;
import tests.StubFactory;
import tests.provider.AccountProvider;

import javax.json.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest
{
    @Test
    void test()
    {
        Account account = (Account) AccountProvider.student;
        Account account2 = (Account) AccountProvider.professor;

        try (JsonWriter writer = Json.createWriter(new FileWriter("../Data/account.json")))
        {
            JsonArray array = Json.createArrayBuilder().add(account.toJson()).add(account2.toJson()).build();
            writer.write(array);

        } catch (IOException e)
        {

        }

        try (JsonReader reader = Json.createReader(new FileReader("../Data/account.json")))
        {
            JsonArray obj = reader.readArray();

        } catch (IOException e)
        {

        }

        Roster roster = StubFactory.makeTestRoster();

    }
}
