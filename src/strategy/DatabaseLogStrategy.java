package strategy;

import db.LogDAO;

// Logging in SQLite database.
public class DatabaseLogStrategy implements LogStrategy {

    private LogDAO logDAO;

    public DatabaseLogStrategy() {
        logDAO = new LogDAO();
    }

    @Override
    public void log(String message) {
        String action = "ACTION";
        String text = message;
        if (message != null && message.contains(":")) {
            int index = message.indexOf(":");
            action = message.substring(0, index).trim();
            text = message.substring(index + 1).trim();
        }
        logDAO.saveLog(action, text);
    }
}
