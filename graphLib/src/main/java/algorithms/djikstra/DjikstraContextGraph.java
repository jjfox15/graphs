package algorithms.djikstra;

import algorithms.ContextGraph;
import models.graphRepresentations.DirectedGraph;

/**
 * Created by sfox on 2/25/17.
 */
public class DjikstraContextGraph<V> extends ContextGraph<V, DjikstraContext<V>> {

    public DjikstraContextGraph(DirectedGraph<V> graph) {
        super(graph);
    }

    @Override
    public DjikstraContext<V> buildContext(V vertex) {
        return new DjikstraContext<>(vertex);
    }
}