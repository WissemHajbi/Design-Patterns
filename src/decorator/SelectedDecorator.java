package decorator;

import javafx.scene.Node;
import model.DrawableShape;

// Adds a selected effect to the shape.
public class SelectedDecorator extends ShapeDecorator {

    public SelectedDecorator(DrawableShape shape) {
        super(shape);
    }

    @Override
    public Node getNode() {
        Node node = shape.getNode();
        shape.setSelectedStyle();
        return node;
    }
}
