package problems;

import algorithms.lineupBuilder.LineupRules;
import algorithms.lineupBuilder.Player;
import algorithms.lineupBuilder.Position;
import algorithms.lineupBuilder.LineupBuilder;
import models.base.Path;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Created by sfox on 2/28/17.
 */
@Test(groups = {"unit"})
public class LineupBuilderTest {


    private List<Player> simplePlayersList() {
        Player player1 = new Player("player 1", Position.QB, 10f, 22.5f);
        Player player2 = new Player("player 2", Position.RB, 10f, 2.5f);
        Player player3 = new Player("player 3", Position.WR, 10f, 12.5f);
        Player player4 = new Player("player 4", Position.TE, 10f, 30f);
        Player player5 = new Player("player 5", Position.DEF, 10f, 20f);

        return Arrays.asList(player1, player2, player3, player4, player5);

    }

    @DataProvider(name = "lineupBuilderProvider")
    public Object[][] lineupBuilderProvider() {

        LineupRules oneOfEach = new LineupRules.Builder()
                .withMaxSalary(50f)
                .withPlayersPerPosition(Position.QB, 1)
                .withPlayersPerPosition(Position.RB, 1)
                .withPlayersPerPosition(Position.WR, 1)
                .withPlayersPerPosition(Position.TE, 1)
                .withPlayersPerPosition(Position.DEF, 1)
                .build();

        Player player1_1 = new Player("player 1_1", Position.QB, 10f, 12f);
        Player player2_1 = new Player("player 2_1", Position.RB, 10f, 2.5f);
        Player player3_1 = new Player("player 3_1", Position.WR, 10f, 12.5f);
        Player player4_1 = new Player("player 4_1", Position.TE, 10f, 10f);
        Player player5_1 = new Player("player 5_1", Position.DEF, 10f, 15f);

        List<Player> oneOfEachList = Arrays.asList(player1_1, player2_1, player3_1, player4_1, player5_1);

        Player player1_2 = new Player("player 1_2", Position.QB, 10f, 10f);
        Player player2_2 = new Player("player 2_2", Position.RB, 10f, 10f);
        Player player3_2 = new Player("player 3_2", Position.WR, 10f, 10f);
        Player player4_2 = new Player("player 4_2", Position.TE, 10f, 10f);
        Player player5_2 = new Player("player 5_2", Position.DEF, 10f, 10f);
        List<Player> multiplesOfAllNotSameValues = new ArrayList<>(Arrays.asList(player1_2, player2_2, player3_2, player4_2, player5_2));
        multiplesOfAllNotSameValues.addAll(oneOfEachList);


        LineupRules multipleRbsRule = new LineupRules.Builder()
                .withMaxSalary(50f)
                .withPlayersPerPosition(Position.QB, 1)
                .withPlayersPerPosition(Position.RB, 2)
                .withPlayersPerPosition(Position.WR, 1)
                .withPlayersPerPosition(Position.TE, 0)
                .withPlayersPerPosition(Position.DEF, 1)
                .build();


        List<Player> hugePlayerList = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < 2000; i++) {
            Player player = new Player("player " + i, Position.values()[rand.nextInt(Position.values().length)], Float.valueOf(25 - rand.nextInt(24)), Float.valueOf(50 - rand.nextInt(25)));
            if (player.getPosition().equals(Position.QB)) {
                player.setAvgPoints(10f);
            }
            hugePlayerList.add(player);
        }


        List<Player> purposefullyTryToBreakAlgList = new ArrayList<>();
        purposefullyTryToBreakAlgList.add(new Player("QB 1", Position.QB, 40f, 50f));
        purposefullyTryToBreakAlgList.add(new Player("RB 1", Position.RB, 2f, 1f));
        purposefullyTryToBreakAlgList.add(new Player("WR 1", Position.WR, 2f, 1f));
        purposefullyTryToBreakAlgList.add(new Player("TE 1", Position.TE, 2f, 1f));
        purposefullyTryToBreakAlgList.add(new Player("DEF 1", Position.DEF, 2f, 1f));

        purposefullyTryToBreakAlgList.add(new Player("QB 2", Position.QB, 10f, 20f));
        purposefullyTryToBreakAlgList.add(new Player("RB 2", Position.RB, 10f, 20f));
        purposefullyTryToBreakAlgList.add(new Player("WR 2", Position.WR, 10f, 20f));
        purposefullyTryToBreakAlgList.add(new Player("TE 2", Position.TE, 10f, 20f));
        purposefullyTryToBreakAlgList.add(new Player("DEF 2", Position.DEF, 10f, 20f));

        LineupRules purposefullyTryToBreakAlgRules = new LineupRules.Builder()
                .withMaxSalary(50f)
                .withPlayersPerPosition(Position.QB, 1)
                .withPlayersPerPosition(Position.RB, 1)
                .withPlayersPerPosition(Position.WR, 1)
                .withPlayersPerPosition(Position.TE, 1)
                .withPlayersPerPosition(Position.DEF, 1)
                .build();

        return new Object[][] {
                {
                        "simple test case with open lineup rules",
                        oneOfEachList,
                        oneOfEach,
                        52f
                },
                {
                        "Multiple players available at each.",
                        multiplesOfAllNotSameValues,
                        oneOfEach,
                        59.5f
                },
                {
                        "Multiple players at position rules.",
                        multiplesOfAllNotSameValues,
                        multipleRbsRule,
                        52f
                },
//                {
//                        "Huge list multiple rbs rule.",
//                        hugePlayerList,
//                        multipleRbsRule,
//                        52f
//                }
//                {
//                        "Purposefully try to break algorithm..",
//                        purposefullyTryToBreakAlgList,
//                        purposefullyTryToBreakAlgRules,
//                        100f
//                },


        };
    }


    @Test(dataProvider = "lineupBuilderProvider", invocationCount = 1)
    public void lineupBuilderTest(String msg, Collection<Player> players, LineupRules lineupRules, Float expectedShortestPath) {

        LineupBuilder lineupBuilder = new LineupBuilder(lineupRules);
        for (Player player: players) {
            lineupBuilder.addPlayer(player);
        }
        lineupBuilder.init();

        Path<Player> actualShortestPath = lineupBuilder.buildLineup();

        Float cost = actualShortestPath.getCost();
        if (null == actualShortestPath.getPath()) {
            Assert.assertNull(expectedShortestPath, "found unexpected null path");
        } else {
            Assert.assertEquals(cost, expectedShortestPath, "Test: " + msg + " failed. Found incorrect shortest path. Expected: "
                    + expectedShortestPath + " but found: " + cost + ". User path\n " + actualShortestPath);
        }
        System.out.println("Found path: " + actualShortestPath);
        System.out.println();
    }
}
