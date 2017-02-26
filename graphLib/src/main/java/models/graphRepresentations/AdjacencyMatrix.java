package models.graphRepresentations;

import models.base.Edge;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by sfox on 2/25/17.
 */
public class AdjacencyMatrix<V> extends GraphRepresentation<V> {

    private Map<V, Integer> vertices;
    private Float[][] adjencyMatrix;
    private Integer nextIdx = 0;
    private final Integer numNodes;
    public AdjacencyMatrix(int numNodes) {
        this.numNodes = numNodes;
        this.vertices = new HashMap<>();
        this.adjencyMatrix = new Float[numNodes][numNodes];
    }

    @Override
    public void addVertex(V value) {
        vertices.put(value, nextIdx++);
    }

    @Override
    public void addEdge(V start, V end, float cost) {
        if (!vertices.containsKey(start)) {
            vertices.put(start, nextIdx++);
        }
        if (!vertices.containsKey(end)) {
            vertices.put(end, nextIdx++);
        }
        adjencyMatrix[vertices.get(start)][vertices.get(end)] = cost;
    }

    @Override
    public void addEdge(Edge<V> edge) {
        V start = edge.getStart();
        V end = edge.getEnd();
        float cost = edge.getCost();
        addEdge(start, end, cost);
    }

    public void setValue(int start, int end, Float cost) {
        if (start < numNodes && end < numNodes) {
            adjencyMatrix[start][end] = cost;
        }
    }

    public Float getValue(int start, int end) {
        if (start < numNodes && end < numNodes) {
            return adjencyMatrix[start][end];
        } else {
            return null;
        }
    }


    public Float[][] getAdjencyMatrix() {
        return adjencyMatrix;
    }

    public Set<V> getVertices() {
        return vertices.keySet();
    }
}
