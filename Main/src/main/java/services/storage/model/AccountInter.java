package services.storage.model;

import model.accounts.classes.Account;
import model.accounts.enums.AccountType;

import javax.json.JsonObject;
import javax.json.JsonValue;
import java.util.List;
import java.util.stream.Collectors;

public class AccountInter extends Account implements JsonInter<Account>
{
    private List<String> courseNameList;
    private boolean isSync;

    public AccountInter(JsonObject object)
    {
        this.firstName = object.getString("firstName");
        this.lastName = object.getString("lastName");
        this.userName = object.getString("userName");
        this.accountType = AccountType.valueOf(object.getString("accountType"));
        this.courseNameList = object.getJsonArray("courseList").stream().map(JsonValue::toString).collect(Collectors.toList());
        isSync = false;
    }


    @Override

    public void sync(Catalog catalog)
    {
        this.courseList = courseNameList.stream().map(catalog::getCourse).collect(Collectors.toList());
        isSync = true;
    }

    @Override
    public Account convert()
    {
        if (!isSync) throw new RuntimeException("need to sync object before converting");
        return this;
    }
}
