package factory;

import model.RectangleAdapter;

public class RectangleFactory {

    public static RectangleAdapter create(double x1, double y1, double x2, double y2) {
        return new RectangleAdapter(x1, y1, x2, y2);
    }
}
