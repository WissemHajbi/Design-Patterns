package factory;

import model.LineAdapter;

public class LineFactory {

    public static LineAdapter create(double x1, double y1, double x2, double y2) {
        return new LineAdapter(x1, y1, x2, y2);
    }
}
