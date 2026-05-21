package factory;

import model.CircleAdapter;
import model.DrawableShape;
import model.GraphEdgeShape;
import model.GraphNodeShape;
import model.LineAdapter;
import model.RectangleAdapter;

// Factory used to create shapes.
// The controller never creates shapes directly with new.
public class ShapeFactory {

    public static DrawableShape createShape(String type, double x1, double y1, double x2, double y2) {
        if ("RECTANGLE".equals(type)) {
            return new RectangleAdapter(x1, y1, x2, y2);
        }
        if ("CIRCLE".equals(type)) {
            return new CircleAdapter(x1, y1, x2, y2);
        }
        if ("LINE".equals(type)) {
            return new LineAdapter(x1, y1, x2, y2);
        }
        throw new IllegalArgumentException("Unknown shape type: " + type);
    }

    public static GraphNodeShape createNode(String label, double x, double y) {
        return new GraphNodeShape(label, x, y);
    }

    public static GraphEdgeShape createEdge(GraphNodeShape source, GraphNodeShape target) {
        return new GraphEdgeShape(source, target);
    }
}
