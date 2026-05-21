# UML Explanation per Pattern

## 1) Singleton
**Problem:** The project needs one shared database connection.

**Solution:** A class with private constructor and a static `getInstance()` method.

**Classes involved:** `singleton.DatabaseConnection`

**Why chosen:** It avoids multiple SQLite connections and keeps the code simple.

## 2) Factory
**Problem:** The controller should not create shapes directly.

**Solution:** A factory class creates the right shape from a type string.

**Classes involved:** `factory.ShapeFactory`, `model.DrawableShape`, `RectangleAdapter`, `CircleAdapter`, `LineAdapter`

**Why chosen:** It centralizes object creation and keeps the controller clean.

## 3) Adapter
**Problem:** JavaFX shapes have different classes and methods.

**Solution:** Each JavaFX shape is wrapped by one common interface.

**Classes involved:** `model.DrawableShape`, `RectangleAdapter`, `CircleAdapter`, `LineAdapter`

**Why chosen:** The controller works with one interface only.

## 4) Observer
**Problem:** When the drawing changes, several UI parts must update.

**Solution:** The model notifies observers automatically.

**Classes involved:** `model.DrawingModel`, `observer.Observer`, `observer.Observable`, `StatusObserver`, `LogObserver`, `ShapeListObserver`

**Why chosen:** It decouples the model from the UI updates.

## 5) Strategy
**Problem:** Logging and shortest path algorithms must change dynamically.

**Solution:** Use an interface and several interchangeable implementations.

**Classes involved:** `strategy.LogStrategy`, `ConsoleLogStrategy`, `FileLogStrategy`, `DatabaseLogStrategy`, `ShortestPathStrategy`, `BFSStrategy`, `DijkstraStrategy`, `ShortestPathContext`

**Why chosen:** It avoids big switch statements and keeps code extensible.

## 6) Decorator
**Problem:** Shapes need extra style effects without changing base classes.

**Solution:** Wrap shapes with decorators that add style.

**Classes involved:** `decorator.ShapeDecorator`, `BorderDecorator`, `ColorDecorator`, `SelectedDecorator`

**Why chosen:** It adds behavior dynamically and keeps the base shapes simple.

## 7) Command
**Problem:** The app must undo draw and delete actions.

**Solution:** Each action is stored as a command object.

**Classes involved:** `command.Command`, `AddShapeCommand`, `DeleteShapeCommand`, `CommandManager`

**Why chosen:** It makes undo easy with a stack.
