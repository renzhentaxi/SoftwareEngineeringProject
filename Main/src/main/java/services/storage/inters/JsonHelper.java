package services.storage.inters;

import javax.json.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class JsonHelper
{
    public static <T> JsonArray toJsonArray(List<T> list, IToString<T> toStringMethod)
    {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        list.forEach(t -> builder.add(toStringMethod.toString(t)));
        return builder.build();
    }

    public static <F> JsonArray toJsonArray(List<F> list, Converter<F, JsonValue> converter)
    {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        list.forEach(t -> builder.add(converter.convert(t)));
        return builder.build();
    }

    public static <T> List<T> toList(JsonArray array, StringConverter<T> converter)
    {
        List<T> list = new ArrayList<>();
        array.forEach(jsonValue -> list.add(converter.convert(jsonValue.toString())));
        return list;
    }

    public static <V> Map<String, V> toMap(JsonObject object, StringConverter<V> converter)
    {
        Map<String, V> map = new HashMap<>();
        object.forEach((s, jsonValue) -> map.put(s, converter.convert(jsonValue.toString())));
        return map;
    }

    interface IToString<T>
    {
        String toString(T object);
    }


    interface Converter<F, T>
    {
        T convert(F from);
    }


    public interface StringConverter<T>
    {
        T convert(String s);
    }
}
