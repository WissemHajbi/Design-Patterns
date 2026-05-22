package factory;

import model.GraphNodeShape;

// Immutable request object used by shape factories.
public class ShapeRequest {

    private final double x1;
    private final double y1;
    private final double x2;
    private final double y2;
    private final String label;
    private final GraphNodeShape source;
    private final GraphNodeShape target;

    private ShapeRequest(double x1, double y1, double x2, double y2, String label, GraphNodeShape source, GraphNodeShape target) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.label = label;
        this.source = source;
        this.target = target;
    }

    public static ShapeRequest geometry(double x1, double y1, double x2, double y2) {
        return new ShapeRequest(x1, y1, x2, y2, null, null, null);
    }

    public static ShapeRequest node(String label, double x, double y) {
        return new ShapeRequest(x, y, x, y, label, null, null);
    }

    public static ShapeRequest edge(GraphNodeShape source, GraphNodeShape target) {
        return new ShapeRequest(0, 0, 0, 0, null, source, target);
    }

    public double getX1() {
        return x1;
    }

    public double getY1() {
        return y1;
    }

    public double getX2() {
        return x2;
    }

    public double getY2() {
        return y2;
    }

    public String getLabel() {
        return label;
    }

    public GraphNodeShape getSource() {
        return source;
    }

    public GraphNodeShape getTarget() {
        return target;
    }
}
