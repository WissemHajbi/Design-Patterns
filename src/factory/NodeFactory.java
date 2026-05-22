package factory;

import model.GraphNodeShape;

public class NodeFactory {

    public static GraphNodeShape create(String label, double x, double y) {
        return new GraphNodeShape(label, x, y);
    }
}
