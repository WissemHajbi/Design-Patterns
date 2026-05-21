package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.DrawingInfo;
import model.DrawableShape;
import singleton.DatabaseConnection;

// DAO for drawings.
public class DrawingDAO {

    private ShapeDAO shapeDAO;

    public DrawingDAO() {
        shapeDAO = new ShapeDAO();
    }

    public int saveDrawing(String name, List<DrawableShape> shapes) {
        int drawingId = -1;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            String sql = "INSERT INTO DRAWINGS(name, created_at) VALUES(?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);
            statement.setString(2, LocalDateTime.now().toString());
            statement.executeUpdate();

            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                drawingId = keys.getInt(1);
            }
            keys.close();
            statement.close();

            if (drawingId != -1) {
                shapeDAO.saveShapes(drawingId, shapes);
            }
        } catch (Exception e) {
            System.out.println("Cannot save drawing.");
        }
        return drawingId;
    }

    public List<DrawingInfo> loadDrawings() {
        List<DrawingInfo> drawings = new ArrayList<>();
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            String sql = "SELECT id, name, created_at FROM DRAWINGS ORDER BY id DESC";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                drawings.add(new DrawingInfo(rs.getInt("id"), rs.getString("name"), rs.getString("created_at")));
            }
            rs.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Cannot load drawings.");
        }
        return drawings;
    }

    public List<DrawableShape> loadDrawingShapes(int drawingId) {
        return shapeDAO.loadShapes(drawingId);
    }
}
