package services.storage.interfaces;

import services.storage.inters.JsonInter;
import services.storage.model.Catalog;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCatalog<T extends JsonInter> implements IJsonable
{
    protected Map<String, T> map;

    public AbstractCatalog(JsonObject data)
    {
        map = new HashMap<>();

        data.forEach((name, jsonValue) -> map.put(name, convert(jsonValue.asJsonObject())));
    }

    @Override
    public JsonObject toJson()
    {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        for (Map.Entry<String, T> entry : map.entrySet())
        {
            String key = entry.getKey();
            IJsonable jsonable = entry.getValue();
            builder.add(key, jsonable.toJson());
        }
        return builder.build();
    }

    public abstract T convert(JsonObject object);


    public T get(String name)
    {
        if (!map.containsKey(name))
        {
            throw new RuntimeException(name + "is not in catalog");
        }
        return map.get(name);
    }

    public boolean has(String name)
    {
        return map.containsKey(name);
    }

    public void Sync(Catalog catalog)
    {
        for (T obj : map.values())
        {


            obj.sync(catalog);
        }
    }

    public void add(String name, T obj)
    {
        if (has(name))
        {
            throw new RuntimeException("already exist " + name);
        }


        map.put(name, obj);
    }

}
