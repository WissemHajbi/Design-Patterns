package command;

import model.DrawableShape;
import model.DrawingModel;

// Command for adding a shape.
public class AddShapeCommand implements Command {

    private DrawingModel model;
    private DrawableShape shape;

    public AddShapeCommand(DrawingModel model, DrawableShape shape) {
        this.model = model;
        this.shape = shape;
    }

    @Override
    public void execute() {
        model.addShape(shape);
        model.setSelectedShapeSilently(shape);
        model.setMessage("Shape drawn: " + shape.getType());
    }

    @Override
    public void undo() {
        model.removeShape(shape);
        model.setSelectedShapeSilently(null);
    }
}
