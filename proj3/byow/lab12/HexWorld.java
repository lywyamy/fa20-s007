package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int numHexagon = 19;
    private static final long SEED = 3722;
    private static final Random RANDOM = new Random(SEED);

    // Constructor that tessellates 19 hexagons of length s into one large hexagon
    public HexWorld(int s){
        if (s < 2) { throw new IllegalArgumentException("The length of an individual hexagon must be greater than or equal to 2"); }

        // initialize the tile rendering engine with a window of size width x height
        TERenderer ter = new TERenderer();
        int width = widthHexWorld(s);
        int height = heightHexWorld(s);
        ter.initialize(width, height);

    }

    // Creates the ith hexagon with length s.

    // Calculates the width of the HexWorld filled be hexagons of length s.
    // The width is comprised of 2 lengths and 3 longest rows (the middle) of a hexagon.
    private int widthHexWorld(int s) { return s * 2 + longestRow(s) * 3; }

    // Calculates the height of the HexWorld filled be hexagons of length s.
    // The height is comprised of 5 heights of a hexagon.
    private int heightHexWorld(int s) { return highestColumn(s) * 5; }

    // Calculate the longest (widest) row of a hexagon with length s.
    private int longestRow(int s) { return s * 3 - 2; }

    // Calculates the highest column of a hexagon with length s.
    private int highestColumn(int s) { return s * 2; }

    // Calculates the lower-left X-coordinate of the ith individual hexagon of length s.
    // The ordering is the level order, from bottom to top, left to right.
    private int ithStartX(int s, int i) {

        return -1;
    }

    // Calculates the lower-left X-coordinate of the ith individual hexagon of length s.
    private int ithStartY(int s, int i) {

        return -1;
    }

    // Selects the tile to be filled in a hexagon randomly.
    private TETile randomTile() {
        int tileNum = RANDOM.nextInt(4);
        switch (tileNum) {
            case 0: return Tileset.MOUNTAIN;
            case 1: return Tileset.TREE;
            case 2: return Tileset.SAND;
            default: return Tileset.WATER;
        }
    }

}