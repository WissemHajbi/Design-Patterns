package model;

import java.util.ArrayList;
import java.util.List;

import observer.Observable;
import observer.Observer;



public class DrawingModel implements Observable {

    private List<Observer> observers;
    private List<DrawableShape> shapes;
    private DrawableShape selectedShape;
    private String message;

    public DrawingModel() {
        observers = new ArrayList<>();
        shapes = new ArrayList<>();
        message = "Ready";
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyAllObservers() {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    public void setMessage(String message) {
        this.message = message;
        notifyAllObservers();
    }

    public String getMessage() {
        return message;
    }

    public List<DrawableShape> getShapes() {
        return shapes;
    }

    public void setShapes(List<DrawableShape> newShapes) {
        shapes.clear();
        shapes.addAll(newShapes);
        setMessage("Load drawing");
    }

    public void addShape(DrawableShape shape) {
        shapes.add(shape);
    }

    public void addShapeAt(int index, DrawableShape shape) {
        if (index < 0 || index > shapes.size()) {
            shapes.add(shape);
        } else {
            shapes.add(index, shape);
        }
    }

    public void removeShape(DrawableShape shape) {
        shapes.remove(shape);
    }

    public void clearAll() {
        shapes.clear();
        selectedShape = null;
        setMessage("Drawing cleared");
    }

    public DrawableShape getSelectedShape() {
        return selectedShape;
    }

    public void setSelectedShape(DrawableShape selectedShape) {
        this.selectedShape = selectedShape;
        if (selectedShape == null) {
            setMessage("Shape selection cleared");
        } else {
            setMessage("Shape selected: " + selectedShape.getType());
        }
    }

    public void setSelectedShapeSilently(DrawableShape selectedShape) {
        this.selectedShape = selectedShape;
    }

    public List<GraphNodeShape> getGraphNodes() {
        List<GraphNodeShape> nodes = new ArrayList<>();
        for (DrawableShape shape : shapes) {
            if (shape instanceof GraphNodeShape) {
                nodes.add((GraphNodeShape) shape);
            }
        }
        return nodes;
    }

    public List<GraphEdgeShape> getGraphEdges() {
        List<GraphEdgeShape> edges = new ArrayList<>();
        for (DrawableShape shape : shapes) {
            if (shape instanceof GraphEdgeShape) {
                edges.add((GraphEdgeShape) shape);
            }
        }
        return edges;
    }

    public List<String> getShapeDescriptions() {
        List<String> items = new ArrayList<>();
        for (DrawableShape shape : shapes) {
            items.add(shape.getType() + " : " + shape.serialize());
        }
        return items;
    }
}
