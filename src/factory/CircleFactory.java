package factory;

import model.CircleAdapter;

public class CircleFactory {

    public static CircleAdapter create(double x1, double y1, double x2, double y2) {
        return new CircleAdapter(x1, y1, x2, y2);
    }
}
