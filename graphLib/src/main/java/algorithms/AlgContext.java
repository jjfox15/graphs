package algorithms;

import java.util.List;

/**
 * Created by sfox on 2/26/17.
 */
public class AlgContext<V> {

    private V value;

    public AlgContext(V value) {
        this.value = value;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

}
