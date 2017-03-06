package topcoderProblems.utilities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sfox on 3/5/17.
 */
public class Trie<T> {

    private Map<T, Trie<T>> children;

    private int numMatching = 0;

    public Trie() {
        children = new HashMap<>();
    }


    /**
     * recursively add nodes to trie of sub tries
     * @param nodes
     */
    public void add(List<T> nodes) {
        if (null == nodes || nodes.isEmpty()) {
            return;
        }

        T head = nodes.get(0);
        if (!children.containsKey(head)) {
            children.put(head, new Trie());
        }

        // bump up the number of matching lists of nodes with the current prefix ending with head.
        children.get(head).setNumMatching(children.get(head).getNumMatching() + 1);

        // insert sublist
        children.get(head).add(nodes.subList(1, nodes.size()));
    }

    public int countPrefixes(List<T> prefix) {
        if (null == prefix || prefix.isEmpty()) {
            return numMatching;
        }

        T head = prefix.get(0);
        if (!children.containsKey(head)) {
            return 0;
        }

        List<T> subList = prefix.subList(1, prefix.size());
        return children.get(head).countPrefixes(subList);
    }


    public boolean matches(List<T> nodes) {
        if (null == nodes || nodes.isEmpty()) {
            return false;
        }

        return matches(nodes, false);
    }

    private boolean matches(List<T> nodes, boolean first) {
        if (null == nodes || nodes.isEmpty()) {
            return true;
        }

        T head = nodes.get(0);
        List<T> subList = nodes.subList(1, nodes.size());

        return children.containsKey(head) && matches(subList, first);
    }

    public int getNumMatching() {
        return numMatching;
    }

    public void setNumMatching(int numMatching) {
        this.numMatching = numMatching;
    }
}
