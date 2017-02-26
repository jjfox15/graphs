package models.translations;

import models.graphRepresentations.GraphRepresentation;
import models.graphRepresentations.DirectedGraph;

/**
 * Translator between a graph representation of type T to and from a base Graph containing nodes with type V
 * Created by sfox on 2/25/17.
 */
public interface GraphTranslator<T extends GraphRepresentation<V>, V> {

    DirectedGraph<V> translateToBase(T graph);

    T translateFromBase(DirectedGraph<V> graph);
}
