package algorithms.lineupBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sfox on 2/28/17.
 */
public enum Position {
    QB,
    RB,
    WR,
    TE,
    DEF,
    K,
    FLEX(RB, WR, TE),

    // special enums only used for generating lineups
    START,
    END;

    private Set<Position> positions = new HashSet<>();

    private Position(Position ... positions) {
        if (positions.length == 0) {
            this.positions.add(this);
        } else {
            for (Position position: positions) {
                this.positions.add(position);
            }
        }
    }

    public Set<Position> getPositions() {
        return positions;
    }
}
