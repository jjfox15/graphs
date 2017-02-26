package models.graphRepresentations;

import models.base.Edge;

/**
 * Created by sfox on 2/25/17.
 */
public interface GraphRepresentation<V> {
    public void addVertex(V value);
    public void addEdge(V start, V end);
    public void addEdge(Edge<V> edge);
}
