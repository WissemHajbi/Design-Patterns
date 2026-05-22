package observer;

import javafx.scene.control.Label;


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
