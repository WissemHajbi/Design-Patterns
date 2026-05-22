package model;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class CircleAdapter implements DrawableShape {

    private Circle circle;
    private double x1;
    private double y1;
    private double x2;
    private double y2;

    public CircleAdapter(double x1, double y1, double x2, double y2) {
        this.x1 = Math.min(x1, x2);
        this.y1 = Math.min(y1, y2);
        this.x2 = Math.max(x1, x2);
        this.y2 = Math.max(y1, y2);

        double width = this.x2 - this.x1;
        double height = this.y2 - this.y1;
        double radius = Math.max(width, height) / 2.0;
        double centerX = this.x1 + width / 2.0;
        double centerY = this.y1 + height / 2.0;

        circle = new Circle(centerX, centerY, radius);
        setNormalStyle();
    }

    @Override
    public Node getNode() {
        return circle;
    }

    @Override
    public String getType() {
        return "CIRCLE";
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
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(1);
    }

    @Override
    public void setSelectedStyle() {
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.RED);
        circle.setStrokeWidth(2);
    }
}
