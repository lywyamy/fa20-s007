import org.junit.Test;
import static org.junit.Assert.*;

public class TestUnionFind {

    @Test
    public void testUF() {
        UnionFind uf = new UnionFind(9);
        uf.connect(2, 3);
        uf.connect(1, 2);
        uf.connect(5, 7);
        uf.connect(8, 4);
        uf.connect(7, 2);
        assertEquals(3, uf.find(3));
        assertEquals(5, uf.sizeOf(7));
        assertTrue(uf.isConnected(2, 5));
        assertFalse(uf.isConnected(8, 1));
        uf.connect(0, 6);
        uf.connect(6, 4);
        uf.connect(6, 3);
        assertEquals(3, uf.find(8));
        assertEquals(9, uf.sizeOf(0));
        assertEquals(6, uf.parent(0));
        assertEquals(3, uf.parent(7));
        assertEquals(-9, uf.parent(3));
    }

    @Test
    public void testPC() {
        UnionFind uf = new UnionFind(9);
        uf.connect(2, 3);
        uf.connect(1, 2);
        uf.connect(5, 7);
        uf.connect(8, 4);
        uf.connect(7, 2);
        uf.connect(0, 6);
        uf.connect(6, 4);
        uf.connect(6, 3);
        assertEquals(6, uf.parent(0));
        assertEquals(3, uf.findPC(0));
        assertEquals(3, uf.parent(0));
        assertEquals(3, uf.parent(6));
        //uf.find(11);
    }

}
