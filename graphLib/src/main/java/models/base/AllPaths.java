package models.base;

import java.util.HashMap;

/**
 * Created by sfox on 2/26/17.
 */
public class AllPaths<V> extends HashMap<V, PathsFromSource<V>> {

    public AllPaths() {

    }

    public PathsFromSource<V> pathsFromSource(V source) {
        return get(source);
    }

    public void addSource(V source, PathsFromSource<V> paths) {
        put(source, paths);
    }
}
