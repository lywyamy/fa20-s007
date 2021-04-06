package bearmaps.proj2d;

import bearmaps.proj2ab.Point;
import bearmaps.proj2ab.PointSet;
import bearmaps.proj2ab.WeirdPointSet;
import bearmaps.proj2c.streetmap.StreetMapGraph;
import bearmaps.proj2c.streetmap.Node;
import edu.princeton.cs.algs4.TrieSET;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {

    private WeirdPointSet wps;
    private HashMap<Point, Long> pointToID;
    private TrieSET trieSet;
    private HashMap<String, HashSet<String>> cleanToFull;
    private HashMap<String, HashSet<Node>> cleanLocations;

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        List<Node> nodes = this.getNodes();
        List<Point> reachablePoints = new ArrayList<>();
        pointToID = new HashMap<>();
        trieSet = new TrieSET();
        cleanToFull = new HashMap<>();
        cleanLocations = new HashMap<>();

        for (Node n : nodes) {
            Point p = new Point(n.lon(), n.lat());
            pointToID.put(p, n.id());
            if (neighbors(n.id()).size() > 0) { reachablePoints.add(p); }

            String fullName = n.name();
            if (fullName != null) {
                String cleanedName = cleanString(fullName);
                trieSet.add(cleanedName);

                if (!cleanToFull.containsKey(cleanedName)) {
                    HashSet<String> fullNames = new HashSet<>();
                    fullNames.add(fullName);
                    cleanToFull.put(cleanedName, fullNames);
                } else {
                    HashSet<String> fullNames = cleanToFull.get(cleanedName);
                    fullNames.add(fullName);
                }

                if (!cleanLocations.containsKey(cleanedName)) {
                    HashSet<Node> locations = new HashSet<>();
                    locations.add(n);
                    cleanLocations.put(cleanedName, locations);
                } else {
                    HashSet<Node> locations = cleanLocations.get(cleanedName);
                    locations.add(n);
                }
            }
        }
        wps = new WeirdPointSet(reachablePoints);
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        Point p = wps.nearest(lon, lat);
        return this.pointToID.get(p);
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        String cleanQuery = cleanString(prefix);
        List<String> suggestedResult = new ArrayList<>();
        for(String s : trieSet.keysWithPrefix(cleanQuery)) {
            suggestedResult.addAll(cleanToFull.get(s));
        }
        return suggestedResult;
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        String cleanQuery = cleanString(locationName);
        if (!cleanLocations.containsKey(cleanQuery)) { throw new IllegalArgumentException("There is no such a location."); }
        HashSet<Node> nodes = cleanLocations.get(cleanQuery);
        List<Map<String, Object>> searchResult = new ArrayList<>();
        for (Node n : nodes) {
            Map<String, Object> location = new HashMap<>();
            location.put("lat", n.lat());
            location.put("lon", n.lon());
            location.put("name", n.name());
            location.put("id", n.id());
            searchResult.add(location);
        }
        return searchResult;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
