package decorator;

import javafx.scene.Node;
import javafx.scene.shape.Shape;
import model.DrawableShape;


public class BorderDecorator extends ShapeDecorator {

    public BorderDecorator(DrawableShape shape) {
        super(shape);
    }

    @Override
    public Node getNode() {
        Node node = shape.getNode();
        if (node instanceof Shape) {
            Shape fxShape = (Shape) node;
            fxShape.setStrokeWidth(2);
        }
        return node;
    }
}
