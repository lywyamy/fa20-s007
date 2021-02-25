import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by hug.
 */
public class TestRedBlackFloorSet {
    @Test
    public void randomizedTest() {
       AListFloorSet aSet = new AListFloorSet();
       RedBlackFloorSet rbSet = new RedBlackFloorSet();

       for (int i= 0; i < 1000000; i++) {
           double randomDouble = StdRandom.uniform(-5000.0, 5000.0);
           aSet.add(randomDouble);
           rbSet.add(randomDouble);
        }

       for (int i = 0; i < 100000; i++) {
           double randomDouble = StdRandom.uniform(-5000.0, 5000.0);
           assertEquals(aSet.floor(randomDouble), rbSet.floor(randomDouble), 0.000001);
       }
    }
}
