package bearmaps.proj2ab;

import java.util.ArrayList;
import java.util.TreeMap;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    public ArrayList<PriorityNode> items;
    public TreeMap<T, Integer> itemIndexMap;

    public ArrayHeapMinPQ() {
        items = new ArrayList<>();
        items.add(null);
        itemIndexMap = new TreeMap<>();
    }

    /* Leave the 0th spot empty in the underlying array.
     * The item is inserted at position size + 1.
     * The last item is at position size.
     * Parent[k] = k / 2, leftChild[k] = k * 2, rightChild[k] = k * 2 + 1
     */
    @Override
    public void add(T item, double priority) {
        if (item == null) { throw new IllegalArgumentException(); }
        if (contains(item)) { throw new IllegalArgumentException("This item is already in the heap."); }
        PriorityNode p = new PriorityNode(item, priority);
        items.add(p);
        swim(p);
        itemIndexMap.put(p.item, p.index);
    }

    private void swim(PriorityNode p) {
        while (needToSwim(p)) {
            int pIndex = p.getIndex();
            int parentIndex = p.getParent().getIndex();
            PriorityNode tempNode = p.getParent();
            items.set(pIndex, tempNode);
            items.set(parentIndex, p);
            tempNode.index = pIndex;
            p.index = parentIndex;
            itemIndexMap.replace(tempNode.item, tempNode.index);
        }
    }

    private boolean needToSwim(PriorityNode p) {
        return p.hasParent() && p.compareTo(p.getParent()) < 0;
    }

    @Override
    public boolean contains(T item) {
        return itemIndexMap.containsKey(item);
    }

    // Returns the item with smallest priority.
    @Override
    public T getSmallest() {
        return items.get(1).item;
    }

    // Removes and returns the item with smallest priority.
    @Override
    public T removeSmallest() {
        T removeItem = getSmallest();
        PriorityNode firstNode = items.get(1);
        PriorityNode lastNode = items.get(size());
        int firstIndex = firstNode.getIndex();
        items.set(1, lastNode);
        items.set(size(), firstNode);
        items.remove(size());
        lastNode.index = firstIndex;
        sink(lastNode);
        itemIndexMap.replace(lastNode.item, lastNode.index);
        itemIndexMap.remove(firstNode.item);
        return removeItem;
    }

    // Swaps the PriorityNode with its successor of smaller priority.
    // Invariant: if a PriorityNode has only one child, it must be the leftChild.
    private void sink(PriorityNode p) {
        while (needToSink(p)) {
            if (p.hasLeftChild() && p.hasRightChild()) {
                PriorityNode successor;
                if (p.getLeftChild().compareTo(p.getRightChild()) <= 0) { successor = p.getLeftChild(); }
                else { successor = p.getRightChild(); }
                int pIndex = p.getIndex();
                int successorIndex = successor.getIndex();
                PriorityNode tempNode = successor;
                items.set(pIndex, tempNode);
                items.set(successorIndex, p);
                tempNode.index = pIndex;
                p.index = successorIndex;
                itemIndexMap.replace(tempNode.item, tempNode.index);
            } else {
                int pIndex = p.getIndex();
                int leftChildIndex = p.getLeftChild().getIndex();
                PriorityNode tempNode = p.getLeftChild();
                items.set(pIndex, tempNode);
                items.set(leftChildIndex, p);
                tempNode.index = pIndex;
                p.index = leftChildIndex;
                itemIndexMap.replace(tempNode.item, tempNode.index);
            }
        }
    }

    // Returns true if p has at least one child and p is smaller than either of its children.
    private boolean needToSink(PriorityNode p) {
        boolean caseOne = (p.hasLeftChild() && p.hasRightChild()) && (p.compareTo(p.getLeftChild()) > 0 || p.compareTo(p.getRightChild()) > 0);
        boolean caseTwo = (p.hasLeftChild() && !p.hasRightChild()) && (p.compareTo(p.getLeftChild()) > 0);
        return caseOne || caseTwo;
    }

    @Override
    public int size() {
        return items.size() - 1;
    }

    // Removes the old PriorityNode and adds a new one.
    @Override
    public void changePriority(T item, double priority) {
        int index = itemIndexMap.get(item);
        PriorityNode newNode = new PriorityNode(item, priority);
        newNode.index = index;
        items.set(index, newNode);
        if (needToSwim(newNode)) { swim(newNode); }
        if (needToSink(newNode)) { sink(newNode); }
    }

    public class PriorityNode implements Comparable<PriorityNode> {
        public T item;
        public double priority;
        public int index;

        public PriorityNode(T item, double priority) {
            this.item = item;
            this.priority = priority;
            this.index = size() + 1; // The initial position is at items[size], but changes along when the PriorityNode swims or sinks.
        }

        private int getIndex() { return this.index; }

        private boolean hasParent() { return getIndex() > 1; }
        private PriorityNode getParent() { return items.get(getIndex() / 2); }

        private boolean hasLeftChild() { return getIndex() * 2 <= size(); }
        private PriorityNode getLeftChild() { return items.get(getIndex() * 2); }

        private boolean hasRightChild() { return getIndex() * 2 + 1 <= size(); }
        private PriorityNode getRightChild() { return items.get(getIndex() * 2 + 1); }

        @Override
        public int compareTo(PriorityNode other) {
            if (other == null) { return -1; }
            return Double.compare(this.priority, other.priority);
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || o.getClass() != this.getClass()) { return false; }
            else { return ((PriorityNode) o).item.equals(this.item); }
        }

        @Override
        public String toString() {
            return "[Item: " + this.item + ", Priority: " + this.priority + "]";
        }
    }
}
