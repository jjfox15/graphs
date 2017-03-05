package algorithms.djikstra;

import models.base.Edge;
import models.graphRepresentations.DirectedGraph;

/**
 * Created by sfox on 2/26/17.
 */
public interface IDjikstraContraint<V> {

    /**
     * Whether or not the given neighbor context is valid to be inserted into the priority queue given top
     * @param top
     * @param edge
     * @return
     */
    float getShortestPathToNeighbor(DjikstraContext<V> top, DjikstraContext<V> neighbor, Edge<V> edge);


    /**
     * Whether or not the given neighbor context is valid to be inserted into the priority queue given top
     * @param top
     * @param neighbor
     * @return
     */
    boolean meetsConstraint(DjikstraContext<V> top, DjikstraContext<V> neighbor, Edge<V> edge);

    /**
     * When we are searching for a target 'done' state which meets a predefined set of constraints defined
     * meetsConstraint this defines how we insert the 'done' state into the graph
     *
     */
    default boolean completionNode(DjikstraContext<V> top) {
        return false;
    }


    /**
     * When we are searching for a target 'done' state which meets a predefined set of constraints defined
     * meetsConstraint this defines how we insert the 'done' state into the graph
     *
     * @param graph
     */
    default void onCompletionNode(DirectedGraph<V> graph, V top, V done) {

    }
}
