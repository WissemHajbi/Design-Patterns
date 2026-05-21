package model;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

// Simple graph node: a circle with a label.
public class GraphNodeShape implements DrawableShape {

    private String label;
    private double centerX;
    private double centerY;
    private Circle circle;
    private Label text;
    private StackPane pane;

    public GraphNodeShape(String label, double centerX, double centerY) {
        this.label = label;
        this.centerX = centerX;
        this.centerY = centerY;

        circle = new Circle(22);
        text = new Label(label);

        pane = new StackPane();
        pane.setLayoutX(centerX - 22);
        pane.setLayoutY(centerY - 22);
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().addAll(circle, text);

        setNormalStyle();
    }

    public String getLabel() {
        return label;
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    @Override
    public Node getNode() {
        return pane;
    }

    @Override
    public String getType() {
        return "NODE";
    }

    @Override
    public String serialize() {
        return getType() + ";" + label + ";" + centerX + ";" + centerY;
    }

    @Override
    public double getX1() {
        return centerX;
    }

    @Override
    public double getY1() {
        return centerY;
    }

    @Override
    public double getX2() {
        return centerX;
    }

    @Override
    public double getY2() {
        return centerY;
    }

    @Override
    public String getExtraData() {
        return label;
    }

    @Override
    public void setNormalStyle() {
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(1);
        text.setTextFill(Color.BLACK);
    }

    @Override
    public void setSelectedStyle() {
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.RED);
        circle.setStrokeWidth(2);
        text.setTextFill(Color.RED);
    }
}
