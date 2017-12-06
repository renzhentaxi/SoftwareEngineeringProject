package services.storage.interfaces;

import java.util.Map;

public abstract class AbstractCatalog<T> implements ICatalog<T>
{
    protected Map<String, T> map;


    public AbstractCatalog(Map<String, T> data)
    {
        map = data;
    }

    @Override
    public T get(String name)
    {
        if (!map.containsKey(name)) throw new RuntimeException(name + "is not in catalog");
        return map.get(name);
    }

    @Override
    public boolean has(String name)
    {
        return map.containsKey(name);
    }
}
