package models.graphRepresentations;

import models.base.Edge;

/**
 * Created by sfox on 2/25/17.
 */
public abstract class GraphRepresentation<V> {
    public abstract void addVertex(V value);
    public void addEdge(V start, V end) {
        addEdge(start, end, 1);
    }
    public abstract void addEdge(V start, V end, float cost);
    public abstract void addEdge(Edge<V> edge);
}
