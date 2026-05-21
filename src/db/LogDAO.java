package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;

import singleton.DatabaseConnection;

// DAO used by the logging database strategy.
public class LogDAO {

    public void saveLog(String action, String message) {
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            String sql = "INSERT INTO LOGS(action, message, created_at) VALUES(?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, action);
            statement.setString(2, message);
            statement.setString(3, LocalDateTime.now().toString());
            statement.executeUpdate();
            statement.close();
        } catch (Exception e) {
            System.out.println("Cannot save log.");
        }
    }
}
