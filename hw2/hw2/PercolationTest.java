package hw2;

import org.junit.Test;
import static org.junit.Assert.*;

public class PercolationTest {
    @Test
    public void testOpenAndFull() {
        Percolation p = new Percolation(4);
        p.open(0, 0);
        assertTrue(p.isOpen(0, 0));
        assertTrue(p.isFull(0, 0));
        p.open(1, 0);
        assertTrue(p.isOpen(1, 0));
        assertTrue(p.isFull(1, 0));
        p.open(2, 2);
        assertFalse(p.isFull(2, 2));
        p.open(1, 1);
        p.open(1, 2);
        assertTrue(p.isFull(2, 2));
        assertEquals(5, p.numberOfOpenSites());
    }

    @Test
    public void testPercolates() {
        Percolation p = new Percolation(4);
        p.open(0, 0);
        assertTrue(p.isOpen(0, 0));
        assertTrue(p.isFull(0, 0));
        p.open(1, 0);
        assertTrue(p.isOpen(1, 0));
        assertTrue(p.isFull(1, 0));
        p.open(2, 2);
        assertFalse(p.isFull(2, 2));
        p.open(1, 1);
        p.open(1, 2);
        assertTrue(p.isFull(2, 2));
        assertFalse(p.percolates());
        p.open(3, 2);
        assertTrue(p.percolates());
    }
}
