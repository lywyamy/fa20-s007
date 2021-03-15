package bearmaps;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class KDTreeTest {
    private static final Random r = new Random(500);

    private Point randomPoint() {
        double x = r.nextDouble();
        double y = r.nextDouble();
        return new Point(x, y);
    }

    private List<Point> listOfNPoints(int n) {
        List<Point> points = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            points.add(randomPoint());
        }
        return points;
    }

    private void testWithNPointsAndQQueries(int pointCount, int queryCount) {
        List<Point> points = listOfNPoints(pointCount);
        NaivePointSet nps = new NaivePointSet(points);
        KDTree kd = new KDTree(points);

        List<Point> queries = listOfNPoints(queryCount);
        for (Point p : queries) {
            Point expected = nps.nearest(p.getX(), p.getY());
            Point actual = kd.nearest(p.getX(), p.getY());
            assertEquals(expected, actual);
        }
    }

    @Test
    public void smallTest() {
        testWithNPointsAndQQueries(1000, 200);
    }

    @Test
    public void bigTest() {
        testWithNPointsAndQQueries(10000, 2000);
    }

    @Test
    public void timeTest() {
        List<Point> randomPoints = listOfNPoints(100000);
        KDTree kd = new KDTree(randomPoints);
        NaivePointSet nps = new NaivePointSet(randomPoints);
        List<Point> queryPoints = listOfNPoints(10000);

        Stopwatch sw = new Stopwatch();
        for (Point p : queryPoints) {
            nps.nearest(p.getX(), p.getY());
        }
        double time = sw.elapsedTime();
        System.out.println("Naive 10000 queries on 100000 points: " + time);

        sw = new Stopwatch();
        for (Point p : queryPoints) {
            kd.nearest(p.getX(), p.getY());
        }
        time = sw.elapsedTime();
        System.out.println("KDTree 10000 queries on 100000 points: " + time);}

}
