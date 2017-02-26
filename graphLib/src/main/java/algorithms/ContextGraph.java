package algorithms;

import models.graphRepresentations.DirectedGraph;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sfox on 2/25/17.
 */
public abstract class ContextGraph<V, C extends DjikstraContext<V>> {

    private Map<V, C> contextMap;

    public ContextGraph(DirectedGraph<V> graph) {
        this.contextMap = new HashMap<>();
        // for each node in map, populate initial context for node
        for (V vertex: graph.getVerticies()) {
            contextMap.put(vertex, buildContext(vertex));
        }
    }

    public void setContext(V value, C context) {
        contextMap.put(value, context);
    }

    public DjikstraContext getContext(V value) {
        return contextMap.get(value);
    }

    public abstract C buildContext(V vertex);
}
