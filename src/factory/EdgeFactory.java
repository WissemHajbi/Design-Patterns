package factory;

import model.DrawableShape;
import model.GraphEdgeShape;

public class EdgeFactory implements ShapeCreator {

    @Override
    public String getType() {
        return "EDGE";
    }

    @Override
    public DrawableShape create(ShapeRequest request) {
        return new GraphEdgeShape(request.getSource(), request.getTarget());
    }
}
