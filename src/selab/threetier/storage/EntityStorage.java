package selab.threetier.storage;

import selab.threetier.logic.Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class EntityStorage<T extends Entity> {

    private HashMap<Integer, T> list = new HashMap<Integer, T>();
    private int counter = 0;

    public int addOrUpdate(T item) {
        int id = item.getId();
        if (id == 0) {
            item.setId(++counter);
            id = counter;
        }

        if (list.containsKey(id))
            list.replace(id, item);
        else
            list.put(id, item);
        return id;
    }

    public boolean remove(int id) {
        T result = list.remove(id);
        return result != null;
    }

    public ArrayList<T> getAll() {
        ArrayList<T> items = new ArrayList<T>(list.values());
        Collections.sort(items);
        return items;
    }

    public T get(int id) {
        return list.get(id);
    }
}
