package problems.maxValueCostLimitProblem.rosterTestCases;

import algorithms.djikstra.DjikstraContext;
import algorithms.djikstra.IDjikstraContraint;
import models.base.Edge;
import models.graphRepresentations.DirectedGraph;
import problems.maxValueCostLimitProblem.Player;

/**
 * Created by sfox on 2/28/17.
 */
public class ForcedSmartTestCase {

    public static Object[] get() {

        DirectedGraph<Player> graph = new DirectedGraph<>();

        Player player1 = new Player("player 1", 20f, 99f);
        Player player2 = new Player("player 2", 15f, 60f);
        Player player3 = new Player("player 3", 15f, 60f);
        Player player4 = new Player("player 4", 5f, 1f);

        graph.addVertex(player1);
        graph.addVertex(player2);
        graph.addVertex(player3);
        graph.addVertex(player4);

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
                        "Forced to make smart decision about taking two smaller values rather than one large one",
                        graph,
                        start,
                        done,
                        pathConstraints,
                        -120f
                }
                ;

    }



}
