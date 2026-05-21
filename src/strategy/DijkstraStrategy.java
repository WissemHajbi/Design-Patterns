package strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import model.GraphEdgeShape;
import model.GraphNodeShape;

// Shortest path with a very simple Dijkstra implementation.
public class DijkstraStrategy implements ShortestPathStrategy {

    @Override
    public List<GraphNodeShape> findPath(GraphNodeShape startNode, GraphNodeShape endNode, List<GraphNodeShape> nodes, List<GraphEdgeShape> edges) {
        Map<String, GraphNodeShape> nodeMap = new LinkedHashMap<>();
        for (GraphNodeShape node : nodes) {
            nodeMap.put(node.getLabel(), node);
        }

        Map<String, List<String>> graph = buildGraph(edges);
        Map<String, Double> distance = new HashMap<>();
        Map<String, String> parent = new HashMap<>();
        PriorityQueue<String> queue = new PriorityQueue<>((a, b) -> Double.compare(distance.getOrDefault(a, Double.MAX_VALUE), distance.getOrDefault(b, Double.MAX_VALUE)));

        for (GraphNodeShape node : nodes) {
            distance.put(node.getLabel(), Double.MAX_VALUE);
        }

        distance.put(startNode.getLabel(), 0.0);
        queue.add(startNode.getLabel());

        while (!queue.isEmpty()) {
            String current = queue.remove();
            if (current.equals(endNode.getLabel())) {
                break;
            }

            List<String> neighbours = graph.get(current);
            if (neighbours == null) {
                continue;
            }

            for (String next : neighbours) {
                double newDistance = distance.get(current) + 1;
                if (newDistance < distance.getOrDefault(next, Double.MAX_VALUE)) {
                    distance.put(next, newDistance);
                    parent.put(next, current);
                    queue.remove(next);
                    queue.add(next);
                }
            }
        }

        return buildPath(startNode.getLabel(), endNode.getLabel(), parent, nodeMap);
    }

    private Map<String, List<String>> buildGraph(List<GraphEdgeShape> edges) {
        Map<String, List<String>> graph = new HashMap<>();
        for (GraphEdgeShape edge : edges) {
            String a = edge.getSourceNode().getLabel();
            String b = edge.getTargetNode().getLabel();
            graph.computeIfAbsent(a, k -> new ArrayList<>()).add(b);
            graph.computeIfAbsent(b, k -> new ArrayList<>()).add(a);
        }
        return graph;
    }

    private List<GraphNodeShape> buildPath(String start, String end, Map<String, String> parent, Map<String, GraphNodeShape> nodeMap) {
        List<GraphNodeShape> path = new ArrayList<>();
        if (!start.equals(end) && !parent.containsKey(end)) {
            return path;
        }

        String current = end;
        while (current != null) {
            path.add(0, nodeMap.get(current));
            current = parent.get(current);
        }
        return path;
    }
}
