package services.storage.interfaces;

public interface ICatalog<T>
{

    T get(String name);

    boolean has(String name);
}
