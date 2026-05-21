package command;

import java.util.Stack;

// Manages the undo stack.
public class CommandManager {

    private Stack<Command> undoStack;

    public CommandManager() {
        undoStack = new Stack<>();
    }

    public void executeCommand(Command command) {
        command.execute();
        undoStack.push(command);
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            Command command = undoStack.pop();
            command.undo();
        }
    }

    public void clear() {
        undoStack.clear();
    }
}
