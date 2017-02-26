package algorithms.floyd_warshall;

import algorithms.ContextGraph;
import models.graphRepresentations.DirectedGraph;

/**
 * Created by sfox on 2/26/17.
 */
public class FloydMarshallContextGraph<V> extends ContextGraph<V, FloydWarshallContext<V>> {

    public FloydMarshallContextGraph(DirectedGraph<V> graph) {
        super(graph);
    }

    @Override
    public FloydWarshallContext<V> buildContext(V vertex) {
        return new FloydWarshallContext<>(vertex);
    }

}
