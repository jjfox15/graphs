package freshStartLineupBuilding;

import algorithms.djikstra.Djikstra;
import algorithms.djikstra.DjikstraContext;
import algorithms.djikstra.DjikstraContextGraph;
import algorithms.djikstra.IDjikstraContraint;
import algorithms.lineupBuilder.LineupConstraints;
import algorithms.lineupBuilder.LineupRules;
import algorithms.lineupBuilder.Player;
import algorithms.lineupBuilder.Position;
import models.base.Edge;
import models.base.Node;
import models.base.Path;
import models.graphRepresentations.DirectedGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Created by sfox on 2/28/17.
 */
public class LineupBuilder2 {

    private DirectedGraph<Player> players;
    private PriorityQueue<DjikstraContext<Player>> priorityQueue;
    private DjikstraContextGraph<Player> contextGraph;
    private static Player START = new Player("start", null, 0f, 0f);
    private static Player DONE = new Player("done", null, 0f, 0f);
    private boolean initializedGraph = false;
    private LineupRules rules;

    public LineupBuilder2(LineupRules rules) {

        Comparator<DjikstraContext<Player>> comparator = new Comparator<DjikstraContext<Player>>() {
            @Override
            public int compare(DjikstraContext<Player> o1, DjikstraContext<Player> o2) {
                return o1.getShortestPath().compareTo(o2.getShortestPath());
            }
        };
        this.priorityQueue = new PriorityQueue<>(comparator);
        this.rules = rules;
        players = new DirectedGraph<>();
        players.addVertex(START);
        players.addVertex(DONE);
    }

    public void addPlayer(Player player) {
        players.addVertex(player);
        initializedGraph = false;
    }

    public void init() {
        // each player can be chosen after any other (for now) so set up all edges. The cost will be the negative of the
        // value added by the second player. (trick for maximizing value)
        for (Player player : players.getVerticies()) {
            for (Player secondPlayer : players.getVerticies()) {
                if (!player.equals(secondPlayer)) {
                    players.addEdge(player, secondPlayer, -1 * secondPlayer.getAvgPoints());
                }
            }
        }

        for (Player player : players.getVerticies()) {
            if (!player.equals(START) && !player.equals(DONE)) {
                players.addEdge(START, player, -1 * player.getAvgPoints());
            }
        }

        initializedGraph = true;
    }

    public Path<Player> buildLineup() {
        if (!initializedGraph) {
            throw new RuntimeException("Tried to build lineup against uninitialized graph. Make sure init is run.");
        }
        contextGraph = new DjikstraContextGraph<>(players);
        DjikstraContext sourceContext = contextGraph.getContext(START);
        sourceContext.setShortestPath(0f);

        sourceContext.setPathToVertex(new ArrayList<>(Arrays.asList(START)));
        contextGraph.setContext(START, sourceContext);
        priorityQueue.add(sourceContext);

        while (!priorityQueue.isEmpty()) {
            DjikstraContext<Player> top = priorityQueue.poll();
            Player value = top.getValue();

            // check to see if top represents a path to complete and valid team. if so break
            if (rules.validateLineup(top.getPathToVertex())) {
                // insert done context with updated path and values then break
                Edge<Player> doneEdge = new Edge<>(top.getValue(), DONE, 0f);
                DjikstraContext<Player> doneContext = new DjikstraContext<Player>(DONE);
                updateContext(top, doneContext, doneEdge);

                break;
            }

            contextGraph.getContext(value).setVisited(true);
            Node graphNode = players.getVertex(value);
            Set<Edge<Player>> edges = graphNode.getPathsToNeighbors();

            for (Edge<Player> edge: edges) {
                DjikstraContext<Player> neighborContext = contextGraph.getContext(edge.getEnd());
                // assume every prior node in path is valid and fits on team, for each neighbor verify that it maintains
                // team validity, fits under the salary limit, and is not already on the path
                Map<Position, Integer> potentialLineup = new HashMap<>();
                boolean addToQueue = meetsConstraint(top, neighborContext, edge);
                if (addToQueue) {
                    for (Player player : top.getPathToVertex()) {
                        addToQueue &= rules.addToLineup(potentialLineup, player.getPosition());
                        if (addToQueue) {

                            if (!potentialLineup.containsKey(player.getPosition())) {
                                potentialLineup.put(player.getPosition(), 0);
                            }
                            potentialLineup.put(player.getPosition(), potentialLineup.get(player.getPosition()) + 1);
                        }
                    }
                    addToQueue &= rules.addToLineup(potentialLineup, neighborContext.getValue().getPosition());
                }

                if (addToQueue) {
                    updateContext(top, neighborContext, edge);

                    // copy object to avoid mutating objects already in the queue
                    priorityQueue.add(new DjikstraContext<>(neighborContext));
                }
            }
        }
        Path<Player> path = getPath(DONE);
        path.setCost(path.getCost() * -1);
        return path;
    }


    public boolean meetsConstraint(DjikstraContext<Player> top, DjikstraContext<Player> neighbor, Edge<Player> edge) {
        if (neighbor.isVisited()) {
            // verify that top is not already in the path to top.
            for (Player playerInPath : top.getPathToVertex()) {
                if (playerInPath.equals(top.getValue())) {
                    return false;
                }
            }
        }
        Float costOfPathPlusNeighbor = 0f;
        for (Player playerInPath : top.getPathToVertex()) {
            costOfPathPlusNeighbor += playerInPath.getCost();
        }
        costOfPathPlusNeighbor += neighbor.getValue().getCost();

        boolean underSalary = costOfPathPlusNeighbor <= rules.getMaxSalary();
        boolean shorterPath = getShortestPathToNeighbor(top, neighbor, edge) < neighbor.getShortestPath();

        return underSalary && shorterPath;
    }

    public float getShortestPathToNeighbor(DjikstraContext<Player> top, DjikstraContext<Player> neighbor, Edge<Player> edge) {
        Float pointInPathPlusNeighbor = 0f;
        for (Player playerInPath : top.getPathToVertex()) {
            pointInPathPlusNeighbor -= playerInPath.getAvgPoints();
        }
        pointInPathPlusNeighbor += edge.getCost();
        return pointInPathPlusNeighbor;
    }

    public void updateContext(DjikstraContext<Player> top, DjikstraContext<Player> neighborContext, Edge<Player> edge) {
        List<Player> pathToNeighbor = new ArrayList(top.getPathToVertex());
        Player neighborValue = neighborContext.getValue();
        pathToNeighbor.add(neighborValue);
        neighborContext.setPathToVertex(pathToNeighbor);

        Float pathToNeighborCost = getShortestPathToNeighbor(top, neighborContext, edge);
        neighborContext.setShortestPath(pathToNeighborCost);

        contextGraph.setContext(neighborContext.getValue(), neighborContext);

    }

    private Path<Player> getPath(Player target) {
        DjikstraContext<Player> targetContext = contextGraph.getContext(target);
        if (null != targetContext) {
            return new Path<>(targetContext.getPathToVertex(), targetContext.getShortestPath());
        } else {
            return null;
        }
    }
}
