package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import factory.ShapeFactory;
import model.DrawableShape;
import model.GraphEdgeShape;
import model.GraphNodeShape;
import singleton.DatabaseConnection;


public class ShapeDAO {

    public void saveShapes(int drawingId, List<DrawableShape> shapes) {
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            String sql = "INSERT INTO SHAPES(drawing_id, type, x1, y1, x2, y2, color, stroke, extra_data) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            for (DrawableShape shape : shapes) {
                statement.setInt(1, drawingId);
                statement.setString(2, shape.getType());
                statement.setDouble(3, shape.getX1());
                statement.setDouble(4, shape.getY1());
                statement.setDouble(5, shape.getX2());
                statement.setDouble(6, shape.getY2());
                statement.setString(7, "WHITE");
                statement.setString(8, "BLACK");
                statement.setString(9, shape.getExtraData());
                statement.executeUpdate();
            }

            statement.close();
        } catch (Exception e) {
            System.out.println("Cannot save shapes.");
        }
    }

    public List<DrawableShape> loadShapes(int drawingId) {
        List<DrawableShape> shapes = new ArrayList<>();
        Map<String, GraphNodeShape> nodeMap = new HashMap<>();

        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();

            String sqlNodes = "SELECT * FROM SHAPES WHERE drawing_id = ? AND type <> 'EDGE' ORDER BY id";
            PreparedStatement statementNodes = connection.prepareStatement(sqlNodes);
            statementNodes.setInt(1, drawingId);
            ResultSet rsNodes = statementNodes.executeQuery();

            while (rsNodes.next()) {
                String type = rsNodes.getString("type");
                double x1 = rsNodes.getDouble("x1");
                double y1 = rsNodes.getDouble("y1");
                double x2 = rsNodes.getDouble("x2");
                double y2 = rsNodes.getDouble("y2");
                String extra = rsNodes.getString("extra_data");

                if ("NODE".equals(type)) {
                    GraphNodeShape node = ShapeFactory.createNode(extra, x1, y1);
                    shapes.add(node);
                    nodeMap.put(node.getLabel(), node);
                } else if ("RECTANGLE".equals(type) || "CIRCLE".equals(type) || "LINE".equals(type)) {
                    shapes.add(ShapeFactory.createShape(type, x1, y1, x2, y2));
                }
            }

            rsNodes.close();
            statementNodes.close();

            String sqlEdges = "SELECT * FROM SHAPES WHERE drawing_id = ? AND type = 'EDGE' ORDER BY id";
            PreparedStatement statementEdges = connection.prepareStatement(sqlEdges);
            statementEdges.setInt(1, drawingId);
            ResultSet rsEdges = statementEdges.executeQuery();

            while (rsEdges.next()) {
                String extra = rsEdges.getString("extra_data");
                String[] parts = extra.split(",");
                if (parts.length >= 2) {
                    String sourceLabel = parts[0];
                    String targetLabel = parts[1];
                    GraphNodeShape source = nodeMap.get(sourceLabel);
                    GraphNodeShape target = nodeMap.get(targetLabel);
                    if (source != null && target != null) {
                        shapes.add(ShapeFactory.createEdge(source, target));
                    }
                }
            }

            rsEdges.close();
            statementEdges.close();
        } catch (Exception e) {
            System.out.println("Cannot load shapes.");
        }

        return shapes;
    }
}
