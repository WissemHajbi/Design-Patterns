package model;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;


public class LineAdapter implements DrawableShape {

    private Line line;
    private double x1;
    private double y1;
    private double x2;
    private double y2;

    public LineAdapter(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;

        line = new Line(x1, y1, x2, y2);
        setNormalStyle();
    }

    @Override
    public Node getNode() {
        return line;
    }

    @Override
    public String getType() {
        return "LINE";
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
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(1);
    }

    @Override
    public void setSelectedStyle() {
        line.setStroke(Color.RED);
        line.setStrokeWidth(2);
    }
}
