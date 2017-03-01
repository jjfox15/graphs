package algorithms.djikstra;

import models.base.Edge;
import models.base.Path;
import models.base.PathsFromSource;
import models.graphRepresentations.DirectedGraph;
import models.base.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Created by sfox on 2/25/17.
 */
public class Djikstra<V> {

    private DirectedGraph<V> graph;
    private DjikstraContextGraph<V> contextGraph;
    private PriorityQueue<DjikstraContext<V>> priorityQueue;
    private IDjikstraContraint<V> constrainer;

    public Djikstra(DirectedGraph<V> graph) {
        this(graph, new DefaultDjikstraConstraint<>());
    }

    public Djikstra(DirectedGraph<V> graph, IDjikstraContraint<V> constrainer) {
        this.contextGraph = new DjikstraContextGraph<>(graph);
        this.graph = graph;
        Comparator<DjikstraContext<V>> comparator = new Comparator<DjikstraContext<V>>() {
            @Override
            public int compare(DjikstraContext<V> o1, DjikstraContext<V> o2) {
                return o1.getShortestPath().compareTo(o2.getShortestPath());
            }
        };
        this.priorityQueue = new PriorityQueue<>(comparator);
        this.constrainer = constrainer;
    }

    public Path<V> getShortestPath(V source, V target) {
        buildPaths(source, target);
        return getPath(target);
    }

    public PathsFromSource<V> getPaths(V source) {
        buildPaths(source, null);
        PathsFromSource<V> pathsFromSource = new PathsFromSource<>(source);
        for (V target: graph.getVerticies()) {
            Path<V> path = getPath(target);
            pathsFromSource.addPath(target, path);
        }
        return pathsFromSource;
    }

    /**
     * builds path to target from source. If given target is null. Will find shortest path to all
     * targets from given source.
     * @param source
     * @param target
     */
    private void buildPaths(V source, V target) {
        DjikstraContext sourceContext = contextGraph.getContext(source);
        sourceContext.setShortestPath(0f);

        sourceContext.setPathToVertex(new ArrayList<>(Arrays.asList(source)));
        contextGraph.setContext(source, sourceContext);
        priorityQueue.add(sourceContext);

        while (!priorityQueue.isEmpty()) {
            DjikstraContext<V> top = priorityQueue.poll();
            V value = top.getValue();
            Node graphNode = graph.getVertex(value);

            if (null != target && top.getValue().equals(target)) {
                break;
            }

            // set this node as visited in the context graph
            contextGraph.getContext(value).setVisited(true);

            Set<Edge<V>> edges = graphNode.getPathsToNeighbors();


            for (Edge<V> edge : edges) {

                DjikstraContext<V> neighborContext = contextGraph.getContext(edge.getEnd());

                if(constrainer.meetsConstraint(top, neighborContext, edge)) {
                    Float pathToNeighborCost = constrainer.getShortestPathToNeighbor(top, neighborContext, edge);
                    if (pathToNeighborCost < neighborContext.getShortestPath()) {
                        List<V> pathToNeighbor = new ArrayList(top.getPathToVertex());
                        V neighborValue = neighborContext.getValue();

                        pathToNeighbor.add(neighborValue);
                        neighborContext.setShortestPath(pathToNeighborCost);
                        neighborContext.setPathToVertex(pathToNeighbor);
                        contextGraph.setContext(neighborContext.getValue(), neighborContext);
                        // copy object to avoid mutating objects already in the queue
                        priorityQueue.add(new DjikstraContext<>(neighborContext));
                    }
                }
            }
        }
    }

    private Path<V> getPath(V target) {
        DjikstraContext<V> targetContext = contextGraph.getContext(target);
        if (null != targetContext) {
            return new Path<>(targetContext.getPathToVertex(), targetContext.getShortestPath());
        } else {
            return null;
        }
    }
}
