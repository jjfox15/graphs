package models.translations;

import models.base.Edge;
import models.graphRepresentations.AdjacencyMatrix;
import models.graphRepresentations.DirectedGraph;
import models.base.Node;

/**
 * Created by sfox on 2/25/17.
 */
public class AdjacencyMatrixTranslator<V> implements GraphTranslator<AdjacencyMatrix<V>, V>  {

    @Override
    public DirectedGraph<V> translateToBase(AdjacencyMatrix<V> graph) {
        return null;
    }

    @Override
    public AdjacencyMatrix<V> translateFromBase(DirectedGraph<V> graph) {
        AdjacencyMatrix<V> adjacencyMatrix = new AdjacencyMatrix<>(graph.getVerticies().size());
        for (V vertex: graph.getVerticies()) {
            Node<V> graphNode = graph.getVertex(vertex);
            for (Edge<V> edge: graphNode.getPathsToNeighbors()) {
                adjacencyMatrix.addEdge(edge);
            }
        }
        return adjacencyMatrix;
    }
}

