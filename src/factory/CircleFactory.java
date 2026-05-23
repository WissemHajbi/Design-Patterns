package factory;

import model.CircleAdapter;
import model.DrawableShape;

public class CircleFactory implements ShapeCreator {

    @Override
    public String getType() {
        return "CIRCLE";
    }

    @Override
    public DrawableShape create(ShapeRequest request) {
        return new CircleAdapter(request.getX1(), request.getY1(), request.getX2(), request.getY2());
    }
}