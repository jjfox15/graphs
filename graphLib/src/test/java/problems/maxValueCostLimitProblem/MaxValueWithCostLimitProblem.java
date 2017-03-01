package problems.maxValueCostLimitProblem;

import algorithms.djikstra.Djikstra;
import algorithms.djikstra.DjikstraContext;
import algorithms.djikstra.IDjikstraContraint;
import models.base.Edge;
import models.base.Path;
import models.base.PathsFromSource;
import models.graphRepresentations.DirectedGraph;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import problems.maxValueCostLimitProblem.rosterTestCases.ForcedSmartTestCase;

import java.util.Random;

/**
 * Created by sfox on 2/26/17.
 */
@Test(groups = {"unit"})
public class MaxValueWithCostLimitProblem {

    @DataProvider(name = "maxValueWithCostLimitProvider")
    public Object[][] maxValueWithCostLimitProvider() {


        return new Object[][]{
                simpleTestCase(),
                ForcedSmartTestCase.get(),
//                lotsOfPlayersTestCase()
        };
    }



    public Object[] simpleTestCase() {

        DirectedGraph<Player> graph = new DirectedGraph<>();

        Player player1 = new Player("player 1", 10f, 22.5f);
        Player player2 = new Player("player 2", 10f, 2.5f);
        Player player3 = new Player("player 3", 10f, 12.5f);
        Player player4 = new Player("player 4", 10f, 30f);
        Player player5 = new Player("player 5", 10f, 20f);

        graph.addVertex(player1);
        graph.addVertex(player2);
        graph.addVertex(player3);
        graph.addVertex(player4);
        graph.addVertex(player5);

        // each player can be chosen after any other (for now) so set up all edges. The cost will be the negative of the
        // value added by the second player. (trick for maximizing value)
        for (Player player : graph.getVerticies()) {
            for (Player secondPlayer : graph.getVerticies()) {
                if (!player.equals(secondPlayer)) {
                    graph.addEdge(player, secondPlayer, -1 * secondPlayer.getAvgPoints());
                }
            }
        }

        // insert 'source' and 'done' nodes which will be the source and target states.
        Player start = new Player("start", 0f, 0f);
        Player done = new Player("done", 0f, 0f);
        graph.addVertex(start);
        graph.addVertex(done);
        for (Player player : graph.getVerticies()) {
            if (!player.equals(start) && !player.equals(done)) {
                graph.addEdge(start, player, -1 * player.getAvgPoints());

                // will add done as a neighbor once we have hit the highest available number of points within the constraints.
                // done should be set with a min value of infinity so it is polled
                graph.addEdge(player, done, -1 * done.getAvgPoints());
            }
        }

        // set the rules for a valid team.
        Float maxTeamSalary = 30f;
        IDjikstraContraint<Player> pathConstraints = new IDjikstraContraint<Player>() {

            @Override
            public float getShortestPathToNeighbor(DjikstraContext<Player> top, DjikstraContext<Player> neighbor, Edge<Player> edge) {
                Float pointInPathPlusNeighbor = 0f;
                for (Player playerInPath : top.getPathToVertex()) {
                    pointInPathPlusNeighbor -= playerInPath.getAvgPoints();
                }
                pointInPathPlusNeighbor += edge.getCost();
                return pointInPathPlusNeighbor;
            }

            @Override
            public boolean meetsConstraint(DjikstraContext<Player> top, DjikstraContext<Player> neighbor, Edge<Player> edge) {
                if (neighbor.isVisited()) {
                    return false;
                }
                Float costOfPathPlusNeighbor = 0f;
                for (Player playerInPath : top.getPathToVertex()) {
                    costOfPathPlusNeighbor += playerInPath.getCost();
                }
                costOfPathPlusNeighbor += neighbor.getValue().getCost();
                return costOfPathPlusNeighbor <= maxTeamSalary && getShortestPathToNeighbor(top, neighbor, edge) < neighbor.getShortestPath();
            }
        };


        return new Object[]

                // unweighted directed graphs
                {
                        "Simplest case. find node one edge away",
                        graph,
                        start,
                        done,
                        pathConstraints,
                        -72.5f
                }
                ;

    }



    public Object[] lotsOfPlayersTestCase() {

        DirectedGraph<Player> graph = new DirectedGraph<>();

        Random rand = new Random();
        for (int i = 0; i < 2000; i++) {
            Player player = new Player("player " + i, Float.valueOf(25 - rand.nextInt(24)), Float.valueOf(50 - rand.nextInt(25)));
            graph.addVertex(player);
        }


        // each player can be chosen after any other (for now) so set up all edges. The cost will be the negative of the
        // value added by the second player. (trick for maximizing value)
        for (Player player : graph.getVerticies()) {
            for (Player secondPlayer : graph.getVerticies()) {
                if (!player.equals(secondPlayer)) {
                    graph.addEdge(player, secondPlayer, -1 * secondPlayer.getAvgPoints());
                }
            }
        }

        // insert 'source' and 'done' nodes which will be the source and target states.
        Player start = new Player("start", 0f, 0f);
        Player done = new Player("done", 0f, 0f);
        graph.addVertex(start);
        graph.addVertex(done);
        for (Player player : graph.getVerticies()) {
            if (!player.equals(start) && !player.equals(done)) {
                graph.addEdge(start, player, -1 * player.getAvgPoints());

                // will add done as a neighbor once we have hit the highest available number of points within the constraints.
                // done should be set with a min value of infinity so it is polled
                graph.addEdge(player, done, -1 * done.getAvgPoints());
            }
        }

        // set the rules for a valid team.
        Float maxTeamSalary = 30f;
        IDjikstraContraint<Player> pathConstraints = new IDjikstraContraint<Player>() {

            @Override
            public float getShortestPathToNeighbor(DjikstraContext<Player> top, DjikstraContext<Player> neighbor, Edge<Player> edge) {
                Float pointInPathPlusNeighbor = 0f;
                for (Player playerInPath : top.getPathToVertex()) {
                    pointInPathPlusNeighbor -= playerInPath.getAvgPoints();
                }
                pointInPathPlusNeighbor += edge.getCost();
                return pointInPathPlusNeighbor;
            }

            @Override
            public boolean meetsConstraint(DjikstraContext<Player> top, DjikstraContext<Player> neighbor, Edge<Player> edge) {
                if (neighbor.isVisited()) {
                    return false;
                }
                Float costOfPathPlusNeighbor = 0f;
                for (Player playerInPath : top.getPathToVertex()) {
                    costOfPathPlusNeighbor += playerInPath.getCost();
                }
                costOfPathPlusNeighbor += neighbor.getValue().getCost();
                return costOfPathPlusNeighbor <= maxTeamSalary && getShortestPathToNeighbor(top, neighbor, edge) < neighbor.getShortestPath();
            }
        };

        System.out.println(graph.getVerticies());
        return new Object[]

                // unweighted directed graphs
                {
                        "Forced to make smart decision about taking two smaller values rather than one large one",
                        graph,
                        start,
                        done,
                        pathConstraints,
                        -120f
                }
                ;

    }



    @Test(dataProvider = "maxValueWithCostLimitProvider", invocationCount = 10)
    public void maxValueWithCostLimitTest(String msg, DirectedGraph<Player> graph, Player source, Player target, IDjikstraContraint<Player> pathConstraints, Float expectedShortestPath) {
        Djikstra<Player> djikstraAlg = new Djikstra<>(graph, pathConstraints);

        PathsFromSource<Player> paths = djikstraAlg.getPaths(source);
        Path<Player> actualShortestPath = null;
        for (Path<Player> path: paths.values()) {
            if (null == actualShortestPath) {
                actualShortestPath = path;
            } else if(path.getCost() < actualShortestPath.getCost()) {
                actualShortestPath = path;
            }
        }
        Float cost = actualShortestPath.getCost();// - Float.MIN_VALUE;
        Assert.assertEquals(cost, expectedShortestPath, "Test: " + msg + " failed. Found incorrect shortest path. Expected: "
                + expectedShortestPath + " but found: " + cost + ". User path\n " + actualShortestPath);
        System.out.println("Found path: " + actualShortestPath);
        System.out.println();
    }
}