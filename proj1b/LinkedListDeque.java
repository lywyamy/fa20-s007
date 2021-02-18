public class LinkedListDeque<T> implements Deque<T> {

    /** The recursive structure under the hood. */
    private class TNode {
        public TNode prev;
        public T item;
        public TNode next;

        public TNode (TNode prev, T item, TNode next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    /** We use a circular sentinel for the deque.
     * The first item (if exits) is at sentinel.next.
     * The last item (if exists) is at sentinel.prev.
     */
    private TNode sentinel;
    private int size;

    /** Creates an empty deque. */
    public LinkedListDeque () {
        sentinel = new TNode(null, (T)"null", null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    public LinkedListDeque (T item) {
        sentinel = new TNode(null, (T)"null", null);
        sentinel.next = new TNode(sentinel, item, sentinel);
        sentinel.prev = sentinel.next;
        size = 1;
    }


    /** Adds an item of type T to the front of the deque. */
    @Override
    public void addFirst(T item) {
        TNode newNode = new TNode(sentinel, item, sentinel.next);
        sentinel.next = newNode;
        newNode.next.prev = newNode;
        size ++;
    }

    /** Adds an item of type T to the back of the deque. */
    @Override
    public void addLast(T item) {
        TNode newNode = new TNode(sentinel.prev, item, sentinel);
        sentinel.prev = newNode;
        newNode.prev.next = newNode;
        size ++;
    }

    /** Returns the number of items in the deque. */
    @Override
    public int size() {
        return size;
    }

    /** Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line.
     */
    @Override
    public void printDeque() {
        TNode p = sentinel;
        while (p.next != sentinel) {
            p = p.next;
            System.out.print(p.item + " ");
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     */
    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        TNode removeNode = sentinel.next;
        sentinel.next = removeNode.next;
        removeNode.next.prev = sentinel;
        size --;
        return removeNode.item;
    }

    /** Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
     */
    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        TNode removeNode = sentinel.prev;
        sentinel.prev = removeNode.prev;
        removeNode.prev.next = sentinel;
        size --;
        return removeNode.item;
    }

    /** Gets the item at the given index, where 0 is the first item.
     * If no such item exists, returns null. Must not alter the deque!
     * Must use iteration.
     */
    @Override
    public T get(int index) {
        TNode p = sentinel.next;
        while (index > 0 && p != sentinel) {
            p = p.next;
            index --;
        }
        return p.item;
    }

    /** Same as get, but uses recursion and helper method. */
    public T getRecursive(int index) {
        TNode firstNode = sentinel.next;
        return getNode(firstNode, index);
        }

    /** Helper method on the TNode class level. */
    public T getNode(TNode p, int index) {
        if (index == 0 ) {
            return p.item;
        } else if (p == sentinel) {
            return null;
        }
        return getNode(p.next,index - 1);
    }
}
