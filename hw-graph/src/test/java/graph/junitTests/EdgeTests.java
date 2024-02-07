package graph.junitTests;

import graph.Graph.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.Rule;
import org.junit.rules.Timeout;

public class EdgeTests {
    @Rule public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    // Nodes
    Node<String> n1 = new Node<String>("n1");
    Node<String> n2 = new Node<String>("n2");
    Node<String> n3 = new Node<String>("n3");
    Object n4 = n1;

    // Edges
    Edge<String, String> e12 = new Edge<String, String>(n1, n2, "e12");
    Edge<String, String> e13 = new Edge<String, String>(n1, n3, "e13");
    Edge<String, String> e21 = new Edge<String, String>(n2, n1, "e21");
    Edge<String, String> e22 = new Edge<String, String>(n2, n2, "e22");
    Edge<String, String> e31 = new Edge<String, String>(n3, n1, "e31");
    Edge<String, String> nullEdge = null;

    // Edge Labels
    String l12 = "<n1, n2>";
    String l22 = "<n2, n2>";
    String l31 = "<n3, n1>";
    String l13 = "<n1, n3>";

    // Edge Strings
    String s12 = "e12: from n1 to n2";
    String s22 = "e22: from n2 to n2";
    String s31 = "e31: from n3 to n1";
    String s13 = "e13: from n1 to n3";

    // tests .getParent()
    @Test
    public void testGetParent() {
        assertEquals(n1, e12.getParent());
        assertEquals(n2, e22.getParent());
        assertEquals(n3, e31.getParent());
        assertEquals(n1, e13.getParent());
        try {
            nullEdge.getParent();
        } catch (NullPointerException npe) {
            System.out.println("Null Pointer Exception thrown. Null Edge.");
        }
    }


    // tests .getChild()
    @Test
    public void testGetChild() {
        assertEquals(n1, e21.getChild());
        assertEquals(n2, e22.getChild());
        assertEquals(n1, e31.getChild());
        assertEquals(n3, e13.getChild());
        try {
            nullEdge.getChild();
        } catch (NullPointerException npe) {
            System.out.println("Null Pointer Exception thrown. Null Edge.");
        }
    }

    // tests .getLabel()
    @Test
    public void testGetLabel() {
        assertEquals("e12", e12.getLabel());
        assertEquals("e22", e22.getLabel());
        assertEquals("e31", e31.getLabel());
        assertEquals("e13", e13.getLabel());
        try {
            nullEdge.getLabel();
        } catch (NullPointerException npe) {
            System.out.println("Null Pointer Exception thrown. Null Edge.");
        }
    }

    // tests .reverseEdge()
    @Test
    public void testReverseEdge() {
        assertEquals(n1, e21.reverseEdge().getParent());
        assertEquals(n2, e21.reverseEdge().getChild());

        assertEquals(n2, e22.reverseEdge().getParent());
        assertEquals(n2, e22.reverseEdge().getChild());

        assertEquals(n3, e13.reverseEdge().getParent());
        assertEquals(n1, e13.reverseEdge().getChild());

        assertEquals(n1, e31.reverseEdge().getParent());
        assertEquals(n3, e31.reverseEdge().getChild());

        try {
            nullEdge.reverseEdge();
        } catch (NullPointerException npe){
            System.out.println("Null Pointer Exception thrown. Null Edge.");
        }
    }

    // tests .equals()
    @Test
    public void testEquals() {
        assertEquals(false, e12.equals(e21));
        assertEquals(true, e22.equals(e22));
    }

    // tests the equality of Edges that have been reversed
    @Test
    public void testEqualsReversed() {
        assertEquals(false, e13.equals(e31.reverseEdge()));
        assertEquals(true, e22.equals(e22.reverseEdge()));
    }

    // tests .toString()
    @Test
    public void testToString() {
        assertEquals(s12, e12.toString());
        assertEquals(s22, e22.toString());
        assertEquals(s31, e31.toString());
        assertEquals(s13, e13.toString());
    }
}
