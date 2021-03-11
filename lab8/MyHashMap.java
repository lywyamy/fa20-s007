import java.util.*;

public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int INIT_INITIALSIZE = 16;
    private static final double INIT_LOADFACTORY = 0.75;
    private int resizeFactor = 2;
    private double loadFactor; // ratio of size over numberOfBucket
    private int n = 0; // number of key-value pairs in the map
    private int size; // size of the hash table aka the number of buckets
    private LinkedList<Pair<K, V>>[] items;

    private class Pair<K, V> {
        private K key;
        private V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }


    // Constructor 1: initialize an empty table with default initialSize of buckets
    public MyHashMap() {
        this(INIT_INITIALSIZE, INIT_LOADFACTORY);
    }

    // Constructor 2: initializes an empty table with the given initialSize of buckets
    public MyHashMap(int initialSize) {
        this(initialSize, INIT_LOADFACTORY);
    }

    // Constructor 3
    public MyHashMap(int initialSize, double loadFactor) {
        this.size = initialSize;
        this.loadFactor = loadFactor;
        items = (LinkedList<Pair<K, V>>[]) new LinkedList[size];
    }

    // If the currentLoad exceed the loadFactor, size up the number of buckets.
    // Size down is not required.
    public void resize() {
        MyHashMap<K, V> newMap = new MyHashMap<>(size * resizeFactor, loadFactor);
        for (Pair<K, V> p : pairSet()) {
            newMap.put(p.key, p.value);
        }
        this.items = newMap.items;
        this.n = newMap.n;
        this.size = newMap.size;
    }

    private Set<Pair<K, V>> pairSet() {
        HashSet<Pair<K, V>> pairSet = new HashSet<>();
        for (int i = 0; i < size; i++) {
            if (items[i] != null) {
                for (Pair<K, V> p : items[i]) {
                    pairSet.add(p);
                }
            }
        }
        return pairSet;
    }

    @Override
    public void clear() {
        n = 0;
        items = (LinkedList<Pair<K, V>>[]) new LinkedList[size];
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) { throw new IllegalArgumentException(); }
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        if (key == null) { throw new IllegalArgumentException(); }
        int index = hash(key, size);
        Pair<K, V> p = find(items[index], key);
        if (p != null) { return p.value; }
        return null;
    }

    @Override
    public int size() {
        return n;
    }

    @Override
    public void put(K key, V value) {
        if (key == null || value == null) { throw new IllegalArgumentException(); }
        int index = hash(key, size);
        if (containsKey(key)) {
            for (Pair<K, V> p : items[index]) {
                if (p.key.equals(key)) {
                    p.value = value;
                }
            }
        } else {
            Pair<K, V> pair = new Pair(key, value);
            if(items[index] == null) {
                items[index] = new LinkedList<>();
                items[index].add(pair);
            } else {
                LinkedList<Pair<K, V>> item = items[index];
                item.add(pair);
            }
            n++;

        }
        if (n * 1.0 / size > loadFactor) { resize(); }
    }

    @Override
    public Set<K> keySet() {
        HashSet<K> keySet = new HashSet<>();
        for (int i = 0; i < size; i++) {
            if (items[i] != null) {
                for (Pair<K, V> p : items[i]) {
                    keySet.add(p.key);
                }
            }
        }
        return keySet;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    // Returns the index of the ith bucket that the key should be placed into
    private int hash(K key, int size) {
        return Math.floorMod(key.hashCode(), size);
    }

    // Returns the key-value pair from a certain bucket if it exists.
    private Pair<K, V> find(LinkedList<Pair<K, V>> item, K key) {
        if (item == null) { return null; }
        for (Pair<K, V> p : item) {
            if (p.key.equals(key)) { return p; }
        }
        return null;
    }

    @Override
    public V remove(K key) {
        if (key == null) { throw new IllegalArgumentException(); }
        if (!containsKey(key)) { throw new RuntimeException(); }
        V removeValue = get(key);
        int index = hash(key, size);
        for (Pair<K, V> p : items[index]) {
            if (p.key.equals(key)) {
                items[index].remove(p);
            }
            n--;
        }
        return removeValue;
    }

    @Override
    public V remove(K key, V value) {
        if (key == null) { throw new IllegalArgumentException(); }
        if (!containsKey(key)) { throw new RuntimeException(); }
        V removeValue = get(key);
        if (removeValue.equals(value)) {
            return remove(key);
        }
        throw new RuntimeException();
    }
}
