package factory;

import model.DrawableShape;

// Factory interface for creating one kind of shape.
public interface ShapeCreator {
    String getType();
    DrawableShape create(ShapeRequest request);
}
