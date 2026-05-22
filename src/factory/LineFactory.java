package factory;

import model.DrawableShape;
import model.LineAdapter;

public class LineFactory implements ShapeCreator {

    @Override
    public String getType() {
        return "LINE";
    }

    @Override
    public DrawableShape create(ShapeRequest request) {
        return new LineAdapter(request.getX1(), request.getY1(), request.getX2(), request.getY2());
    }
}
