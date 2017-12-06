package services.storage.inters;

import services.storage.interfaces.IJsonable;
import services.storage.model.Catalog;

public interface JsonInter<A> extends IJsonable
{
    void sync(Catalog catalog);

    A convert();


}
