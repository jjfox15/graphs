package models.base;

/**
 * Created by sfox on 2/25/17.
 */
@FunctionalInterface
public interface CostEvaluator<V> {
    float evaluate(V start, V end);
}
