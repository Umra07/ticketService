package structures;

import java.util.HashMap;

public class CustomHashSet<E> {
    private final HashMap<E, Object> map;
    private static final Object PRESENT = new Object();

    public CustomHashSet() {
        map = new HashMap<>();
    }

    public boolean add(E element) {
        return map.put(element, PRESENT) != null;
    }

    public boolean contains(E element) {
        return map.containsKey(element);
    }

    public boolean remove(E element) {
        return map.remove(element) != null;
    }

    public int size() {
        return map.size();
    }

    public void iterate() {
        for(E element : map.keySet()) {
            System.out.println(element);
        }
    }
}
