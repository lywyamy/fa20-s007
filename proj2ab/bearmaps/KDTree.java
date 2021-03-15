package bearmaps;

import java.util.List;

public class KDTree implements PointSet {
    private static final boolean HORIZONTAL = false;
    private Node root;

    private static class Node {
        private boolean orientation;
        Point p;
        Node leftOrDown, rightOrUp;

        private Node(Point p, boolean orientation) {
            this.p = p;
            this.orientation = orientation;
        }
        private double getX() { return this.p.getX(); }
        private double getY() { return this.p.getY(); }
    }

    // Constructor
    // We can assume points has at least size 1
    public KDTree(List<Point> points) {
        for (Point p : points) {
            root = insert(p, root, HORIZONTAL);
        }
    }

    private Node insert(Point p, Node n, boolean orientation) {
        if (p == null) { throw new IllegalArgumentException(); }
        if (n == null) {
            return new Node(p, orientation);
        } else if (p.equals(n.p)) {
            return n;
        } else {
            int cmp = comparePoints(p, n.p, orientation);
            if (cmp < 0) {
                n.leftOrDown = insert(p, n.leftOrDown, !n.orientation);
            } else {
                n.rightOrUp = insert(p, n.rightOrUp, !n.orientation);
            }
        }
        return n;
    }

    private int comparePoints(Point p1, Point p2, boolean orientation) {
        if (orientation == HORIZONTAL) { return Double.compare(p1.getX(), p2.getX()); }
        else { return Double.compare(p1.getY(), p2.getY()); }
    }

    // Returns the closest point to the inputted coordinates.
    // The runtime should take Theta(logN) where N is the number of points.
    @Override
    public Point nearest(double x, double y) {
        return nearestPrune(root, new Point(x, y), root.p);
    }

    private Point nearestBasic(Node n, Point goal, Point best) {
        if (n == null) { return best; }
        if (Double.compare(Point.distance(goal, n.p), Point.distance(goal, best)) < 0) { best = n.p; }
        best = nearestBasic(n.leftOrDown, goal, best);
        best = nearestBasic(n.rightOrUp, goal, best);
        return best;
    }

    private Point nearestGoodFirst(Node n, Point goal, Point best) {
        if (n == null) { return best; }
        if (Double.compare(Point.distance(goal, n.p), Point.distance(goal, best)) < 0) { best = n.p; }
        int cmp = comparePoints(goal, n.p, n.orientation);
        Node goodSide, badSide;
        if (cmp < 0) {
            goodSide = n.leftOrDown;
            badSide = n.rightOrUp;
        } else {
            goodSide = n.rightOrUp;
            badSide = n.leftOrDown;
        }
        best = nearestBasic(goodSide, goal, best);
        best = nearestBasic(badSide, goal, best);
        return best;
    }

    private Point nearestPrune(Node n, Point goal, Point best) {
        if (n == null) { return best; }
        if (Double.compare(Point.distance(goal, n.p), Point.distance(goal, best)) < 0) { best = n.p; }

        int cmp = comparePoints(goal, n.p, n.orientation);
        Node goodSide, badSide;
        if (cmp < 0) {
            goodSide = n.leftOrDown;
            badSide = n.rightOrUp;
        } else {
            goodSide = n.rightOrUp;
            badSide = n.leftOrDown;
        }
        best = nearestPrune(goodSide, goal, best);

        if (worthExploring(n, goal, best)) {
            best = nearestPrune(badSide, goal, best);
        }
        return best;
    }


    private boolean worthExploring(Node n, Point goal, Point best) {
        double bestDistance = Point.distance(goal, best);
        double badDistance;
        if (n.orientation == HORIZONTAL) {
            badDistance = Point.distance(goal, new Point(n.getX(), goal.getY()));
        } else { badDistance = Point.distance(goal, new Point(goal.getX(), n.getY())); }
        return Double.compare(badDistance, bestDistance) < 0;
    }

    public static void main(String[] args) {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);
        KDTree kd = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7));
        System.out.println(kd.nearest(0, 7));
    }
}
