package algorithms.floyd_warshall;

import models.base.AllPaths;
import models.graphRepresentations.AdjacencyMatrix;
import models.graphRepresentations.DirectedGraph;
import models.translations.AdjacencyMatrixTranslator;

/**
 * Created by sfox on 2/26/17.
 */
public class FloydWarshall<V> {

    private FloydMarshallContextGraph<V> contextGraph;
    private AdjacencyMatrix<V> adjacencyMatrix;
    private AdjacencyMatrixTranslator<V> adjacencyMatrixTranslator = new AdjacencyMatrixTranslator<>();

    public FloydWarshall(AdjacencyMatrix<V> adjacencyMatrix) {
        this.adjacencyMatrix = adjacencyMatrix;
    }

    public FloydWarshall(DirectedGraph<V> graph) {
        this.adjacencyMatrix = adjacencyMatrixTranslator.translateFromBase(graph);
    }

    public AdjacencyMatrix<V> allPairsShortestPaths() {
        int numNodes = adjacencyMatrix.getVertices().size();
        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                for (int k = 0; k < numNodes; k++) {
                    Float existingShortest = adjacencyMatrix.getValue(j, k);
                    Float potentialShortest = adjacencyMatrix.getValue(j, i) + adjacencyMatrix.getValue(i, k);
                    if (potentialShortest < existingShortest) {
                        adjacencyMatrix.setValue(j, k, potentialShortest);
                    }
                }
            }
        }
        return adjacencyMatrix;
    }
}
