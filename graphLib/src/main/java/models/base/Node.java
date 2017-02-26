package models.base;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sfox on 2/25/17.
 */
public class Node<V> {
    private V value;
    private Set<Edge<V>> pathsToNeighbors;

    public Node(V value) {
        this.value = value;
        this.pathsToNeighbors = new HashSet<>();
    }

    public void addNeighbor(Edge<V> edge) {
        if (value.equals(edge.getStart())) {
            pathsToNeighbors.add(edge);
        }
    }

    public Set<Edge<V>> getPathsToNeighbors() {
        return pathsToNeighbors;
    }
}
