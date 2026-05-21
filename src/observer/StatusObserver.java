package observer;

import javafx.scene.control.Label;

// Observer used to update the status label.
public class StatusObserver implements Observer {

    private Label statusLabel;

    public StatusObserver(Label statusLabel) {
        this.statusLabel = statusLabel;
    }

    @Override
    public void update(String message) {
        statusLabel.setText(message);
    }
}
