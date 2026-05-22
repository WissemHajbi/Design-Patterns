package factory;

import model.DrawableShape;
import model.GraphNodeShape;

public class NodeFactory implements ShapeCreator {

    @Override
    public String getType() {
        return "NODE";
    }

    @Override
    public DrawableShape create(ShapeRequest request) {
        return new GraphNodeShape(request.getLabel(), request.getX1(), request.getY1());
    }
}
