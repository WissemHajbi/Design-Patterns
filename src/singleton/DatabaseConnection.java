package singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

// Singleton for the SQLite connection.
public class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:drawing_app.db");
            initializeDatabase();
        } catch (SQLException e) {
            System.out.println("Database connection error.");
        }
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    private void initializeDatabase() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("PRAGMA foreign_keys = ON");

        statement.executeUpdate("CREATE TABLE IF NOT EXISTS DRAWINGS ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT NOT NULL, "
                + "created_at TEXT NOT NULL"
                + ")");

        statement.executeUpdate("CREATE TABLE IF NOT EXISTS SHAPES ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "drawing_id INTEGER NOT NULL, "
                + "type TEXT NOT NULL, "
                + "x1 REAL, "
                + "y1 REAL, "
                + "x2 REAL, "
                + "y2 REAL, "
                + "color TEXT, "
                + "stroke TEXT, "
                + "extra_data TEXT, "
                + "FOREIGN KEY(drawing_id) REFERENCES DRAWINGS(id) ON DELETE CASCADE"
                + ")");

        statement.executeUpdate("CREATE TABLE IF NOT EXISTS LOGS ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "action TEXT, "
                + "message TEXT, "
                + "created_at TEXT NOT NULL"
                + ")");

        statement.executeUpdate("CREATE TABLE IF NOT EXISTS NODES ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "drawing_id INTEGER NOT NULL, "
                + "label TEXT, "
                + "x REAL, "
                + "y REAL, "
                + "FOREIGN KEY(drawing_id) REFERENCES DRAWINGS(id) ON DELETE CASCADE"
                + ")");

        statement.executeUpdate("CREATE TABLE IF NOT EXISTS EDGES ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "drawing_id INTEGER NOT NULL, "
                + "source_node TEXT, "
                + "target_node TEXT, "
                + "weight REAL, "
                + "FOREIGN KEY(drawing_id) REFERENCES DRAWINGS(id) ON DELETE CASCADE"
                + ")");

        statement.close();
    }
}
