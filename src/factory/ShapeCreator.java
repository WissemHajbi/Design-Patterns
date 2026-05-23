package factory;

import model.DrawableShape;

public interface ShapeCreator {
    String getType();
    DrawableShape create(ShapeRequest request);
}