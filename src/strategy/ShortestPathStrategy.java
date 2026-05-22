package strategy;

import java.util.List;

import model.GraphEdgeShape;
import model.GraphNodeShape;

public interface ShortestPathStrategy {
    List<GraphNodeShape> findPath(GraphNodeShape startNode, GraphNodeShape endNode, List<GraphNodeShape> nodes, List<GraphEdgeShape> edges);
}
