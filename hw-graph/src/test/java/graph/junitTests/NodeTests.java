package graph.junitTests;

import graph.Graph;
import graph.Graph.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.Rule;
import org.junit.rules.Timeout;

public class NodeTests {
    @Rule public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    Node<String> n1 = new Node<String>("n1");
    Node<String> n2 = new Node<String>("n2");
    Node<String> n3 = new Node<String>("n1");
    Node<String> nullNode;

    String valueN1 = "n1";

    // tests .getValue()
    @Test
    public void testGetValue() {
        assertEquals(valueN1, n1.getValue());
        try {
            nullNode.getValue();
        } catch (NullPointerException npe){
            System.out.println("Null Pointer Exception thrown. Null value.");
        }
    }

    // tests .equals(Node n)
    @Test
    public void testEquality() {
        assertEquals(true, n1.equals(n1)); // check equality against same Node
        assertEquals(false, n1.equals(n2)); // check equality against different Nodes
        assertEquals(true, n1.equals(n3)); // check equality with same label Nodes
    }

    // tests .toString()
    @Test
    public void testToString() {
        assertEquals(valueN1, n1.toString());
    }
}
