package algorithms.djikstra;

import models.base.Edge;

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
}
