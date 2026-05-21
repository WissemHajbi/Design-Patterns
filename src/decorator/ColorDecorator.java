package decorator;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import model.DrawableShape;

// Adds a simple color style.
public class ColorDecorator extends ShapeDecorator {

    private Color fillColor;
    private Color strokeColor;

    public ColorDecorator(DrawableShape shape, Color fillColor, Color strokeColor) {
        super(shape);
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
    }

    @Override
    public Node getNode() {
        Node node = shape.getNode();
        if (node instanceof Shape) {
            Shape fxShape = (Shape) node;
            fxShape.setFill(fillColor);
            fxShape.setStroke(strokeColor);
        }
        return node;
    }
}
