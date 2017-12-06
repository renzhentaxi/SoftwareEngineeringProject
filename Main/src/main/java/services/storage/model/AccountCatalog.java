package services.storage.model;

import model.accounts.interfaces.IAccount;
import services.storage.interfaces.AbstractCatalog;
import services.storage.inters.AccountInter;

import java.util.Map;

public class AccountCatalog extends AbstractCatalog<AccountInter>
{
    public AccountCatalog(Map<String, AccountInter> data)
    {
        super(data);
    }
}
