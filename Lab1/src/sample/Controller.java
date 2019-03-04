package sample;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.geometry.Line;
import sample.geometry.Ray;
import sample.geometry.Segment;
import sample.geometry.Shape;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;

import java.util.ArrayList;


enum ShapeType {
    SEGMENT, RAY, LINE
}

public class Controller {
    @FXML private Canvas canvas;
    @FXML private ColorPicker strokePicker;
    @FXML private ColorPicker fillPicker;
    @FXML private Label coordLabel;
    @FXML private RadioButton segmentRb;
    @FXML private RadioButton rayRb;
    @FXML private RadioButton lineRb;
    @FXML private TextField lineWidthTf;
    @FXML private ComboBox<String> lineStyleCb;

    private GraphicsContext gc;
    private boolean isDrawing;
    private ArrayList<Point2D> pool;
    private ShapeType currentShape;
    private int currentLineStyle;
    private int currentLineWidth;
    private int currentLimit;
    private final String strSolid = "Solid", strDashed = "Dashed", strDotted = "Dotted";

    private ArrayList<Shape> shapes;

    @FXML private void initialize() {
        coordLabel.setText("");
        strokePicker.setTooltip(new Tooltip("Stroke"));
        fillPicker.setTooltip(new Tooltip("Fill"));
        strokePicker.setValue(Color.BLACK);
        lineStyleCb.getItems().addAll(strSolid, strDashed, strDotted);
        lineStyleCb.setValue(strSolid);

        pool = new ArrayList<>();
        isDrawing = false;
        currentLimit = -1;
        shapes = new ArrayList<>();

        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
        //Stage stage = (Stage)canvas.getScene().getWindow();
        ChangeListener<Number> canvasSizeListener = (observable, oldValue, newValue) -> drawShapes();
        canvas.widthProperty().addListener(canvasSizeListener);
        canvas.heightProperty().addListener(canvasSizeListener);
    }

    @FXML private void showCursorCoord(MouseEvent event) {
        double x = event.getX(), y = event.getY();
        coordLabel.setText(String.format("X: %.1f, Y: %.1f", x, y));
    }

    @FXML private void selectShape(ActionEvent event) {
        if (event.getSource() == segmentRb) {
            System.out.println("segment");
            currentShape = ShapeType.SEGMENT;
            currentLimit = 2;
        }
        if (event.getSource() == rayRb) {
            System.out.println("ray");
            currentShape = ShapeType.RAY;
            currentLimit = 2;
        }
        if (event.getSource() == lineRb) {
            System.out.println("line");
            currentShape = ShapeType.LINE;
            currentLimit = 2;
        }
    }

    @FXML private void addPoint(MouseEvent event) {
        pool.add(new Point2D(event.getX(), event.getY()));
        if (pool.size() == currentLimit) {
            newShape();
            pool.clear();
        }
    }

    private int selectStyle() {
        switch (lineStyleCb.getValue()) {
            case strSolid: {
                return 0;
            }
            case strDashed: {
                return 1;
            }
            case strDotted: {
                return 2;
            }
        }
        return -1;
    }

    private void newShape() {
        Shape shape;
        Color stroke = strokePicker.getValue();
        double lineWidth = Double.valueOf(lineWidthTf.getText());
        int lineStyle = selectStyle();

        switch (currentShape) {
            case SEGMENT: {
                shape = new Segment(pool.get(0), pool.get(1), stroke, lineWidth, lineStyle);
                shapes.add(shape);
                break;
            }
            case RAY: {
                shape = new Ray(pool.get(0), pool.get(1), stroke, lineWidth, lineStyle);
                shapes.add(shape);
                break;
            }
            case LINE: {
                shape = new Line(pool.get(0), pool.get(1), stroke, lineWidth, lineStyle);
                shapes.add(shape);
                break;
            }
        }
        drawShapes();
    }

    private void drawShapes() {
        gc.setFill(Color.WHITE);
        gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
        for (Shape shape : shapes) {
            shape.draw(gc);
        }
    }
}
