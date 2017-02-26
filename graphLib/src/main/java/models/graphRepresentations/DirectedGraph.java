package models.graphRepresentations;

import models.base.Edge;
import models.base.Node;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by sfox on 2/25/17.
 */
public class DirectedGraph<V> implements GraphRepresentation<V> {

    private Map<V, Node<V>> graphNodes;

    public DirectedGraph() {
        graphNodes = new HashMap<>();
    }

    @Override
    public void addVertex(V value) {
        Node newNode = new Node(value);
        graphNodes.put(value, newNode);
    }

    @Override
    public void addEdge(V start, V end) {
        Edge<V> newEdge = new Edge<>(start, end);
        if (!graphNodes.containsKey(start)) {
            addVertex(start);
        }
        Node<V> startNode = graphNodes.get(start);
        startNode.addNeighbor(newEdge);
        graphNodes.put(start, startNode);
    }

    @Override
    public void addEdge(Edge<V> edge) {
        addEdge(edge.getStart(), edge.getEnd());
    }

    public Node<V> getVertex(V value) {
        return graphNodes.get(value);
    }

    public Set<V> getVerticies() {
        return graphNodes.keySet();
    }

}
