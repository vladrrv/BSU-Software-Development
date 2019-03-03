package sample;

import sample.geometry.Line;
import sample.geometry.Ray;
import sample.geometry.Segment;
import sample.geometry.Shape;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;

import java.util.ArrayList;


enum Shapes {
    SEGMENT, RAY, LINE
}

public class Controller {
    @FXML private Canvas canvas;
    @FXML private ColorPicker borderColorPicker;
    @FXML private ColorPicker fillColorPicker;
    @FXML private Label coordLabel;
    @FXML private RadioButton segmentRb;
    @FXML private RadioButton rayRb;
    @FXML private RadioButton lineRb;

    private boolean isDrawing;
    private ArrayList<Point2D> pool;
    private Shapes currentShape;
    private int currentLimit;

    private ArrayList<Shape> shapes;

    @FXML private void initialize() {
        coordLabel.setText("");
        borderColorPicker.setTooltip(new Tooltip("Border Color"));
        fillColorPicker.setTooltip(new Tooltip("Fill Color"));
        pool = new ArrayList<>();
        isDrawing = false;
        currentLimit = -1;
        shapes = new ArrayList<>();
    }

    @FXML private void showCursorCoord(MouseEvent event) {
        double x = event.getX(), y = event.getY();
        coordLabel.setText(String.format("X: %.1f, Y: %.1f", x, y));
    }

    @FXML private void selectShape(ActionEvent event) {
        if (event.getSource() == segmentRb) {
            System.out.println("segment");
            currentShape = Shapes.SEGMENT;
            currentLimit = 2;
        }
        if (event.getSource() == rayRb) {
            System.out.println("ray");
            currentShape = Shapes.RAY;
            currentLimit = 2;
        }
        if (event.getSource() == lineRb) {
            System.out.println("line");
            currentShape = Shapes.LINE;
            currentLimit = 2;
        }
    }

    @FXML private void addPoint(MouseEvent event) {
        pool.add(new Point2D(event.getX(), event.getY()));
        if (pool.size() == currentLimit) {
            newShape();
        }
    }

    private void newShape() {
        Shape shape;
        Color stroke = borderColorPicker.getValue();
        switch (currentShape) {
            case SEGMENT: {
                shape = new Segment(pool.get(0), pool.get(1), stroke);
                shapes.add(shape);
                break;
            }
            case RAY: {
                shape = new Ray(pool.get(0), pool.get(1), stroke);
                shapes.add(shape);
                break;
            }
            case LINE: {
                shape = new Line(pool.get(0), pool.get(1), stroke);
                shapes.add(shape);
                break;
            }
        }
        pool.clear();
        drawShapes();
    }

    private void drawShapes() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
        for (Shape shape : shapes) {
            shape.draw(gc);
        }
    }
}
