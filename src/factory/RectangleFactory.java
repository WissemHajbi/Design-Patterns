package factory;

import model.DrawableShape;
import model.RectangleAdapter;

public class RectangleFactory implements ShapeCreator {

    @Override
    public String getType() {
        return "RECTANGLE";
    }

    @Override
    public DrawableShape create(ShapeRequest request) {
        return new RectangleAdapter(request.getX1(), request.getY1(), request.getX2(), request.getY2());
    }
}
