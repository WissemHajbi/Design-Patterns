package app;

import controller.DrawingController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        DrawingController controller = new DrawingController();
        Scene scene = new Scene(controller.getRoot(), 1100, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX Drawing Application using Design Patterns");
        primaryStage.show();
    }
}
