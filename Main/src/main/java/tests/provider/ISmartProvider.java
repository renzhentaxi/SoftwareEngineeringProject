package tests.provider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public interface ISmartProvider <T>
{
    default Iterator<T> provide(String names)
    {
        return provide(names.split(","));
    }

    default Iterator<T> provide(String... names)
    {
        return provideMany(names).iterator();
    }

    default List<T> provideMany(String names)
    {
        return provideMany(names.split(","));
    }

    default List<T> provideMany(String... names)
    {
        List<T> t = new ArrayList<>();
        for (String name : names)
        {
            t.add(provideSingle(name));
        }
        return t;
    }

    T provideSingle(String name);
}
