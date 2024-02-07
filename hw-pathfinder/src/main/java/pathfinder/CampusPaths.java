package pathfinder;

import graph.Graph;
import graph.Graph.*;
import pathfinder.datastructures.Path;

import java.util.*;

/**
 * CampusPaths finds the shortest paths between two Objects of type T on a graph. The value of the edge between the two Objects
 * must at least be of type Double.
 */
public class CampusPaths<T> {

    // This class does not represent an ADT.

    /**
     * Finds a path between two objects of the same type on the map. If there is no path, a null path
     * is returned. If the start and destination object are the same object, null is returned.
     *
     * @param start the object the route is starting at
     * @param destination the object the route is ending at
     * @param graph the graph that is being referenced when finding a path
     * @throws IllegalArgumentException if one or both of the given Nodes are not in the given Graph.
     * @return A path between the first object and second object, or null if none exists.
     */
    public Path<T> findPath(Graph<T, Double> graph, Node<T> start, Node<T> destination) {
        boolean startFound = graph.getNodes().contains(start.getValue());
        boolean destinationFound = graph.getNodes().contains(destination.getValue());

        if((start.equals(destination) && startFound)){
            return null;
        }

        if (!startFound && !destinationFound) {
            throw new IllegalArgumentException("unknown node " + start.getValue() + "\n" + "unknown node " + destination.getValue());
        } else if (!startFound) {
            throw new IllegalArgumentException("unknown node " + start.getValue());
        } else if (!destinationFound) {
            throw new IllegalArgumentException("unknown node " + destination.getValue());
        }

        PriorityQueue<Path<T>> active = new PriorityQueue<Path<T>>(new PathComparator());
        Set<T> finished = new HashSet<T>();

        Path<T> self = new Path<T>(start.getValue());
        active.add(self);

        while (!active.isEmpty()) {
            Path<T> minPath = active.remove();
            T minDest = minPath.getEnd();

            if (minDest.equals(destination.getValue())) {
                return minPath;
            }

            if (!finished.contains(minDest)) {
                List<Graph.Edge<Double, T>> parentPaths = graph.getEdges(new Node<T>(minDest));
                for (Edge<Double, T> edge : parentPaths) {
                    if (!finished.contains(edge.getChild())) {
                        Path<T> newPath = minPath.extend(edge.getChild().getValue(), edge.getLabel());
                        active.add(newPath);
                    }
                }
            }
            finished.add(minDest);
        }

        return null;
    }

    /**
     * A Comparator between two Paths.
     */
    public class PathComparator implements Comparator<Path<T>> {
        /**
         * Compares two Paths of type CampusBuilding.
         *
         * @param p1 the first CampusBuilding to be compared
         * @param p2 the second CampusBuilding to be compared
         * @return a negative integer, zero, or a positive integer as the first argument is
         * less than, equal to, or greater than the second.
         */
        @Override
        public int compare(Path<T> p1, Path<T> p2) {
            return Double.compare(p1.getCost(), p2.getCost());
        }
    }
}
