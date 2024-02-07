/*
 * Copyright (C) 2021 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Winter Quarter 2021 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package pathfinder;

import graph.Graph;
import graph.Graph.*;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import pathfinder.parser.CampusBuilding;
import pathfinder.parser.CampusPath;
import pathfinder.parser.CampusPathsParser;

import java.util.HashMap;
import java.util.Map;

/**
 * CampusMap represents a map of the UW campus circa 2006. Each building and intersection of paths is represented as
 * a point on the map, and every path between two points is some distance long. It has a constructor which builds the map,
 * can get the full name for a building given the abbreviation (if the abbreviation exists), and can find the shortest path
 * between two campus buildings.
 */
public class CampusMap implements ModelAPI {

    // AF(this) =
    //      each point P in the graph mapped to the paths of which P is the parent => graph
    //      mapping the abbreviations of buildings to the building the abbreviation is referring to => names
    //      the y coordinate -> y;
    //      the shortest path between two CampusBuildings => the Path<Point> returned by findShortestPath

    // Rep Invariant:
    //      graph != null
    //      foreach key k : graph.keySet(). graph.get(k) != null
    //      names != null

    /**
     * Maps a Point to the edges it is the parent of. The edges have a Double value.
     */
    private final Graph<Point, Double> graph;

    /**
     * maps abbreviations to the CampusBuilding that the abbreviation represents
     */
    private final Map<String, CampusBuilding> names;

    /**
     * Constructs a new CampusMap representing the UW campus circa 2006 and associates each abbreviation
     * with the building the abbreviation is referring to.
     */
    public CampusMap() {
        this.graph = new Graph<Point, Double>();
        this.names = new HashMap<String, CampusBuilding>();

        for (CampusBuilding building : CampusPathsParser.parseCampusBuildings("campus_buildings.tsv")) {
            // add building to the map
            Node<Point> buildingPoint = new Node<Point>(new Point(building.getX(), building.getY()));
            graph.addNode(buildingPoint);

            // associate abbreviation with building
            if (!names.containsKey(building.getShortName())) {
                names.put(building.getShortName(), building);
            }
        }


        for (CampusPath path : CampusPathsParser.parseCampusPaths("campus_paths.tsv")) {
            // add points in path to graph
            Point p1 = new Point(path.getX1(), path.getY1());
            Point p2 = new Point(path.getX2(), path.getY2());
            graph.addNode(new Node<Point>(p1));
            graph.addNode(new Node<Point>(p2));

            // add edge to graph
            Edge<Double, Point> edge = new Edge<Double, Point>(new Node<Point>(p1), new Node<Point>(p2), path.getDistance());
            graph.addEdge(edge);
        }
        // checkRep not necessary, the representation fields are final and immutable.
    }

    /**
     * @param shortName The short name of a building to query.
     * @return {@literal true} iff the short name provided exists in this campus map.
     */
    @Override
    public boolean shortNameExists(String shortName) {
        return names.containsKey(shortName);
    }

    /**
     * @param shortName The short name of a building to look up.
     * @return The long name of the building corresponding to the provided short name.
     * @throws IllegalArgumentException if the short name provided does not exist.
     */
    @Override
    public String longNameForShort(String shortName) {
        if (!shortNameExists(shortName)) {
            throw new IllegalArgumentException();
        }
        return names.get(shortName).getLongName();
    }

    /**
     * @return A mapping from all the buildings' short names to their long names in this campus map.
     */
    @Override
    public Map<String, String> buildingNames() {
        Map<String, String> buildingNames = new HashMap<String, String>();
        for (String s : names.keySet()) {
            buildingNames.put(s, names.get(s).getLongName());
        }
        return buildingNames;
    }

    /**
     * Finds the shortest path of Points, by distance, between the two provided buildings.
     *
     * @param startShortName The short name of the building at the beginning of this path.
     * @param endShortName   The short name of the building at the end of this path.
     * @return A path between {@code startBuilding} and {@code endBuilding}, or {@literal null}
     * if none exists.
     * @throws IllegalArgumentException if {@code startBuilding} or {@code endBuilding} are
     *                                  {@literal null}, or not valid short names of buildings in
     *                                  this campus map.
     */
    @Override
    public Path<Point> findShortestPath(String startShortName, String endShortName) {
        if (!shortNameExists(startShortName) || !shortNameExists(endShortName)) {
            throw new IllegalArgumentException();
        }

        CampusBuilding start = names.get(startShortName);
        CampusBuilding end = names.get(endShortName);

        Point startPoint = new Point(start.getX(), start.getY());
        Point endPoint = new Point(end.getX(), end.getY());

        Node<Point> startNode = new Node<Point>(startPoint);
        Node<Point> endNode = new Node<Point>(endPoint);

        CampusPaths<Point> path = new CampusPaths<>();

        return path.findPath(graph, startNode, endNode);
    }

}
