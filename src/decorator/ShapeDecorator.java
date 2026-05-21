package decorator;

import javafx.scene.Node;
import model.DrawableShape;

// Base decorator.
public abstract class ShapeDecorator implements DrawableShape {

    protected DrawableShape shape;

    public ShapeDecorator(DrawableShape shape) {
        this.shape = shape;
    }

    @Override
    public Node getNode() {
        return shape.getNode();
    }

    @Override
    public String getType() {
        return shape.getType();
    }

    @Override
    public String serialize() {
        return shape.serialize();
    }

    @Override
    public double getX1() {
        return shape.getX1();
    }

    @Override
    public double getY1() {
        return shape.getY1();
    }

    @Override
    public double getX2() {
        return shape.getX2();
    }

    @Override
    public double getY2() {
        return shape.getY2();
    }

    @Override
    public String getExtraData() {
        return shape.getExtraData();
    }

    @Override
    public void setNormalStyle() {
        shape.setNormalStyle();
    }

    @Override
    public void setSelectedStyle() {
        shape.setSelectedStyle();
    }
}
