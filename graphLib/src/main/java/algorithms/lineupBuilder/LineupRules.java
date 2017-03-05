package algorithms.lineupBuilder;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by sfox on 2/28/17.
 */
public class LineupRules {

    private Float maxSalary;
    private Map<Position, Integer> playersPerPosition;
    private int totalAllowedPlayers = 0;

    public LineupRules() {
        playersPerPosition = new HashMap<>();
    }

    public boolean completesLineup(List<Player> existingLineup, Player playerToAdd) {

        // reserved for when we see start/end players which wont have a position
        if (null == playerToAdd.getPosition()) {
            return false;
        }
//        if (Position.END.equals(playerToAdd.getPosition())) {
//            return true;
//        } else if (Position.START.equals(playerToAdd.getPosition())) {
//            return false;
//        }


        Map<Position, Integer> potentialLineup = new HashMap<>();

        boolean validLineup = true;
        for (Player player: existingLineup) {
            validLineup &= addToLineup(potentialLineup, player.getPosition());
            if (validLineup) {

                if (!potentialLineup.containsKey(player.getPosition())) {
                    potentialLineup.put(player.getPosition(), 0);
                }
                potentialLineup.put(player.getPosition(), potentialLineup.get(player.getPosition()) + 1);
            }
        }

        validLineup &= addToLineup(potentialLineup, playerToAdd.getPosition());

        // add 1 to account for start node
        return validLineup && (existingLineup.size() - 1 == totalAllowedPlayers);
    }



    public boolean validateLineup(List<Player> lineup) {
        Map<Position, Integer> potentialLineup = new HashMap<>();
        boolean validLineup = true;

        for (Player player: lineup) {
            validLineup &= addToLineup(potentialLineup, player.getPosition());
            if (validLineup) {

                if (!potentialLineup.containsKey(player.getPosition())) {
                    potentialLineup.put(player.getPosition(), 0);
                }
                potentialLineup.put(player.getPosition(), potentialLineup.get(player.getPosition()) + 1);
            }
        }
        return validLineup && (lineup.size() - 1 == totalAllowedPlayers);
    }

    /**
     * try to put the player in its corresponding bucket. if this fails check to see if it can be put in
     * a flex bucket.
     * @param lineup
     * @param position
     * @return
     */
    public boolean addToLineup(Map<Position, Integer> lineup, Position position) {
        boolean canAdd = false;

        if (null == position) {
            return true;
        }
        int numAtPosition = lineup.containsKey(position) ? lineup.get(position) : 0;
        int numAllowedAtPosition = playersPerPosition.containsKey(position) ? playersPerPosition.get(position) : 0;

        int numAtFlex = lineup.containsKey(Position.FLEX) ? lineup.get(Position.FLEX) : 0;
        int numAllowedAtFlex = playersPerPosition.containsKey(Position.FLEX) ? playersPerPosition.get(Position.FLEX) : 0;

        if (numAllowedAtPosition > 0 && numAtPosition < numAllowedAtPosition) {
            canAdd = true;
        } else if (numAllowedAtFlex > 0 && numAtFlex < numAllowedAtFlex) {
            canAdd = true;
        }

        return canAdd;
    }


    /*
    BUILDER
     */
    private LineupRules(Builder builder) {
        setMaxSalary(builder.maxSalary);
        setPlayersPerPosition(builder.playersPerPosition);
        for (Integer numPlayers: playersPerPosition.values()) {
            totalAllowedPlayers += numPlayers;
        }
    }

    public Float getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(Float maxSalary) {
        this.maxSalary = maxSalary;
    }

    public Map<Position, Integer> getPlayersPerPosition() {
        return playersPerPosition;
    }

    public void setPlayersPerPosition(Map<Position, Integer> playersPerPosition) {
        this.playersPerPosition = playersPerPosition;
    }

    public static final class Builder {
        private Float maxSalary;
        private Map<Position, Integer> playersPerPosition;

        public Builder() {
            playersPerPosition = new HashMap<>();
        }

        public Builder withMaxSalary(Float val) {
            maxSalary = val;
            return this;
        }

        public Builder withPlayersPerPosition(Position position, Integer num) {
            playersPerPosition.put(position, num);
            return this;
        }

        public LineupRules build() {
            return new LineupRules(this);
        }
    }
}
