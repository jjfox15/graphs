package algorithms;

import java.util.List;

/**
 * Created by sfox on 2/25/17.
 */
public class DjikstraContext<V> {

    private V value;
    private boolean visited;
    private Float shortestPath;

    private List<V> pathToVertex;

    public DjikstraContext(V value) {
        this.value = value;
        visited = false;
        shortestPath = Float.MAX_VALUE;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }


    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public Float getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(Float shortestPath) {
        this.shortestPath = shortestPath;
    }

    public List<V> getPathToVertex() {
        return pathToVertex;
    }

    public void setPathToVertex(List<V> pathToVertex) {
        this.pathToVertex = pathToVertex;
    }
}
