package es.datastructur.synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestArrayRingBuffer {
    @Test
    public void testARB() {
        ArrayRingBuffer<Double> arb = new ArrayRingBuffer(10);
        double r = 0.1;
        for (int i = 0; i < 5; i++) {
            arb.enqueue(i + r);
        }
        assertEquals(10, arb.capacity());
        assertEquals(5, arb.fillCount());
        assertEquals(0.1, arb.dequeue(), 0.001);
        assertFalse(arb.isFull());
        for (int i = 0; i < 4; i++) {
            arb.dequeue();
        }
        assertTrue(arb.isEmpty());
        //arb.dequeue();
    }

    @Test
    public void testIterator() {
        ArrayRingBuffer<Double> arb = new ArrayRingBuffer(10);
        double r = 0.1;
        for (int i = 0; i < 10; i++) {
            arb.enqueue(i + r);
        }

        for (double item : arb) {
            System.out.println(item);
        }
    }

    @Test
    public void testEqualInteger() {
        ArrayRingBuffer<Integer> arbOne = new ArrayRingBuffer(10);
        ArrayRingBuffer<Integer> arbTwo = new ArrayRingBuffer(10);
        for (int i = 0; i < 10; i++) {
            arbOne.enqueue(i);
            arbTwo.enqueue(i);
        }
        assertTrue(arbOne.equals(arbTwo));
        arbTwo.dequeue();
        arbTwo.enqueue(50);
        assertFalse(arbOne.equals(arbTwo));
    }

    @Test
    public void testEqualString() {
        ArrayRingBuffer<String> one = new ArrayRingBuffer<>(3);
        ArrayRingBuffer<String> two = new ArrayRingBuffer<>(3);
        ArrayRingBuffer<String> three = new ArrayRingBuffer<>(3);
        one.enqueue("lywy");
        one.enqueue("tongtong");
        one.enqueue("zy");
        two.enqueue("lywy");
        two.enqueue("tongtong");
        two.enqueue("zy");
        three.enqueue("lywy");
        three.enqueue("tongtong");
        three.enqueue("ZY");
        assertTrue(one.equals(two));
        assertFalse(one.equals(three));
    }

}
