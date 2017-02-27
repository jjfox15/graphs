package algorithms.djikstra;

import algorithms.AlgContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sfox on 2/25/17.
 */
public class DjikstraContext<V> extends AlgContext<V> {

    private boolean visited;
    private Float shortestPath;
    private List<V> pathToVertex;

    public DjikstraContext(DjikstraContext<V> other) {
        super(other);
        this.visited = other.visited;
        this.shortestPath = new Float(other.shortestPath);
        this.pathToVertex = new ArrayList<>(other.pathToVertex);
    }

    public DjikstraContext(V value) {
        super(value);
        this.visited = false;
        this.shortestPath = Float.MAX_VALUE;
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
