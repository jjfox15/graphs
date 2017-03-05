package algorithms.lineupBuilder;

import java.util.Objects;
import java.util.UUID;

/**
 * Created by sfox on 2/26/17.
 */
public class Player {

    // keep uuid so players with same cost and average points are not equal
    private UUID uuid;
    private String name;
    private Position position;
    private Float cost;
    private Float avgPoints;

    public Player(String name, Position position, Float cost, Float avgPoints) {
        this.uuid = UUID.randomUUID();
        this.cost = cost;
        this.position = position;
        this.name = name;
        this.avgPoints = avgPoints;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Float getAvgPoints() {
        return avgPoints;
    }

    public void setAvgPoints(Float avgPoints) {
        this.avgPoints = avgPoints;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, name, cost, avgPoints);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        final Player other = (Player) obj;

        // We don't check table alias as its transient and isn't persisted
        return Objects.equals(this.uuid, other.uuid)
                && Objects.equals(this.name, other.name)
                && Objects.equals(this.cost, other.cost)
                && Objects.equals(this.avgPoints, other.avgPoints);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("name: ").append(name);//.append("\n");
        sb.append(", position: ").append(position);//.append("\n");
        sb.append(", cost: ").append(cost);//.append("\n");
        sb.append(", avgPoints: ").append(avgPoints);//.append("\n");
        sb.append("}\n");
        return sb.toString();
    }
}
