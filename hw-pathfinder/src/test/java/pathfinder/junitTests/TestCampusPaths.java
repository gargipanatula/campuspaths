package pathfinder.junitTests;

import graph.Graph;
import graph.Graph.*;
import org.junit.Test;
import pathfinder.CampusPaths;

import static org.junit.Assert.assertEquals;

public class TestCampusPaths {

    CampusPaths<String> campusPath = new CampusPaths<String>();

    Graph<String, Double> graph = new Graph<String, Double>();
    Node<String> A = new Node<String>("A");
    Node<String> B = new Node<String>("B");
    Node<String> C = new Node<String>("C");
    Node<String> D = new Node<String>("D");

    Edge<Double, String> AB = new Edge<Double, String>(A, B, 2.0);

    // tests CampusPaths.findPath with one invalid Node
    @Test
    public void testOneInvalidNode() {
        graph.addNode(A);
        graph.addNode(B);
        graph.addEdge(AB);
        boolean threw = false;
        try {
            campusPath.findPath(graph, A, C);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        assertEquals(true, threw);
    }

    // tests CampusPaths.findPath with two invalid Nodes
    public void testTwoInvalidNodes() {
        graph.addNode(A);
        graph.addNode(B);
        graph.addEdge(AB);
        boolean threw = false;
        try {
            campusPath.findPath(graph, C, D);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        assertEquals(true, threw);
    }
}
