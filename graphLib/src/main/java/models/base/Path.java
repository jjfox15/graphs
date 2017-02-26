package models.base;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sfox on 2/25/17.
 */
public class Path<V> extends ArrayList<V> {

    private List<V> path;
    private float cost;

    public Path(List<V> path, float cost) {
        this.path = path;
        this.cost = cost;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public List<V> getPath() {
        return path;
    }

    public void setPath(List<V> path) {
        this.path = path;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (V node: path) {
            if (!first) {
                sb.append(", ");
            }
            first = false;
            sb.append(node);
        }
        return sb.toString();
    }
}
