package graph.junitTests;

import graph.Graph;
import graph.Graph.*;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;

import java.util.*;

public class GraphTests {
    @Rule public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    Graph<String, String> graph1 = new Graph<String, String>();
    Graph<String, String> graph2;

    // Nodes
    Node<String> n1 = new Node<String>("n1");
    Node<String> n2 = new Node<String>("n2");
    Node<String> n3 = new Node<String>("n3");
    Node<String> n4 = new Node<String>("n1");
    Node<String> n5 = new Node<String>("n5");
    Node<String> nullNode = null;

    // Edges
    Edge<String, String> e12 = new Edge<String, String>(n1, n2, "e12");
    Edge<String, String> e21 = new Edge<String, String>(n2, n1, "e21");
    Edge<String, String> e23 = new Edge<String, String>(n2, n3, "e23");
    Edge<String, String> e22 = new Edge<String, String>(n2, n2, "e22");
    Edge<String, String> e45 = new Edge<String, String>(n4, n5, "e45");

    // Paths
    List<Edge<String, String>> p12 = new ArrayList<Edge<String, String>>();
    List<Edge<String, String>> p23 = new ArrayList<Edge<String, String>>();
    List<Edge<String, String>> p22 = new ArrayList<Edge<String, String>>();
    List<Edge<String, String>> p45 = new ArrayList<Edge<String, String>>();
    List<Edge<String, String>> p54 = new ArrayList<Edge<String, String>>();

    // tests .getChildren()
    @Test
    public void testGetChildren() {
        graph1.addNode(nullNode);
        try {
            graph1.getChildren(nullNode);
        } catch (NullPointerException npe){
            System.out.println("Null Pointer Exception thrown. Null Node.");
        }
    }

    // tests .getNodes()
    @Test
    public void testGetNodes() {
        try {
            graph2.getNodes();
        } catch (NullPointerException npe){
            System.out.println("Null Pointer Exception thrown. Null Graph.");
        }
    }

    //tests .getEdges()
    @Test
    public void testGetEdges() {
        try {
            graph2.getEdges(n1);
        } catch (NullPointerException npe){
            System.out.println("Null Pointer Exception thrown. Null Graph.");
        }
        try {
            graph1.getEdges(nullNode);
        } catch (NullPointerException npe) {
            System.out.println("Null Pointer Exception thrown. Null Node.");
        }
    }
}
