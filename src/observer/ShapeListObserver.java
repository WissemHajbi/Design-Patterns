package observer;

import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import model.DrawingModel;

// Observer used to refresh the list of shapes.
public class ShapeListObserver implements Observer {

    private ListView<String> listView;
    private DrawingModel model;

    public ShapeListObserver(ListView<String> listView, DrawingModel model) {
        this.listView = listView;
        this.model = model;
    }

    @Override
    public void update(String message) {
        listView.setItems(FXCollections.observableArrayList(model.getShapeDescriptions()));
    }
}
