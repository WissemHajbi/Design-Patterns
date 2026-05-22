package factory;

import model.DrawableShape;
import model.GraphEdgeShape;
import model.GraphNodeShape;

// Legacy facade kept for compatibility.
// New code should use the small dedicated factories.
public class ShapeFactory {

    public static DrawableShape createShape(String type, double x1, double y1, double x2, double y2) {
        if ("RECTANGLE".equals(type)) {
            return RectangleFactory.create(x1, y1, x2, y2);
        }
        if ("CIRCLE".equals(type)) {
            return CircleFactory.create(x1, y1, x2, y2);
        }
        if ("LINE".equals(type)) {
            return LineFactory.create(x1, y1, x2, y2);
        }
        throw new IllegalArgumentException("Unknown shape type: " + type);
    }

    public static GraphNodeShape createNode(String label, double x, double y) {
        return NodeFactory.create(label, x, y);
    }

    public static GraphEdgeShape createEdge(GraphNodeShape source, GraphNodeShape target) {
        return EdgeFactory.create(source, target);
    }
}
