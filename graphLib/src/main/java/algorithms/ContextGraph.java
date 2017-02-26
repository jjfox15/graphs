package algorithms;

import models.graphRepresentations.DirectedGraph;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sfox on 2/25/17.
 */
public class ContextGraph<V> {

    private Map<V, DjikstraContext<V>> contextMap;

    public ContextGraph(DirectedGraph<V> graph) {
        this.contextMap = new HashMap<>();
        // for each node in map, populate initial context for node
        for (V vertex: graph.getVerticies()) {
            contextMap.put(vertex, new DjikstraContext<>(vertex));
        }
    }

    public void setContext(V value, DjikstraContext context) {
        contextMap.put(value, context);
    }

    public DjikstraContext getContext(V value) {
        return contextMap.get(value);
    }

}
