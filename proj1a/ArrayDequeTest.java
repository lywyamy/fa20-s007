import static org.junit.Assert.*;

import org.junit.Test;

public class ArrayDequeTest {

    @Test
    public void testArray() {
        ArrayDeque<Integer> ints = new ArrayDeque<>();
        ints.addFirst(5);
        ints.addLast(6);
        ints.addLast(7);
        ints.addLast(8);
        ints.addFirst(4);
        ints.addFirst(3);
        ints.printDeque();
        ints.printItems();
        assertEquals(6, ints.size());
        assertEquals(false, ints.isEmpty());
        int first = ints.get(0);
        int last = ints.get(5);
        int second = ints.get(1);
        int third = ints.get(2);
        assertEquals(3, first);
        assertEquals(8, last);
        assertEquals(4, second);
        assertEquals(5, third);
        int removedFirst = ints.removeFirst();
        int removedLast = ints.removeLast();
        assertEquals(3, removedFirst);
        assertEquals(8, removedLast);
        assertEquals(4, ints.size());
        ints.printDeque();
        ints.printItems();
    }

    @Test
    public void testStraightSizeUp() {
        ArrayDeque<Integer> lst= new ArrayDeque<>();
        lst.addFirst(0);
        for (int i = 1; i < 8; i++) {
            lst.addLast(i);
        }
        lst.addFirst(-1);
        lst.addLast(8);
        lst.printDeque();
        lst.printItems();
        int first = lst.get(0);
        int last = lst.get(9);
        int fifth = lst.get(4);
        assertEquals(-1, first);
        assertEquals(8, last);
        assertEquals(3, fifth);
    }

    @Test
    public void testLoopSizeUp (){
        ArrayDeque<Integer> llst= new ArrayDeque<>();
        for (int i = 0; i< 3; i++) {
            llst.addFirst(3 - i);
        }
        for (int i = 0; i < 5; i++) {
            llst.addLast(i + 4);
        }
        llst.addFirst(22);
        llst.addLast(23);
        int fifth = llst.get(4);
        llst.printDeque();
        llst.printItems();
        assertEquals(4, fifth);
    }

    @Test
    public void testStraightSizeDown() {
        ArrayDeque<Integer> lstd = new ArrayDeque<>();
        lstd.addFirst(1);
        for (int i = 0; i < 16; i ++) {
            lstd.addLast(i + 2);
        }
        lstd.printDeque();
        lstd.printItems();
        for (int i = 0; i < 10; i ++) {
            lstd.removeFirst();
        }
        lstd.addFirst(100);
        lstd.printDeque();
        lstd.printItems();
    }

    @Test
    public void testLoopSizeDown() {
        ArrayDeque<Integer> llstd = new ArrayDeque<>();
        for (int i = 0; i< 3; i++) {
            llstd.addFirst(3 - i);
        }
        for (int i = 0; i < 16; i ++) {
            llstd.addLast(i + 4);
        }
        llstd.printDeque();
        llstd.printItems();
        for (int i = 0; i < 11; i ++) {
            llstd.removeLast();
        }
        llstd.removeFirst();
        llstd.printDeque();
        llstd.printItems();
    }
}
