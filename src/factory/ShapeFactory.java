package factory;

import java.util.HashMap;
import java.util.Map;

import model.DrawableShape;
import model.GraphEdgeShape;
import model.GraphNodeShape;

// Central registry for shape factories.
// The creation logic is delegated to concrete factories.
public class ShapeFactory {

    private static final Map<String, ShapeCreator> CREATORS = new HashMap<>();

    static {
        register(new RectangleFactory());
        register(new CircleFactory());
        register(new LineFactory());
        register(new NodeFactory());
        register(new EdgeFactory());
    }

    public static void register(ShapeCreator creator) {
        CREATORS.put(creator.getType(), creator);
    }

    public static DrawableShape createShape(String type, double x1, double y1, double x2, double y2) {
        return getCreator(type).create(ShapeRequest.geometry(x1, y1, x2, y2));
    }

    public static GraphNodeShape createNode(String label, double x, double y) {
        return (GraphNodeShape) getCreator("NODE").create(ShapeRequest.node(label, x, y));
    }

    public static GraphEdgeShape createEdge(GraphNodeShape source, GraphNodeShape target) {
        return (GraphEdgeShape) getCreator("EDGE").create(ShapeRequest.edge(source, target));
    }

    private static ShapeCreator getCreator(String type) {
        ShapeCreator creator = CREATORS.get(type);
        if (creator == null) {
            throw new IllegalArgumentException("Unknown shape type: " + type);
        }
        return creator;
    }
}
