package bearmaps.proj2c;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import bearmaps.proj2ab.DoubleMapPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.*;
import java.util.Collections;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome;
    private List<Vertex> solution;
    private double explorationTime;
    private HashMap<Vertex, Double> bestKnownDistance;
    private HashMap<Vertex, Vertex> bestKnownRoute;
    private HashSet<Vertex> visitedVertices;
    private AStarGraph input;
    private Vertex start, end;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        this.input = input;
        this.start = start;
        this.end = end;
        Stopwatch sw = new Stopwatch();

        /* Keeps track of vertices that have been popped from the fringe PQ.*/
        visitedVertices = new HashSet<>();

        /* Keeps track of the best known distance (from the source) to every vertex that we have seen. */
       bestKnownDistance = new HashMap<>();

       /* Keeps track of the best known solution. */
        bestKnownRoute = new HashMap<>();

        /* Adds start vertex to the fringe PQ. */
        ArrayHeapMinPQ<Vertex> fringe = new ArrayHeapMinPQ<>();
        //DoubleMapPQ<Vertex> fringe = new DoubleMapPQ<>();
        fringe.add(start, heuristicDist(start));
        bestKnownDistance.put(start, 0.0);
        bestKnownRoute.put(start, null);

        /*  Repeatedly removes the best vertex from the fringe PQ
         *  and relaxes all edges pointing out from that vertex
         *  until we've hit the end.
         */
        while (fringe.size() != 0) {
            Vertex poppedVertex = fringe.removeSmallest();
            visitedVertices.add(poppedVertex);
            this.explorationTime = sw.elapsedTime();

            /* Terminates execution when the end vertex has been popped from the fringe. */
            if (poppedVertex.equals(end)) {
                this.outcome = SolverOutcome.SOLVED;
                solution = new ArrayList<>();
                for(Vertex v = end; edgeTo(v) != null; v = edgeTo(v)) { solution.add(v); }
                solution.add(start);
                Collections.reverse(solution);
                break;
            }

            /* Terminates execution when the runtime exceeds the given time. */
            if (explorationTime > timeout) {
                this.outcome = SolverOutcome.TIMEOUT;
                break;
            }
            /* Provides a list of all edges that go out from the current vertex to its neighbors. */
            List<WeightedEdge<Vertex>> neighboringEdges = input.neighbors(poppedVertex);

            /* Relaxes all edges pointing out from the current vertex.
               Updates/adds the outgoing vertices to the bestKnownDistance.
               Updates/adds the outgoing vertices to the bestKnownSolution.
               Updates/adds the outgoing vertices to the fringe PQ.
             */
            for(WeightedEdge<Vertex> edge : neighboringEdges) {
                Vertex p = edge.from();
                Vertex q = edge.to();
                double weight = edge.weight();

                // Checks the bestKnownDistance.
                if (!bestKnownDistance.containsKey(q)) { bestKnownDistance.put(q, Double.POSITIVE_INFINITY); }
                if (distTo(p) + weight < distTo(q)) {
                    bestKnownDistance.replace(q, distTo(p) + weight);

                    // Checks the fringe PQ.
                    if (!fringe.contains(q)) {
                        fringe.add(q, distTo(q) + heuristicDist(q));
                        bestKnownRoute.put(q, p);
                    }
                    else {
                        fringe.changePriority(q, bestKnownDistance.get(q) + heuristicDist(q));
                        bestKnownRoute.replace(q, p);
                    }
                }
            }
        }

        if (this.outcome != SolverOutcome.SOLVED && fringe.size() == 0) { this.outcome = SolverOutcome.UNSOLVABLE; }
    }

    /* Returns the best known distance for a given vertex from the start. */
    private double distTo(Vertex v) { return this.bestKnownDistance.get(v); }

    /* Returns the heuristic distance for a given vertex to the end. */
    private double heuristicDist(Vertex v) { return this.input.estimatedDistanceToGoal(v, end); }

    /* Returns the vertex from which the current vertex is from. */
    private Vertex edgeTo(Vertex v) { return this.bestKnownRoute.get(v); }

    @Override
    public SolverOutcome outcome() { return this.outcome; }

    @Override
    public List<Vertex> solution() {
        if (outcome().equals(SolverOutcome.SOLVED)) { return this.solution; }
        else { return new ArrayList<>(); }
    }

    @Override
    public double solutionWeight() {
        if (outcome().equals(SolverOutcome.SOLVED)) { return distTo(end); }
        else { return 0; }
    }

    @Override
    public int numStatesExplored() { return this.visitedVertices.size(); }

    @Override
    public double explorationTime() {
        return this.explorationTime;
    }
}