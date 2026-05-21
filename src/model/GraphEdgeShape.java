package model;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

// Simple graph edge between two nodes.
public class GraphEdgeShape implements DrawableShape {

    private GraphNodeShape sourceNode;
    private GraphNodeShape targetNode;
    private Line line;
    private double weight;

    public GraphEdgeShape(GraphNodeShape sourceNode, GraphNodeShape targetNode) {
        this.sourceNode = sourceNode;
        this.targetNode = targetNode;
        this.weight = 1.0;

        line = new Line(sourceNode.getCenterX(), sourceNode.getCenterY(), targetNode.getCenterX(), targetNode.getCenterY());
        setNormalStyle();
    }

    public GraphNodeShape getSourceNode() {
        return sourceNode;
    }

    public GraphNodeShape getTargetNode() {
        return targetNode;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public Node getNode() {
        return line;
    }

    @Override
    public String getType() {
        return "EDGE";
    }

    @Override
    public String serialize() {
        return getType() + ";" + sourceNode.getLabel() + ";" + targetNode.getLabel() + ";" + weight + ";" + sourceNode.getCenterX() + ";" + sourceNode.getCenterY() + ";" + targetNode.getCenterX() + ";" + targetNode.getCenterY();
    }

    @Override
    public double getX1() {
        return sourceNode.getCenterX();
    }

    @Override
    public double getY1() {
        return sourceNode.getCenterY();
    }

    @Override
    public double getX2() {
        return targetNode.getCenterX();
    }

    @Override
    public double getY2() {
        return targetNode.getCenterY();
    }

    @Override
    public String getExtraData() {
        return sourceNode.getLabel() + "," + targetNode.getLabel() + "," + weight;
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
