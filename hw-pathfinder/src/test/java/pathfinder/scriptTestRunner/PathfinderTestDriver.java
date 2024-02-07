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

package pathfinder.scriptTestRunner;

import graph.Graph;
import graph.Graph.*;
import pathfinder.CampusPaths;
import pathfinder.datastructures.Path;

import java.io.*;
import java.util.*;

/**
 * This class implements a test driver that uses a script file format
 * to test an implementation of Dijkstra's algorithm on a graph.
 */
public class PathfinderTestDriver {

    private final Map<String, Graph<String, Double>> graphs = new HashMap<String, Graph<String, Double>>();
    private final PrintWriter output;
    private final BufferedReader input;

    public static void main(String[] args) {
        // You only need a main() method if you choose to implement
        // the 'interactive' test driver, as seen with GraphTestDriver's sample
        // code. You may also delete this method entirely and just
    }

    // Leave this constructor public
    public PathfinderTestDriver(Reader r, Writer w) {
        input = new BufferedReader(r);
        output = new PrintWriter(w);
    }

    // Leave this method public
    public void runTests() throws IOException {
        String inputLine;
        while ((inputLine = input.readLine()) != null) {
            if ((inputLine.trim().length() == 0) ||
                    (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            } else {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if (st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<String>();
                    while (st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    }

    private void executeCommand(String command, List<String> arguments) {
        try {
            switch (command) {
                case "CreateGraph":
                    createGraph(arguments);
                    break;
                case "AddNode":
                    addNode(arguments);
                    break;
                case "AddEdge":
                    addEdge(arguments);
                    break;
                case "ListNodes":
                    listNodes(arguments);
                    break;
                case "ListChildren":
                    listChildren(arguments);
                    break;
                case "FindPath":
                    findPath(arguments);
                    break;
                default:
                    output.println("Unrecognized command: " + command);
                    break;
            }
        } catch (Exception e) {
            output.println("Exception: " + e.toString());
        }
    }

    private void createGraph(List<String> arguments) {
        if (arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        createGraph(graphName);
    }

    private void createGraph(String graphName) {
        graphs.put(graphName, new Graph<String, Double>());
        output.println("created graph " + graphName);
    }

    private void addNode(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to AddNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {
        Graph<String, Double> g = graphs.get(graphName);
        boolean containsInsert = false;
        List<String> nodes = g.getNodes();
        Collections.sort(nodes);
        for (String s : nodes) {
            if (nodeName.equals(s)) {
                containsInsert = true;
            }
        }
        if (!containsInsert) {
            g.addNode(new Node<String>(nodeName));
            output.println("added node " + nodeName + " to " + graphName);
        }
    }

    private void addEdge(List<String> arguments) {
        if (arguments.size() != 4) {
            throw new CommandException("Bad arguments to AddEdge: " + arguments);
        }
        try {
            Double.parseDouble(arguments.get(3));
        } catch (NumberFormatException e) {
            throw new CommandException("Edge label cannot be parsed as a number: " + arguments.get(3));
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        Double edgeLabel = Double.parseDouble(arguments.get(3));

        addEdge(graphName, parentName, childName, edgeLabel);
    }

    private void addEdge(String graphName, String parentName, String childName,
                         Double edgeLabel) {
        Graph<String, Double> g = graphs.get(graphName);
        Edge<Double, String> insert = new Edge<Double, String>(new Graph.Node<String>(parentName), new Graph.Node<String>(childName), edgeLabel);
        boolean containsInsert = false;
        if (parentName != null) {
            List<Edge<Double, String>> edges = g.getEdges(new Graph.Node<String>(parentName));
            for (Graph.Edge<Double, String> e : edges) {
                if (e.getChild().getValue().equals(childName)) {
                    containsInsert = true;
                }
            }
        }
        if (!containsInsert) {
            g.addEdge(insert);
            output.println("added edge " + String.format("%.3f", edgeLabel) + " from " + parentName + " to " + childName + " in " + graphName);
        }
    }

    private void listNodes(List<String> arguments) {
        if (arguments.size() != 1) {
            throw new CommandException("Bad arguments to ListNodes: " + arguments);
        }

        String graphName = arguments.get(0);
        listNodes(graphName);
    }


    private void listNodes(String graphName) {
        Graph<String, Double> g = graphs.get(graphName); //get the graph
        List<String> nodes = g.getNodes();
        Collections.sort(nodes);
        output.print(graphName + " contains:"); //print out the node names
        for (String s : nodes) {
            output.print(" " + s);
        }
        output.println();
    }

    private void listChildren(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to ListChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }


    private void listChildren(String graphName, String parentName) {
        Graph<String, Double> g = graphs.get(graphName); //get graph
        Graph.Node<String> parent = new Graph.Node<String>(parentName); //set parent
        List<Edge<Double, String>> edges = g.getEdges(parent);
        Collections.sort(edges, new PathEdgeComparator());
        output.print("the children of " + parentName + " in " + graphName + " are:");
        for (Edge<Double, String> e : edges) {
            output.print(" " + e.getChild().getValue() + "("  + String.format("%.3f", e.getLabel()) + ")");
        }
        output.println();
    }

    private void findPath(List<String> arguments) {
        if (arguments.size() != 3) {
            throw new CommandException("Bad arguments to FindPath: " + arguments);
        }

        String graphName = arguments.get(0);
        String start = arguments.get(1);
        String destination = arguments.get(2);
        findPath(graphName, start, destination);
    }

    private void findPath(String graphName, String start, String destination) {
        CampusPaths<String> campusPath = new CampusPaths<String>();
        Graph<String, Double> graph = graphs.get(graphName);
        Node<String> beginning = new Node<String>(start);
        Node<String> end = new Node<String>(destination);

        //Path<String> path = campusPath.findPath(graph, beginning, end);
        boolean invalid = false;
        Path<String> path = null;
        try {
            path = campusPath.findPath(graph, beginning, end);
        } catch (IllegalArgumentException e) {
            invalid = true;
            output.println(e.getLocalizedMessage());
        }

        if (!invalid) {
            output.println("path from " + start + " to " + end + ":");
            if (start.equals(destination)) {
                output.println("total cost: 0.000");
            } else if (path == null) {
                output.println("no path found");
            } else {
                for (Path<String>.Segment s : path) {
                    output.println(s.getStart() + " to " + s.getEnd() + " with weight " + String.format("%.3f", s.getCost()));
                }
                output.println(String.format("total cost: %.3f", path.getCost()));
            }
        }

    }

    public static class PathEdgeComparator implements Comparator<Edge<Double, String>> {

        @Override
        public int compare(Edge<Double, String> o1, Edge<Double, String> o2) {
            return o1.getLabel().compareTo(o2.getLabel());
        }

    }

    /**
     * This exception results when the input file cannot be parsed properly
     **/
    static class CommandException extends RuntimeException {

        public CommandException() {
            super();
        }

        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }
}
