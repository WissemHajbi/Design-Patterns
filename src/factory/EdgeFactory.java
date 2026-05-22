package factory;

import model.GraphEdgeShape;
import model.GraphNodeShape;

public class EdgeFactory {

    public static GraphEdgeShape create(GraphNodeShape source, GraphNodeShape target) {
        return new GraphEdgeShape(source, target);
    }
}
