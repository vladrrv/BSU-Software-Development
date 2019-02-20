package sample.geometry;

public abstract class Shape {
    private javafx.geometry.Point2D theCenter;
    private java.util.ArrayList<javafx.geometry.Point2D> points;
    private javafx.scene.paint.Color strokeColor;

    public abstract void draw();

    public void move() {
    }

    public void location() {
    }
}
