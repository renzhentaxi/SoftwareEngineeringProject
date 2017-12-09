package services.storage.model;

import services.storage.interfaces.AbstractCatalog;
import services.storage.inters.AccountInter;

import javax.json.JsonObject;

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
