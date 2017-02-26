package models.base;

import java.util.HashMap;

/**
 * Map of
 * Created by sfox on 2/25/17.
 */
public class PathsFromSource<V> extends HashMap<V, Path<V>>{

    private V source;

    public PathsFromSource(V source) {
        this.source = source;
    }

    public void addPath(V target, Path<V> path) {
        put(target, path);
    }

    public Path<V> getPath(V target) {
        return get(target);
    }


}
