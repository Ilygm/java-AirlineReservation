package MVC.M;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MapHandler<K, T> {
    public Map<K, T> database = new HashMap<>();

    public boolean addEntry(K k, T t) {
        if (database.containsKey(k)) return false;
        else database.put(k, t);
        return true;
    }

    public boolean removeEntry(K k) {
        if (database.containsKey(k)) {
            database.remove(k);
            return true;
        } else return false;
    }

    public T findEntry(K k) {
        return database.get(k);
    }

    public boolean entryExists(K k) {
        return database.containsKey(k);
    }

    public List<T> filterEntries(T t) {
        List<T> filtered = new ArrayList<>();
        database.values().stream()
                .filter(v -> v.equals(t))
                .forEach(filtered::add);
        return filtered;
    }
}
