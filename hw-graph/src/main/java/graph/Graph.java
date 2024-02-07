package graph;

import java.util.*;

/**
 * Graph represents a mutable collection of Nodes and Edges. The graph is directed, meaning that all Edges only
 * go in one direction. The Graph is also labeled, meaning that every Edge has a label of some sort. Labels can
 * be repeated between Edges, except in cases where Edges have the same parent and child Nodes. In this case,
 * the labels must be different. The same Node may not be added to the Graph twice. A Graph does not contain
 * null Nodes or Edges.
 *
 * Paths can be created to get from one Node in the Graph to another Node in the Graph. A Path is an ordered
 * list of Edges, where an Edge to some Node is immediately followed by an edge from that node. A Path may
 * traverse a particular Edge any number of times.
 */

public class Graph<N, E> {

    // Abstraction Function: Represents a Map of Nodes and Edges, where the key is a Node and the value is a Set
    // of Edges of which the key Node is a parent. A Graph is composed of unique Nodes (meaning that all the
    // Nodes have different values) and unique Edges (meaning that all Edges that have the same parent and
    // child Nodes must have different labels). A Graph does not include null Nodes or Edges.

    // Representation Invariant: for each Node k in map.keySet(), k != null. for each Edge e in map.get(k), where
    // k is any one Node in the Graph, e != null. For two equal Edges e1 and e2, if map.containsValue(e1) then e2
    // cannot be added to the Graph. For two equal Nodes n1 and n2, if map.containsKey(n1) then n2 cannot be added
    // to the Graph.

    // The collection of Nodes and Edges in the Graph
    private Map<Node<N>, Set<Edge<E, N>>> map;

    // Determines whether the Graph class will undergo debugging
    private final boolean GRAPH_DEBUG = false;

    /**
     * Creates a new Graph with 0 Nodes and 0 Edges.
     *
     * @spec.effects creates a new Graph
     */
    public Graph() {
        this.map = new HashMap<Node<N>, Set<Edge<E, N>>>();
        checkRep();
    }

    /**
     * Adds a Node to the Graph. If the Node is already present in the Graph, then the Node is not
     * added to the Graph. If a Node is null, then it is not added to the Graph.
     *
     * @spec.modifies adds a Node to the Graph
     * @spec.requires n is not null and that the n is not already in the Graph.
     * @param n the Node that is being added to the Graph.
     */
    public void addNode(Node<N> n) {
        checkRep();
        boolean contains = !map.containsKey(n);
        if (n != null && !map.containsKey(n)) {
            map.put(n, new HashSet<Edge<E, N>>());
        }
        checkRep();
    }

    // adds an edge into the graph. the parent and child nodes of the graph must already be in the graph.
    public void addEdge(Edge<E, N> edge) {
        if (edge != null) {

            // ensures Edge is not already in the Graph
            Set<Edge<E, N>> edges = map.get(edge.getParent());
            if (!edges.contains(edge)) {
                Node<N> parent = new Node<N>(edge.getParent().getValue());
                Node<N> child = new Node<N>(edge.getChild().getValue());

                // ensures parent and child are already in graph
                if (map.containsKey(parent) && map.containsKey(child)) {
                    map.get(parent).add(new Edge<E, N>(edge.getParent(), edge.getChild(), edge.getLabel()));
                }
            }
        }
    }

    /**
     * Returns an array of the values of the Nodes that are the children of the given Node.
     * The given Node must be in the Graph and not be null. Otherwise, a NullPointerException
     * is thrown.
     *
     * @param n the Node that is the parent of the Nodes whose values are being returned
     * @throws NullPointerException if n is null or is not in the Graph
     * @return a String array of the values of the Nodes that are the children of n
     */

    @SuppressWarnings("unchecked")
    public ArrayList<N> getChildren(Node<N> n) {
        checkRep();
        // null check
        if (n.getValue() == null || !map.containsKey(n)) {
            checkRep();
            throw new NullPointerException();
        }

        Set<Edge<E, N>> edges = map.get(n);

        //N[] childLabels = (N[]) new Object[edges.size()];
        ArrayList<N> childLabels = new ArrayList<N>(edges.size());
        //adding child labels to an array
        for (Edge<E, N> e : edges) {
            childLabels.add(e.getChild().getValue());
        }

        checkRep();
        return childLabels;
    }

    /**
     * Returns an array that contains the values of all of the Nodes in the Graph. The Graph must
     * not be null, otherwise a NullPointerException will be thrown.
     *
     * @return a List of the values of the Nodes that are in the Graph.
     */
    public List<N> getNodes() {
        checkRep();

        List<N> nodesList = new ArrayList<N>();
        for (Node<N> n : map.keySet()) {
            nodesList.add(n.getValue());
        }
        return nodesList;
    }

    /**
     * Lists the Edges in a Graph for which the given Node is a parent. The Graph must not be null,
     * otherwise a NullPointerException is thrown. The given Node must be in the Graph.
     * Otherwise, a NullPointerException is thrown.
     *
     * @spec.requires this Graph is not null.
     * @param n Node whose Edges are being returned
     * @throws NullPointerException if n is not in the Graph
     * @return a List of all the Edges connected to Node n in the Graph
     */
    public List<Edge<E, N>> getEdges(Node<N> n) {
        checkRep();
        if (!map.containsKey(n)) {
            throw new NullPointerException();
        }
        List<Edge<E, N>> edges = new ArrayList<Edge<E, N>>();
        for (Edge<E, N> e : map.get(n)) {
            edges.add(new Edge<E, N>(e.getParent(), e.getChild(), e.getLabel()));
        }

        checkRep();
        return edges;
    }

    @SuppressWarnings("unchecked")
    private void checkRep() {
        if (GRAPH_DEBUG) {

            Node<N>[] nodes = (Node<N>[]) new Object[map.keySet().size()];
            for (int i = 0; i < map.keySet().size(); i++) {
                for (Node<N> n : map.keySet()) {
                    nodes[i] = new Node<N>(n.getValue());
                }
            }
            if (nodes.length > 0) {
                assert(nodes[0] != null) : "Contains a null node.";

                for (int i = 1; i < nodes.length; i++) {
                    assert(nodes[i - 1] != null) : "Contains a null node.";
                    assert(nodes[i - 1].equals(nodes[i])) : "Contains duplicate nodes.";
                }
            }

            for (Node<N> n : map.keySet()) {

                Edge<E, N>[] edges = (Edge <E, N>[]) new Object[map.get(n).size()];
                //Edge[] edges = new Edge[map.get(n).size()];
                for (Edge<E, N> e : map.get(n)) {
                    for (int i = 0; i < edges.length; i++) {
                        edges[i] = new Edge<E, N>(e.getParent(), e.getChild(), e.getLabel());
                    }
                }
                if (edges.length > 0) {
                    assert(edges[0] != null) : "Contains a null edge.";
                    for (int i = 1; i < edges.length - 1; i++) {
                        assert(edges[i - 1] != null) : "Contains a null edge.";
                        assert(edges[i - 1].equals(edges[i])) : "Contains duplicate edges.";
                    }
                }
            }
        }
    }

    /**
     * Edge represents an immutable connection between two Nodes. Edges are one directional, meaning that
     * the fact that there is an edge from Node A to Node B does not imply that there is an edge from Node
     * B to Node A. An Edge starts from a parent Node and stops at a child Node.
     *
     * Each Edge has a label containing information. Multiple edges may have the same label. There can be
     * any number of edges between two nodes. However, no 2 edges with the same parent and child Nodes can
     * have the same edge label.
     */

    public static class Edge<E, N> {

        // Abstraction Function: Represents a unidirectional connection from the parent Node to the child
        // Node. Has the label 'label'.

        // Representation Invariant: label != null && parent != null && child != null

        // The label of the Edge
        private E label;

        // The parent Node of the Edge
        private Node<N> parent;

        // The child Node of the Edge
        private Node<N> child;

        // Determines whether the Graph class will undergo debugging
        private final boolean EDGE_DEBUG = false;

        /**
         * Constructs an Edge with a parent Node, child Node, and label. If any of the parameters are
         * null, the Edge is not created.
         *
         * @spec.effects Constructs a new Edge
         * @spec.requires Neither parent, child are null.
         * @param parent the Node that the edge is originating from.
         * @param child the Node that the edge is ending at.
         * @param label the label of the Node.
         */
        public Edge (Node<N> parent, Node<N> child, E label) {
            if (parent != null && child != null && label != null) {
                this.parent = parent;
                this.child = child;
                this.label = label;
            }
            checkRep();
        }

        /**
         * Gets the parent Node of this Edge. This Edge must not be null.
         *
         * @return the parent Node of this Edge
         */
        public Node<N> getParent() {
            checkRep();
            return new Node<>(this.parent.getValue());
        }

        /**
         * Gets the child Node of this Edge. This Edge must not be null.
         *
         * @throws NullPointerException if the Edge is null.
         * @return the child Node of this Edge
         */
        public Node<N> getChild() {
            checkRep();
            return new Node<>(this.child.getValue());
        }

        /**
         * Gets the label of this Edge. This Edge must not be null.
         *
         * @throws NullPointerException if the Edge is null.
         * @return the label of this Edge
         */
        public E getLabel() {
            checkRep();
            return this.label;
        }

        /**
         * Returns an Edge whose parent is the child of this Node, and whose child is the parent of this Node.
         * The label of the reversed Edge is the same as that of the original Edge. The original Edge must
         * not be null. This Edge must not be null.
         *
         * @throws NullPointerException if this Edge is null.
         * @return an Edge whose parent is the child of this Node, and whose child is the parent of this Node.
         */
        public Edge<E, N> reverseEdge() {
            checkRep();
            return new Edge<>(this.getChild(), this.getParent(), this.getLabel());
        }

        /**
         * Standard equality operation.
         *
         * @param obj the object to be compared for equality
         * @return true if and only if 'obj' is an instance of an Edge and 'this' and 'obj' represent the
         * same Edge.
         */
        @Override
        public boolean equals(Object obj) {
            checkRep();
            if (!(obj instanceof Edge<?, ?>)) {
                checkRep();
                return false;
            }
            Edge<?, ?> e = (Edge<?, ?>) obj;
            boolean sameParents = this.getParent().equals(e.getParent());
            boolean sameChildren = this.getChild().equals(e.getChild());
            boolean sameLabel = this.getLabel().equals(e.getLabel());
            checkRep();
            return sameParents && sameChildren && sameLabel;
        }

        /**
         * Standard hashCode function.
         *
         * @return an int that all objects equal to this will also return
         */
        @Override
        public int hashCode() {
            checkRep();
            return this.getLabel().hashCode() + this.getParent().hashCode() + this.getChild().hashCode();
        }

        /**
         * Returns a string representation of this Edge.
         *
         * @return a String representation of the Edge represented by this.
         */
        @Override
        public String toString() {
            checkRep();
            return this.getLabel() + ": from " + this.getParent().toString() + " to " + this.getChild().toString();
        }

        private void checkRep() {
            if (EDGE_DEBUG) {
                assert (this.label != null) : "label is null";
                assert (this.parent != null) : "parent is null";
                assert (this.child != null) : "child is null";
            }
        }
    }



    /**
     * Node represents an immutable point on the graph. It has a value that is in the form of a String.
     * Clients can create Nodes, see the value of a Node, and check its equality to other nodes.
     */

    public static class Node<N> {

        // Abstraction Function: represents a vertex on the graph that has a value 'value'.

        // Representation Invariant: value != null

        // The value of the Node.
        private final N value;

        // Determines whether the Graph class will undergo debugging
        private final boolean NODE_DEBUG = false;

        /**
         * @spec.effects Constructs a new Node with value n
         * @spec.requires value is not null
         * @param value the value associated with the Node.
         */
        public Node(N value) {
            this.value = value;
            checkRep();
        }

        /**
         * Gets the value of this Node.
         *
         * @return the value of this Node.
         */
        public N getValue() {
            checkRep();
            return this.value;
        }

        /**
         * Standard equality operation.
         *
         * @param obj the object to be compared for equality
         * @return true if and only if 'obj' is an instance of a Node and 'this' and 'obj' represent the
         * same Node.
         */
        @Override
        public boolean equals(Object obj) {
            checkRep();
            if (!(obj instanceof Node<?>)) {
                return false;
            }
            Node<?> n = (Node<?>) obj;
            checkRep();
            return this.value.equals(n.value);
        }

        /**
         * Standard hashCode function.
         *
         * @return an int that all objects equal to this will also return
         */
        @Override
        public int hashCode() {
            checkRep();
            return this.getValue().hashCode();
        }

        /**
         * Returns a string representation of this Node.
         *
         * @return a String representation of the Node represented by this.
         */
        @Override
        public String toString() {
            checkRep();
            return this.value.toString();
        }

        private void checkRep() {
            if (NODE_DEBUG) {
                assert (value != null) : "Node's value is null";
            }
        }
    }
}
