package strategy;

import java.util.List;

import model.GraphEdgeShape;
import model.GraphNodeShape;

// Context for shortest path strategies.
public class ShortestPathContext {

    private ShortestPathStrategy strategy;

    public void setStrategy(ShortestPathStrategy strategy) {
        this.strategy = strategy;
    }

    public List<GraphNodeShape> findPath(GraphNodeShape startNode, GraphNodeShape endNode, List<GraphNodeShape> nodes, List<GraphEdgeShape> edges) {
        if (strategy == null) {
            throw new IllegalStateException("No shortest path strategy selected.");
        }
        return strategy.findPath(startNode, endNode, nodes, edges);
    }
}
