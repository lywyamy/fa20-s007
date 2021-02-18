public class ArrayDeque <T> implements Deque<T> {
    private T[] items;
    private int size;
    private int nextFirstIndex;
    private int nextLastIndex;

    /** Creates an empty array of length 8.
     * nextFirst is at position x (set to 0),
     * nextLast is at position x + 1 (set to 1).
     */
    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirstIndex = 0;
        nextLastIndex = 1;
    }

    /** Checks if an ArrayDeque straight. False, if it is looped. */
    public boolean isStraightArray() {
        return !(nextFirstIndex < items.length - 1 && nextLastIndex > 0 && nextFirstIndex + 1 >= nextLastIndex);
    }

    /** Counts the number of items in the tail of the underlying array.
     * Only applies to looped ArrayDeque.
     */
    public int tailCount() {
        if (!isStraightArray()) {
            return items.length - 1 - nextFirstIndex;
        } else return 0;
    }

    /** Counts the number of items in the head of the underlying array.
     * Only applies to looped ArrayDeque.
     */
    public int headCount() {
        if (!isStraightArray()) {
            return size - (items.length - 1 - nextFirstIndex);
        } else return 0;
    }

    /** Resizes the underlying array if:
     * (1) array is full
     * (2) For arrays of length 16 or more and usage ratio is under 25%.
     *  For smaller arrays, usageRatio can be arbitrarily low.
      */
    private void sizeUp() {
        T[] resizeUp = (T[]) new Object[items.length * 2];
        if (isStraightArray()) {
            System.arraycopy(items, 0, resizeUp, 0, size);
            if (nextFirstIndex == items.length - 1) {
                nextFirstIndex = resizeUp.length - 1;
            }
            if (nextLastIndex == 0) {
                nextLastIndex = items.length;
            }
        } else {
            System.arraycopy(items, 0, resizeUp, 0, headCount());
            System.arraycopy(items, nextFirstIndex + 1, resizeUp, nextFirstIndex + 1 + items.length, tailCount());
            nextFirstIndex += items.length;
        }
        items = resizeUp;
    }

    private void sizeDown() {
        T[] resizeDown = (T[]) new Object[items.length / 2];
        if (isStraightArray()) {
            if (nextFirstIndex == items.length - 1) {
                System.arraycopy(items, 0, resizeDown, 0, size);
            } else {
                System.arraycopy(items, nextFirstIndex + 1, resizeDown, 0, size);
            }
            nextFirstIndex = items.length / 2 - 1;
            nextLastIndex = size;
        } else {
            System.arraycopy(items, 0, resizeDown, 0, headCount());
            System.arraycopy(items, nextFirstIndex + 1, resizeDown, nextFirstIndex + 1 - items.length / 2, tailCount());
            nextFirstIndex -= items.length / 2;
        }
        items = resizeDown;
    }

    /** Adds an item of type T to the front of the deque.
     * After adding an item, nextFirstIndex is x decrements by 1.
     * When array[0] is filled, nextFirstIndex is array[length - 1].
     */
    @Override
    public void addFirst(T item) {
        // If the array is already full, resizing is needed BEFORE adding another item.
        if (size == items.length) {
            sizeUp();
        }
        items[nextFirstIndex] = item;
        if (nextFirstIndex == 0) {
            nextFirstIndex = items.length  - 1;
        } else {
            nextFirstIndex --;
        }
        size ++;
    }


    /** Adds an item of type T to the back of the deque.
     * After adding an item, nextLastIndex (x + 1) increments by 1.
     * When array[length - 1] is filled, nextLastIndex is array[0].
     */
    @Override
    public void addLast(T item) {
        // If the array is already full, resizing is needed BEFORE adding another item.
        if (size == items.length) {
            sizeUp();
        }
        items[nextLastIndex] = item;
        if (nextLastIndex == items.length - 1) {
            nextLastIndex = 0;
        } else {
            nextLastIndex ++;
        }
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
        if (isStraightArray()) {
            for (int i = 0; i < size; i++) {
                System.out.print(get(i) + " ");
            }
        } else {
            for (int j = 0; j < tailCount(); j ++) {
                System.out.print(get(j) + " ");
            }
            for (int k = 0; k < headCount(); k++) {
                System.out.print(get(k + (items.length - 1 - nextFirstIndex)) + " ");
            }
        }
        System.out.println();
    }

    public void printItems (){
        for (int i = 0; i < items.length; i++) {
            System.out.print(items[i] + " ");
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     */
    @Override
    public T removeFirst() {
        // If removing an item results in usage ratio going below 25%, resizing is needed AFTER removal.
        if (isEmpty()) {
            return null;
        } else {
            T firstItem = get(0);
            if (nextFirstIndex < items.length - 1) {
                items[nextFirstIndex + 1] = null;
                nextFirstIndex ++;
            } else {
                items[0] = null;
                nextFirstIndex = 0;
            }
            size --;
            if (size < items.length * 0.25) {
                sizeDown();
            }
            return firstItem;
        }
    }

    /** Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
     */
    @Override
    public T removeLast() {
        // If removing an item results in usage ratio going below 25%, resizing is needed AFTER removal.
        if (isEmpty()) {
            return null;
        } else {
            T lastItem = get(size - 1);
            if (nextLastIndex == 0) {
                items[items.length - 1] = null;
                nextLastIndex = items.length - 1;
            } else {
                items[nextLastIndex - 1] = null;
                nextLastIndex --;
            }
            size --;
            if (size < items.length * 0.25) {
                sizeDown();
            }
            return lastItem;
        }
    }

    /** Gets the item at the given index.
     * If no such item exists, returns null.
     */
    @Override
    public T get(int index) {
        if (isEmpty()) {
            return null;
        } else if (nextFirstIndex + index + 1 <= items.length - 1) {
            return items[nextFirstIndex + index + 1];
        } else return items[nextFirstIndex + index + 1 - items.length];
    }
}
