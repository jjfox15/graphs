package algorithms.djikstra;

import models.base.Edge;

/**
 * Created by sfox on 2/26/17.
 */
public class DefaultDjikstraConstraint<V> implements IDjikstraContraint<V> {

    @Override
    public float getShortestPathToNeighbor(DjikstraContext<V> top, DjikstraContext<V> neighbor, Edge<V> edge) {
        return top.getShortestPath() + edge.getCost();
    }

    @Override
    public boolean meetsConstraint(DjikstraContext<V> top, DjikstraContext<V> neighbor, Edge<V> edge) {
        return !neighbor.isVisited();
    }
}
