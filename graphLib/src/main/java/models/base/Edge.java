package models.base;

/**
 * Created by sfox on 2/25/17.
 */
public class Edge<V> {

    private V start;
    private V end;
    private float cost;

    public Edge(V start, V end, float cost) {
        this.start = start;
        this.end = end;
        this.cost = cost;
    }

    public V getStart() {
        return start;
    }

    public void setStart(V start) {
        this.start = start;
    }

    public V getEnd() {
        return end;
    }

    public void setEnd(V end) {
        this.end = end;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }


}
