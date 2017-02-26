package models.graphRepresentations;

import models.base.CostEvaluator;
import models.base.Edge;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by sfox on 2/25/17.
 */
public class AdjacencyMatrix<V> extends GraphRepresentation<V> {

    private Set<V> vertices;
    private Map<V, Map<V, Float>> adjencyMatrix;
    private CostEvaluator<V> costEvaluator;

    public AdjacencyMatrix(CostEvaluator<V> costEvaluator) {
        this.vertices = new HashSet<>();
        this.adjencyMatrix = new HashMap<>();
        this.costEvaluator = costEvaluator;
    }

    @Override
    public void addVertex(V value) {
        vertices.add(value);
    }

    @Override
    public void addEdge(V start, V end, float cost) {
        if (vertices.contains(start) && vertices.contains(end)) {
            if (!adjencyMatrix.containsKey(start)) {
                adjencyMatrix.put(start, new HashMap<>());
            }
            adjencyMatrix.get(start).put(end, cost);
        }
    }

    @Override
    public void addEdge(Edge<V> edge) {
        V start = edge.getStart();
        V end = edge.getEnd();
        float cost = edge.getCost();
        if (vertices.contains(start) && vertices.contains(end)) {
            if (!adjencyMatrix.containsKey(start)) {
                adjencyMatrix.put(start, new HashMap<>());
            }
            adjencyMatrix.get(start).put(end, cost);
        }
    }

    public Map<V, Map<V, Float>> getAdjencyMatrix() {
        return adjencyMatrix;
    }

    public void setAdjencyMatrix(Map<V, Map<V, Float>> adjencyMatrix) {
        this.adjencyMatrix = adjencyMatrix;
    }

    public CostEvaluator<V> getCostEvaluator() {
        return costEvaluator;
    }

    public void setCostEvaluator(CostEvaluator<V> costEvaluator) {
        this.costEvaluator = costEvaluator;
    }

    public Set<V> getVertices() {
        return vertices;
    }

    public void setVertices(Set<V> vertices) {
        this.vertices = vertices;
    }

}
