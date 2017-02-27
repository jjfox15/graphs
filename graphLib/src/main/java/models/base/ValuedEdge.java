package models.base;

/**
 * Created by sfox on 2/26/17.
 */
public class ValuedEdge<V> extends Edge<V> {

    private float value;

    public ValuedEdge(V start, V end, float cost, float value) {
        super(start, end, cost);
        this.value = value;
    }
}
