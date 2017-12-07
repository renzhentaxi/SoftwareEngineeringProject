package services.storage.model;

import model.accounts.interfaces.IAccount;
import services.storage.interfaces.AbstractCatalog;
import services.storage.inters.AccountInter;

import javax.json.JsonObject;
import java.util.Map;

public class AccountCatalog extends AbstractCatalog<AccountInter>
{

    public AccountCatalog(JsonObject data)
    {
        super(data);
    }

    @Override
    public AccountInter convert(JsonObject object)
    {
        return new AccountInter(object);
    }

}
