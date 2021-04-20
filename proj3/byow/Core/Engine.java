package byow.Core;


import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.*;

public class Engine {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 30;
    private static final double terminationRatio = 0.5;
    private static final int roomWidthMin = 4;
    private static final int roomWidthMax = 10;
    private static final int roomHeightMin = 4;
    private static final int roomHeightMax = 8;
    private static final int hallwayWidth = 3;
    private static final int hallwayHeightMin = 3;
    private static final int hallwayHeightMax = 12;

    TERenderer ter = new TERenderer();
    private TETile[][] myWorld = new TETile[WIDTH][HEIGHT];
    private int filledArea = 0;
    private double filledRatio = (double) filledArea / WIDTH * HEIGHT;
    private Random rd;
    private Map<Edge, Boolean> edgesToConnectablity = new HashMap<>();
    private List<Edge> connectableEdges = new ArrayList<>();


    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        initialize();
        int seed = getSeed(input);
        this.rd = new Random(seed);
        addFirstRoom();
        /*while (this.filledRatio < terminationRatio) {
            addSpace();
        }*/
        return myWorld;
    }

    /* Steps to add a random space to myWorld:
     * 1. get a connectable edge from the list
     * 2. get the direction of the edge and randomly get a wall to be the connecting door
     *  (the connecting door cannot be the four corners.)
     * 3. explore its outer space and return int[]
     *  (if unable to add any space onto this edge, start over from step 1.)
     * 4. add a space
     * 5. connect two spaces by marking their doors Tileset.FLOOR
     * 6. add the three non-connecting edges of the new space to map
     * 7. mark the two connecting edges false and update the edge list
     */
    private void addSpace() {
        Edge edge = getEdge();
        int doorX = getDoorX(edge);
        int doorY = getDoorY(edge);
        Point door = new Point(doorX, doorY);

        int[] feasibleRegion = exploreOuterSpace(edge, door);
    }

    /**
     * @param edge a connectable edge randomly picked from connectableEdges
     * @param point the door on the edge
     * @return a list of four integers that define the possible outer space off the door
     *         (lowerLeftX, lowerLeftY, upperRightX, upperRightY)
     */
    private int[] exploreOuterSpace(Edge edge, Point point) {
        int[] outerSpace = new int[4];
        String direction = edge.getDirection();

        if (direction.equals("north")) {


        } else if (direction.equals("south")) {

        } else if (direction.equals("west")) {

        } else {

        }

        return null; // dummy value
    }


    private int getDoorX(Edge edge) {
        String direction = edge.getDirection();
        int minX = edge.getStartX();
        int maxX = edge.getEndX();
        if (direction.equals("north") || direction.equals("south")) { return randomInt(minX, maxX); }
        else { return minX; }
    }

    private int getDoorY(Edge edge) {
        String direction = edge.getDirection();
        int minY = edge.getStartY();
        int maxY = edge.getEndY();
        if (direction.equals("west") || direction.equals("east")) { return randomInt(minY, maxY); }
        else { return minY; }
    }

    private Edge getEdge() {
        int size = connectableEdges.size();
        int index = rd.nextInt(size);
        return connectableEdges.get(index);
    }

    /* Adds the first room,
     * Stores its four edge to the Edges HashMap.
     */
    private void addFirstRoom() {
        int startX = randomInt(10, 70);
        int startY = randomInt(10, 20);
        int width = randomInt(roomWidthMin, roomWidthMax);
        int height = randomInt(roomHeightMin, roomHeightMax);
        int endX = startX + width - 1;
        int endY = startY + height - 1;

        fillSpace(startX, startY, endX, endY);

        // Updates the filledArea
        this.filledArea += width * height;

        // Creates the four edges and add them to the edges map.
        Edge north = new Edge(startX - 1, endY, endX - 1, endY, "north");
        Edge south = new Edge(startX - 1, startY, endX - 1, startY, "south");
        Edge west = new Edge(startX, startY - 1, startX, endY - 1, "west");
        Edge east = new Edge(endX, startY - 1, endX, endY -1, "east");
        edgesToConnectablity.put(north, true);
        edgesToConnectablity.put(south, true);
        edgesToConnectablity.put(west, true);
        edgesToConnectablity.put(east, true);

        // Updates the list of connectable edges.
        updateEdgeList();

    }

    /* Fills the walls and floor of a space. */
    private void fillSpace(int startX, int startY, int endX, int endY) {
        // Fills the North and South walls
        for (int x = startX; x <= endX; x++) {
            myWorld[x][startY] = Tileset.WALL;
            myWorld[x][endY] = Tileset.WALL;
        }

        // Fills the West and East walls
        for (int y = startY; y <= endY; y++) {
            myWorld[startX][y] = Tileset.WALL;
            myWorld[endX][y] = Tileset.WALL;
        }

        // Fills the floors
        for (int x = startX + 1; x <= endX - 1; x++) {
            for (int y = startY + 1; y <= endY - 1; y++) {
                myWorld[x][y] = Tileset.FLOOR;
            }
        }
    }

    private void updateEdgeList() {
        for (Edge edge: edgesToConnectablity.keySet()) {
            if(edgesToConnectablity.get(edge)) {
                connectableEdges.add(edge);
            }
        }
    }

    private class Point {
        int coordinateX, coordinateY;
        TETile tile;

        public Point(int coordinateX, int coordinateY) {
            if (coordinateX < 0 || coordinateX >= WIDTH) { throw new IllegalArgumentException("The x coordinate must be 0 - 79 inclusively."); }
            if (coordinateY < 0 || coordinateY >= HEIGHT) { throw new IllegalArgumentException("The y coordinate must be 0 - 29 inclusively."); }
            this.coordinateX = coordinateX;
            this.coordinateY = coordinateY;
            this.tile = myWorld[coordinateX][coordinateY];
        }

        public int getCoordinateX() { return this.coordinateX; }
        public int getCoordinateY() { return this.coordinateY; }
        public TETile getTile() { return this.tile; }

        public Point getNeighbor(String direction) {
            int x = getCoordinateX();
            int y = getCoordinateY();
            if (direction.equals("north")) {
                if (y == HEIGHT - 1) { return null; }
                return new Point(x, y + 1);
            } else if (direction.equals("south")) {
                if (y == 0) { return null; }
                return new Point(x, y - 1);
            } else if (direction.equals("west")) {
                if (x == 0) { return null; }
                return new Point(x - 1, y);
            } else if (direction.equals("east")) {
                if (x == WIDTH - 1) { return null; }
                return new Point(x + 1, y);
            } else {
                throw new IllegalArgumentException("The input direction is invalid.");
            }
        }
    }

    private class Edge {
        int startX, startY, endX, endY;
        String direction;

        public Edge(int startX, int startY, int endX, int endY, String direction) {
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
            this.direction = direction;
        }

        public int getStartX() { return this.startX; }
        public int getStartY() { return this.startY; }
        public int getEndX() { return this.endX; }
        public int getEndY() { return this.endY; }
        public String getDirection() { return this.direction; }
    }

    /* Creates a random integer generator that will be used for the entire process. */
    private int randomInt(int min, int max) { return min + rd.nextInt(max - min + 1); }

    /* Initializes myWorld with default NOTHING tiles. */
    private void initialize() {
        for (int i = 0; i < myWorld.length; i++) {
            for (int j = 0; j < myWorld[0].length; j++) {
                myWorld[i][j] = Tileset.NOTHING;
            }
        }
    }

    /**
     * Validates user input
     * @param input is in the format "N#######S" where each # is a digit and there can be an arbitrary number of #s.
     * @return the integer digits from the input as the random seed.
     */
    private int getSeed(String input) {
        char firstChar = input.charAt(0);
        char lastChar = input.charAt(input.length() - 1);
        if (Character.toLowerCase(firstChar) != 'n') { throw new IllegalArgumentException("The input must start with 'n', case insensitive."); }
        if (Character.toLowerCase(lastChar) != 's') { throw new IllegalArgumentException("The input must end with 's', case insensitive."); }

        StringBuilder seedString = new StringBuilder();
        for(int i = 1; i < input.length() - 1; i++) {
            if (!Character.isDigit(input.charAt(i))) { throw new IllegalArgumentException("The input must contain only digits between 'n' and 's'."); }
            seedString.append(input.charAt(i));
        }
        return Integer.parseInt(seedString.toString());
    }

    public static void main(String[] args) {
        Engine engine = new Engine();
        engine.ter.initialize(WIDTH, HEIGHT);
        engine.ter.renderFrame(engine.interactWithInputString("n3722s"));
    }
}
