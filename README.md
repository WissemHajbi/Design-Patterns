# JavaFX Drawing Application using Design Patterns

School mini project made with simple JavaFX and design patterns.

## Features
- Draw Rectangle, Circle, and Line
- Delete selected shape
- Undo last action
- Save and load drawings with SQLite
- Log actions with 3 strategies:
  - Console
  - File
  - Database
- Optional graph mode:
  - Node
  - Edge
  - Shortest path (BFS / Dijkstra)

## Patterns used
- Singleton: SQLite connection
- Factory: shape creation
- Adapter: JavaFX shapes behind one interface
- Observer: update status, logs, list view
- Strategy: logging and shortest path algorithms
- Decorator: extra style on shapes
- Command: undo

## Package structure
See `PACKAGE_STRUCTURE.txt`.

## Database
SQLite file: `drawing_app.db`

Tables:
- DRAWINGS
- SHAPES
- LOGS
- NODES
- EDGES

## Run notes
- Use Java 8+ with JavaFX available
- Add SQLite JDBC driver
- Start class: `app.MainApp`

## Short explanation of patterns
See `UML_Explanation.md`.
