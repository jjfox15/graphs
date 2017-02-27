package unitTests;

import algorithms.djikstra.Djikstra;
import models.base.Path;
import models.base.PathsFromSource;
import models.graphRepresentations.DirectedGraph;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * Created by sfox on 2/25/17.
 */
@Test(groups = {"unit"})
public class DjikstraUnitTest {

    @DataProvider(name = "djikstraProvider")
    public Object[][] djikstraProvider() {
        DirectedGraph<String> graph = new DirectedGraph<>();
        graph.addVertex("a");
        graph.addVertex("b");
        graph.addVertex("c");
        graph.addVertex("d");

        graph.addEdge("a", "b");
        graph.addEdge("b", "c");
        graph.addEdge("c", "d");
        graph.addEdge("b", "d");

        DirectedGraph<String> weightedGraph = new DirectedGraph<>();
        weightedGraph.addVertex("a");
        weightedGraph.addVertex("b");
        weightedGraph.addVertex("c");
        weightedGraph.addVertex("d");

        weightedGraph.addEdge("a", "b", 10f);
        weightedGraph.addEdge("a", "c");
        weightedGraph.addEdge("c", "b");

        return new Object[][] {

                // unweighted directed graphs
                {
                        "Simplest case. find node one edge away",
                        graph,
                        "a",
                        "b",
                        1f
                },
                {
                        "find node 2 edges away",
                        graph,
                        "a",
                        "c",
                        2f
                },
                {
                        "find node 2 edges away ",
                        graph,
                        "a",
                        "d",
                        2f
                },

                // weighted directed graphs
                {
                        "find node along longer node path which is shorter in terms of cost ",
                        weightedGraph,
                        "a",
                        "b",
                        2f
                },

        };
    }


    @Test(dataProvider = "djikstraProvider")
    public void getPathTest(String msg, DirectedGraph<String> graph, String source, String target, Float expectedShortestPath) {
        Djikstra<String> djikstraAlg = new Djikstra<>(graph);

        Path actualShortestPath = djikstraAlg.getShortestPath(source, target);
        Float cost = actualShortestPath.getCost();
        Assert.assertEquals(cost, expectedShortestPath, "Test: " + msg + " failed. Found incorrect shortest path. Expected: "
                + expectedShortestPath + " but found: " + cost);
        System.out.println("Found path: " + actualShortestPath);
        System.out.println();
    }


    @Test(dataProvider = "djikstraProvider")
    public void getPathsTest(String msg, DirectedGraph<String> graph, String source, String target, Float expectedShortestPath) {
        Djikstra<String> djikstraAlg = new Djikstra<>(graph);

        PathsFromSource<String> pathsFromSource = djikstraAlg.getPaths(source);
        Path<String> pathToTarget = pathsFromSource.getPath(target);
        Float cost = pathToTarget.getCost();
        Assert.assertEquals(cost, expectedShortestPath, "Test: " + msg + " failed. Found incorrect shortest path. Expected: "
                + expectedShortestPath + " but found: " + cost);

        for (Map.Entry<String, Path<String>> pathEntry: pathsFromSource.entrySet()) {
            if (pathEntry.getValue().getPath() != null) {
                System.out.println("Found path: " + pathEntry.getValue());
            } else {
                System.out.println("No path found for target: " + pathEntry.getKey());
            }
        }
        System.out.println();
    }

}
