import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private class Node {
        private K key;
        private V value;
        private Node left, right;
        private int size;

        // Constructor of the recursive node
        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.size = 1;
        }
    }

    private Node root; // Represents the node at the root of the tree

    public BSTMap() {

    }

    @Override
    public void clear() {
        this.root = null;
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) { throw new IllegalArgumentException(); }
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        return get(key, root);
    }

    private V get(K key, Node n) {
        if (key == null) { throw new IllegalArgumentException(); }
        if (n == null) { return null; }
        int cmp = key.compareTo(n.key);
        if (cmp < 0) { return get(key,n.left); }
        else if (cmp > 0) { return get(key, n.right);}
        else { return n.value; }
    }

    @Override
    public int size() {
        return size(root);
    }

    private int size(Node n) {
        if (n == null) { return 0; }
        else return n.size;
    }

    @Override
    public void put(K key, V value) {
        if (key == null) { throw new IllegalArgumentException(); }
        root = put(key, value, root);
    }

    private Node put(K key, V value, Node n) {
        if (key == null) { throw new IllegalArgumentException(); }
        if (n == null) { n = new Node(key, value); }
        int cmp = key.compareTo(n.key);
        if (cmp < 0) { n.left = put(key, value, n.left); }
        else if (cmp > 0){ n.right = put(key, value, n.right); }
        else { n.value = value; }
        n.size = size(n.left) + size(n.right) + 1;
        return n;
    }

    /* Prints out key-value pairs in order of increasing Key. */
    public void printInOrder() {
        printInOrder(root);
    }

    private void printInOrder(Node n) {
        if (n == null) { System.out.println(); }
        if (n.left == null && n.right == null) {
            System.out.println(n.key.toString() + n.value.toString());
        } else if (n.left != null && n.right == null) {
            printInOrder(n.left);
            System.out.println(n.key.toString() + n.value.toString());
        } else if (n.left == null && n.right != null) {
            System.out.println(n.key.toString() + n.value.toString());
            printInOrder(n.right);
        } else {
            printInOrder(n.left);
            System.out.println(n.key.toString() + n.value.toString());
            printInOrder(n.right);
        }
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        V removeValue = get(key, root);
        root = remove(key, root);
        return removeValue;
    }

    private Node remove(K key, Node n) {
        if (key == null) { throw new IllegalArgumentException(); }
        if (n == null) { return null; }

        int cmp = key.compareTo(n.key);
        if (cmp < 0) { n.left = remove(key, n.left); }
        else if (cmp > 0) {n.right = remove(key, n.right); }
        else {
            if (n.left == null) { return n.right; }
            if (n.right == null) { return n.left; }
            Node temp = n;
            n = min(temp.right);
            n.right = deleteMin(temp.right);
            n.left = temp.left;

        }
        n.size = size(n.left) + size(n.right) + 1;
        return n;
    }

    // Removes the minimum key with its value from the BST
    public void deleteMin() {
        root = deleteMin(root);
    }

    private Node deleteMin(Node n) {
        if (n == null) { return null; }
        if (n.left == null) { return n.right; }
        else { n.left = deleteMin(n.left); }
        return n;
    }

    // Returns the minimum key in the BST
    public K min() {
        return min(root).key;
    }

    private Node min(Node n) {
        if (n == null) { return null; }
        if (n.left == null) { return n;}
        else { return min(n.left); }
    }

    // Returns the max key in the BST
    public K max() {
        return max(root).key;
    }

    private Node max(Node n) {
        if (n == null) { return null; }
        if (n.right == null) { return n; }
        else { return max(n.right); }
    }

    @Override
    public V remove(K key, V value) {
        V removeValue = get(key);
        if (value == removeValue) {
            remove(key);
            return removeValue;
        }
        throw new RuntimeException("The key cannot be removed because the given value doesn't match.");
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

}
