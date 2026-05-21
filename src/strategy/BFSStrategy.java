package strategy;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import model.GraphEdgeShape;
import model.GraphNodeShape;

// Shortest path with BFS.
public class BFSStrategy implements ShortestPathStrategy {

    @Override
    public List<GraphNodeShape> findPath(GraphNodeShape startNode, GraphNodeShape endNode, List<GraphNodeShape> nodes, List<GraphEdgeShape> edges) {
        Map<String, GraphNodeShape> nodeMap = new LinkedHashMap<>();
        for (GraphNodeShape node : nodes) {
            nodeMap.put(node.getLabel(), node);
        }

        Map<String, List<String>> graph = buildGraph(edges);
        Queue<String> queue = new ArrayDeque<>();
        Map<String, String> parent = new HashMap<>();

        queue.add(startNode.getLabel());
        parent.put(startNode.getLabel(), null);

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
                if (!parent.containsKey(next)) {
                    parent.put(next, current);
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
        if (!parent.containsKey(end)) {
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
