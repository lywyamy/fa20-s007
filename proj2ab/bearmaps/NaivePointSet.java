package bearmaps;

import java.util.LinkedList;
import java.util.List;

public class NaivePointSet implements PointSet {
    private final List<Point> lst;

    // Constructor
    // We can assume points has at least size 1
    public NaivePointSet(List<Point> points) {
        lst = new LinkedList<>();
        lst.addAll(points);

    }

    // Returns the closest point to the inputted coordinates.
    // The runtime should take Theta(N) where N is the number of points.
    @Override
    public Point nearest(double x, double y) {
        Point goal = new Point(x, y);
        Point best = lst.get(0);
        double bestDistance = Point.distance(goal, best);

        for (Point p : lst) {
            double currDistance = Point.distance(goal, p);
            if (Double.compare(currDistance, bestDistance) < 0) {
                best = p;
                bestDistance = currDistance;
            }
        }
        return best;
    }

    public static void main(String[] args) {
        Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);

        NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3));
        Point ret = nn.nearest(3.0, 4.0); // returns p2
        System.out.println(ret.getX()); // evaluates to 3.3
        System.out.println(ret.getY()); // evaluates to 4.4
    }
}
