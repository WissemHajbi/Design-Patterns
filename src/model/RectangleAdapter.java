package model;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class RectangleAdapter implements DrawableShape {

    private Rectangle rectangle;
    private double x1;
    private double y1;
    private double x2;
    private double y2;

    public RectangleAdapter(double x1, double y1, double x2, double y2) {
        this.x1 = Math.min(x1, x2);
        this.y1 = Math.min(y1, y2);
        this.x2 = Math.max(x1, x2);
        this.y2 = Math.max(y1, y2);

        rectangle = new Rectangle(this.x1, this.y1, this.x2 - this.x1, this.y2 - this.y1);
        setNormalStyle();
    }

    @Override
    public Node getNode() {
        return rectangle;
    }

    @Override
    public String getType() {
        return "RECTANGLE";
    }

    @Override
    public String serialize() {
        return getType() + ";" + x1 + ";" + y1 + ";" + x2 + ";" + y2;
    }

    @Override
    public double getX1() {
        return x1;
    }

    @Override
    public double getY1() {
        return y1;
    }

    @Override
    public double getX2() {
        return x2;
    }

    @Override
    public double getY2() {
        return y2;
    }

    @Override
    public String getExtraData() {
        return "";
    }

    @Override
    public void setNormalStyle() {
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(1);
    }

    @Override
    public void setSelectedStyle() {
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(Color.RED);
        rectangle.setStrokeWidth(2);
    }
}
