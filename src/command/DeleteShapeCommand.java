package command;

import model.DrawableShape;
import model.DrawingModel;

// Command for deleting a shape.
public class DeleteShapeCommand implements Command {

    private DrawingModel model;
    private DrawableShape shape;
    private int index;

    public DeleteShapeCommand(DrawingModel model, DrawableShape shape) {
        this.model = model;
        this.shape = shape;
        this.index = -1;
    }

    @Override
    public void execute() {
        index = model.getShapes().indexOf(shape);
        model.removeShape(shape);
        model.setSelectedShapeSilently(null);
        model.setMessage("Shape deleted: " + shape.getType());
    }

    @Override
    public void undo() {
        model.addShapeAt(index, shape);
        model.setSelectedShapeSilently(shape);
    }
}
