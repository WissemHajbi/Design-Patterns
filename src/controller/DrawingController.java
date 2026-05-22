package controller;

import java.util.ArrayList;
import java.util.List;

import command.AddShapeCommand;
import command.CommandManager;
import command.DeleteShapeCommand;
import decorator.BorderDecorator;
import decorator.ColorDecorator;
import decorator.SelectedDecorator;
import db.DrawingDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import factory.ShapeFactory;
import model.DrawableShape;
import model.DrawingInfo;
import model.DrawingModel;
import model.GraphEdgeShape;
import model.GraphNodeShape;
import observer.LogObserver;
import observer.ShapeListObserver;
import observer.StatusObserver;
import strategy.BFSStrategy;
import strategy.ConsoleLogStrategy;
import strategy.DatabaseLogStrategy;
import strategy.DijkstraStrategy;
import strategy.FileLogStrategy;
import strategy.LogStrategy;
import strategy.ShortestPathContext;



public class DrawingController {

    private BorderPane root;
    private Pane drawingPane;
    private Label statusLabel;
    private Label titleLabel;
    private TextField drawingNameField;
    private ComboBox<DrawingInfo> drawingComboBox;
    private ComboBox<String> modeComboBox;
    private ComboBox<String> logStrategyComboBox;
    private ComboBox<String> shortestPathComboBox;
    private ListView<String> shapeListView;
    private Button rectangleButton;
    private Button circleButton;
    private Button lineButton;
    private Button nodeButton;
    private Button edgeButton;
    private Button saveButton;
    private Button loadButton;
    private Button deleteButton;
    private Button undoButton;
    private Button setStartButton;
    private Button setEndButton;
    private Button shortestPathButton;

    private DrawingModel model;
    private DrawingDAO drawingDAO;
    private CommandManager commandManager;
    private ShortestPathContext shortestPathContext;
    private LogObserver logObserver;

    private String selectedTool;
    private String selectedMode;
    private double startX;
    private double startY;
    private GraphNodeShape pendingEdgeNode;
    private GraphNodeShape startNode;
    private GraphNodeShape endNode;

    public DrawingController() {
        model = new DrawingModel();
        drawingDAO = new DrawingDAO();
        commandManager = new CommandManager();
        shortestPathContext = new ShortestPathContext();
        selectedTool = "RECTANGLE";
        selectedMode = "SHAPE";

        createView();
        createObservers();
        createEvents();
        updateDrawingList();
        setLogStrategy("Console");
        updateMode();
        refreshDrawingPane();
    }

    public BorderPane getRoot() {
        return root;
    }

    private void createView() {
        root = new BorderPane();
        root.setPadding(new Insets(10));

        titleLabel = new Label("JavaFX Drawing Application using Design Patterns");

        drawingNameField = new TextField();
        drawingNameField.setPromptText("Drawing name");
        drawingNameField.setPrefWidth(150);

        saveButton = new Button("Save");
        loadButton = new Button("Load");

        drawingComboBox = new ComboBox<>();
        drawingComboBox.setPrefWidth(250);

        HBox topBox = new HBox(10);
        topBox.setPadding(new Insets(5));
        topBox.getChildren().addAll(titleLabel, drawingNameField, saveButton, loadButton, drawingComboBox);
        root.setTop(topBox);

        VBox leftBox = new VBox(10);
        leftBox.setPadding(new Insets(5));
        Label paletteLabel = new Label("Palette");

        modeComboBox = new ComboBox<>();
        modeComboBox.getItems().addAll("Shape Mode", "Graph Mode");
        modeComboBox.setValue("Shape Mode");

        rectangleButton = new Button("Rectangle");
        circleButton = new Button("Circle");
        lineButton = new Button("Line");
        nodeButton = new Button("Node");
        edgeButton = new Button("Edge");

        leftBox.getChildren().addAll(paletteLabel, modeComboBox, rectangleButton, circleButton, lineButton, nodeButton, edgeButton);
        root.setLeft(leftBox);

        drawingPane = new Pane();
        drawingPane.setPrefSize(700, 500);
        drawingPane.setStyle("-fx-background-color: white; -fx-border-color: black;");

        StackPane centerPane = new StackPane();
        centerPane.getChildren().add(drawingPane);
        root.setCenter(centerPane);

        VBox rightBox = new VBox(10);
        rightBox.setPadding(new Insets(5));

        Label actionsLabel = new Label("Actions");

        logStrategyComboBox = new ComboBox<>();
        logStrategyComboBox.getItems().addAll("Console", "File", "Database");
        logStrategyComboBox.setValue("Console");

        shortestPathComboBox = new ComboBox<>();
        shortestPathComboBox.getItems().addAll("Dijkstra", "BFS");
        shortestPathComboBox.setValue("Dijkstra");

        deleteButton = new Button("Delete");
        undoButton = new Button("Undo");
        setStartButton = new Button("Set Start");
        setEndButton = new Button("Set End");
        shortestPathButton = new Button("Shortest Path");

        shapeListView = new ListView<>();
        shapeListView.setPrefHeight(220);

        rightBox.getChildren().addAll(actionsLabel, logStrategyComboBox, shortestPathComboBox, setStartButton, setEndButton, shortestPathButton, deleteButton, undoButton, shapeListView);
        root.setRight(rightBox);

        statusLabel = new Label("Ready");
        HBox bottomBox = new HBox(statusLabel);
        bottomBox.setPadding(new Insets(5));
        root.setBottom(bottomBox);
    }

    private void createObservers() {
        StatusObserver statusObserver = new StatusObserver(statusLabel);
        logObserver = new LogObserver(new ConsoleLogStrategy());
        ShapeListObserver shapeListObserver = new ShapeListObserver(shapeListView, model);

        model.addObserver(statusObserver);
        model.addObserver(logObserver);
        model.addObserver(shapeListObserver);
    }

    private void createEvents() {
        modeComboBox.setOnAction(e -> {
            if ("Graph Mode".equals(modeComboBox.getValue())) {
                selectedMode = "GRAPH";
            } else {
                selectedMode = "SHAPE";
            }
            updateMode();
            model.setMessage("Mode selected: " + modeComboBox.getValue());
        });

        logStrategyComboBox.setOnAction(e -> setLogStrategy(logStrategyComboBox.getValue()));

        rectangleButton.setOnAction(e -> selectTool("RECTANGLE"));
        circleButton.setOnAction(e -> selectTool("CIRCLE"));
        lineButton.setOnAction(e -> selectTool("LINE"));
        nodeButton.setOnAction(e -> selectTool("NODE"));
        edgeButton.setOnAction(e -> selectTool("EDGE"));

        saveButton.setOnAction(e -> saveDrawing());
        loadButton.setOnAction(e -> loadDrawing());
        deleteButton.setOnAction(e -> deleteSelectedShape());
        undoButton.setOnAction(e -> undoLastAction());
        shortestPathButton.setOnAction(e -> showShortestPath());
        setStartButton.setOnAction(e -> setStartNode());
        setEndButton.setOnAction(e -> setEndNode());

        drawingPane.setOnMousePressed(e -> {
            startX = e.getX();
            startY = e.getY();
        });

        drawingPane.setOnMouseReleased(e -> {
            if ("SHAPE".equals(selectedMode)) {
                if ("RECTANGLE".equals(selectedTool) || "CIRCLE".equals(selectedTool) || "LINE".equals(selectedTool)) {
                    DrawableShape shape = ShapeFactory.createShape(selectedTool, startX, startY, e.getX(), e.getY());
                    shape = new ColorDecorator(shape, Color.WHITE, Color.BLACK);
                    shape = new BorderDecorator(shape);
                    commandManager.executeCommand(new AddShapeCommand(model, shape));
                    refreshDrawingPane();
                }
            }
        });

        drawingPane.setOnMouseClicked(e -> {
            if ("GRAPH".equals(selectedMode)) {
                if ("NODE".equals(selectedTool)) {
                    String label = "N" + (model.getGraphNodes().size() + 1);
                    DrawableShape node = ShapeFactory.createNode(label, e.getX(), e.getY());
                    commandManager.executeCommand(new AddShapeCommand(model, node));
                    refreshDrawingPane();
                }
            }
        });
    }

    private void selectTool(String tool) {
        selectedTool = tool;
        model.setMessage("Shape selected: " + tool);
    }

    private void setLogStrategy(String value) {
        LogStrategy strategy;
        if ("File".equals(value)) {
            strategy = new FileLogStrategy();
        } else if ("Database".equals(value)) {
            strategy = new DatabaseLogStrategy();
        } else {
            strategy = new ConsoleLogStrategy();
        }
        logObserver.setLogStrategy(strategy);
    }

    private void updateMode() {
        boolean shapeMode = "SHAPE".equals(selectedMode);
        rectangleButton.setDisable(!shapeMode);
        circleButton.setDisable(!shapeMode);
        lineButton.setDisable(!shapeMode);
        nodeButton.setDisable(shapeMode);
        edgeButton.setDisable(shapeMode);
        setStartButton.setDisable(shapeMode);
        setEndButton.setDisable(shapeMode);
        shortestPathButton.setDisable(shapeMode);
        shortestPathComboBox.setDisable(shapeMode);
    }

    private void refreshDrawingPane() {
        drawingPane.getChildren().clear();

        List<DrawableShape> normalShapes = new ArrayList<>();
        List<DrawableShape> edgeShapes = new ArrayList<>();

        for (DrawableShape shape : model.getShapes()) {
            if ("EDGE".equals(shape.getType())) {
                edgeShapes.add(shape);
            } else {
                normalShapes.add(shape);
            }
        }

        for (DrawableShape shape : edgeShapes) {
            addShapeToPane(shape);
        }
        for (DrawableShape shape : normalShapes) {
            addShapeToPane(shape);
        }
    }

    private void addShapeToPane(DrawableShape shape) {
        Node node;
        if (shape == model.getSelectedShape()) {
            node = new SelectedDecorator(shape).getNode();
        } else {
            shape.setNormalStyle();
            node = shape.getNode();
        }

        node.setOnMouseClicked(e -> {
            e.consume();
            handleShapeClick(shape);
        });

        drawingPane.getChildren().add(node);
    }

    private void handleShapeClick(DrawableShape shape) {
        if ("GRAPH".equals(selectedMode) && shape instanceof GraphNodeShape && "EDGE".equals(selectedTool)) {
            handleEdgeSelection((GraphNodeShape) shape);
            return;
        }
        model.setSelectedShape(shape);
        refreshDrawingPane();
    }

    private void handleEdgeSelection(GraphNodeShape node) {
        if (pendingEdgeNode == null) {
            pendingEdgeNode = node;
            model.setMessage("Select the target node");
        } else {
            if (pendingEdgeNode != node) {
                DrawableShape edge = ShapeFactory.createEdge(pendingEdgeNode, node);
                commandManager.executeCommand(new AddShapeCommand(model, edge));
                refreshDrawingPane();
            }
            pendingEdgeNode = null;
        }
    }

    private void deleteSelectedShape() {
        DrawableShape selected = model.getSelectedShape();
        if (selected == null) {
            showInfo("Delete", "No shape selected.");
            return;
        }

        commandManager.executeCommand(new DeleteShapeCommand(model, selected));
        if (selected == startNode) {
            startNode = null;
        }
        if (selected == endNode) {
            endNode = null;
        }
        refreshDrawingPane();
    }

    private void undoLastAction() {
        commandManager.undo();
        refreshDrawingPane();
        model.setMessage("Undo");
    }

    private void saveDrawing() {
        String name = drawingNameField.getText();
        if (name == null || name.trim().isEmpty()) {
            name = "Drawing " + (drawingComboBox.getItems().size() + 1);
        }

        drawingDAO.saveDrawing(name, model.getShapes());
        updateDrawingList();
        model.setMessage("Save drawing");
    }

    private void loadDrawing() {
        DrawingInfo selected = drawingComboBox.getValue();
        if (selected == null) {
            showInfo("Load", "Choose a drawing first.");
            return;
        }

        List<DrawableShape> shapes = drawingDAO.loadDrawingShapes(selected.getId());
        model.setShapes(shapes);
        model.setSelectedShapeSilently(null);
        commandManager.clear();
        pendingEdgeNode = null;
        startNode = null;
        endNode = null;
        refreshDrawingPane();
    }

    private void updateDrawingList() {
        ObservableList<DrawingInfo> items = FXCollections.observableArrayList(drawingDAO.loadDrawings());
        drawingComboBox.setItems(items);
    }

    private void setStartNode() {
        DrawableShape selected = model.getSelectedShape();
        if (selected instanceof GraphNodeShape) {
            startNode = (GraphNodeShape) selected;
            model.setMessage("Start node selected: " + startNode.getLabel());
        } else {
            showInfo("Start Node", "Select a graph node first.");
        }
    }

    private void setEndNode() {
        DrawableShape selected = model.getSelectedShape();
        if (selected instanceof GraphNodeShape) {
            endNode = (GraphNodeShape) selected;
            model.setMessage("End node selected: " + endNode.getLabel());
        } else {
            showInfo("End Node", "Select a graph node first.");
        }
    }

    private void showShortestPath() {
        if (startNode == null || endNode == null) {
            showInfo("Shortest Path", "Please choose a start node and an end node.");
            return;
        }

        if (model.getGraphNodes().size() < 2) {
            showInfo("Shortest Path", "At least two nodes are required.");
            return;
        }

        GraphNodeShape startNodeValue = startNode;
        GraphNodeShape endNodeValue = endNode;

        if ("BFS".equals(shortestPathComboBox.getValue())) {
            shortestPathContext.setStrategy(new BFSStrategy());
        } else {
            shortestPathContext.setStrategy(new DijkstraStrategy());
        }

        List<GraphNodeShape> path = shortestPathContext.findPath(startNodeValue, endNodeValue, model.getGraphNodes(), model.getGraphEdges());
        if (path.isEmpty()) {
            showInfo("Shortest Path", "No path found.");
            return;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < path.size(); i++) {
            builder.append(path.get(i).getLabel());
            if (i < path.size() - 1) {
                builder.append(" -> ");
            }
        }

        model.setMessage("Shortest path: " + builder.toString());
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
