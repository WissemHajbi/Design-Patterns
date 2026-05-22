package model;

import javafx.scene.Node;



public interface DrawableShape {

    Node getNode();

    String getType();

    String serialize();

    double getX1();

    double getY1();

    double getX2();

    double getY2();

    String getExtraData();

    void setNormalStyle();

    void setSelectedStyle();
}
