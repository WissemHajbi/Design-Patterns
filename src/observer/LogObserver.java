package observer;

import strategy.LogStrategy;

// Observer used to log actions automatically.
public class LogObserver implements Observer {

    private LogStrategy logStrategy;

    public LogObserver(LogStrategy logStrategy) {
        this.logStrategy = logStrategy;
    }

    public void setLogStrategy(LogStrategy logStrategy) {
        this.logStrategy = logStrategy;
    }

    @Override
    public void update(String message) {
        if (logStrategy != null) {
            logStrategy.log(message);
        }
    }
}
