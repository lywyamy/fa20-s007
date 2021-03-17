package bearmaps;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class ArrayHeapMinPQTest {
    ArrayHeapMinPQ<String> h = new ArrayHeapMinPQ<>();

    @Test
    public void testEmptyArrayHeap() {
        int size = h.size();
        assertEquals(0, size);
    }

    @Test
    public void testGetSmallest() {
        h.add("Tongtong", 5);
        System.out.println(h.itemIndexMap.entrySet());
        h.add("lywy", 0.9);
        System.out.println(h.itemIndexMap.entrySet());
        h.add("ZY", 0.1);
        System.out.println(h.itemIndexMap.entrySet());
        assertEquals(3, h.size());
        assertEquals("ZY", h.getSmallest());
        h.changePriority("ZY", 1.2);
        assertEquals("lywy", h.getSmallest());
        for(ArrayHeapMinPQ.PriorityNode p : h.items) {
            System.out.println(p);
        }
    }

    @Test
    public void testRemoveSmallest() {
        ArrayHeapMinPQ<String> h = new ArrayHeapMinPQ<>();
        h.add("Tongtong", 5);
        h.add("lywy", 0.9);
        h.add("ZY", 0.1);
        String smallest = h.removeSmallest();
        System.out.println(h.itemIndexMap.entrySet());
        assertEquals(2, h.size());
        assertEquals("ZY", smallest);
        assertEquals("lywy", h.getSmallest());
    }

    private static final Random r = new Random(500);

    ArrayHeapMinPQ<Integer> pq = new ArrayHeapMinPQ<>();
    NaiveMinPQ<Integer> npq = new NaiveMinPQ<>();
    int N = 100000;

    @Test
    public void timeTest() {
        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < N; i++) {
            pq.add(r.nextInt(), r.nextDouble());
        }
        double time = sw.elapsedTime();
        System.out.println("Adding 10000 entries to ArrayHeapMinPQ: " + time);

        sw = new Stopwatch();
        for (int i = 0; i < N; i++) {
            npq.add(r.nextInt(), r.nextDouble());
        }
        time = sw.elapsedTime();
        System.out.println("Adding 10000 entries to NaiveHeapMinPQ: " + time);

        sw = new Stopwatch();
        pq.getSmallest();
        time = sw.elapsedTime();
        System.out.println("Getting the smallest item from ArrayHeapMinPQ: " + time);

        sw = new Stopwatch();
        npq.getSmallest();
        time = sw.elapsedTime();
        System.out.println("Getting the smallest item from NaiveHeapMinPQ: " + time);
    }
}
