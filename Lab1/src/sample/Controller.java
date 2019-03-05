package sample;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    @FXML private Button clearButton;
    @FXML private RadioButton segmentRb;
    @FXML private RadioButton rayRb;
    @FXML private RadioButton lineRb;
    @FXML private TextField lineWidthTf;
    @FXML private ComboBox<String> lineStyleCb;

    private GraphicsContext gc;
    private boolean isDrawing;
    private ArrayList<Point2D> pool;
    private ShapeType currentShape;
    private int currentLimit;
    private final String strSolid = "Solid", strDashed = "Dashed", strDotted = "Dotted";

    private ArrayList<Shape> shapes;

    private void setInitialControls() {
        coordLabel.setText("");
        strokePicker.setValue(Color.BLACK);
        lineStyleCb.getItems().addAll(strSolid, strDashed, strDotted);
        lineStyleCb.setValue(strSolid);

        RadioButton[] rbs = new RadioButton[]{segmentRb, rayRb, lineRb};
        String[] names = new String[]{"segment", "ray", "line"};
        for (int i = 0; i < names.length; ++i) {
            RadioButton rb = rbs[i];
            String name = names[i];
            rb.getStyleClass().remove("radio-button");
            rb.getStyleClass().add("toggle-button");
            ImageView iv = new ImageView(new Image("file:res/icon_"+name+".png"));
            iv.setPreserveRatio(true);
            iv.setFitHeight(32);
            rb.setGraphic(iv);
        }

        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());

        ChangeListener<Number> canvasSizeListener = (observable, oldValue, newValue) -> drawShapes();
        canvas.widthProperty().addListener(canvasSizeListener);
        canvas.heightProperty().addListener(canvasSizeListener);
    }
    private void setVariables() {
        pool = new ArrayList<>();
        isDrawing = false;
        currentLimit = -1;
        shapes = new ArrayList<>();
    }

    @FXML private void initialize() {
        setInitialControls();
        setVariables();
    }

    @FXML private void showCursorCoord(MouseEvent event) {
        double x = event.getX(), y = event.getY();
        coordLabel.setText(String.format("X: %.1f, Y: %.1f", x, y));
    }

    @FXML private void selectShape(ActionEvent event) {
        pool.clear();
        drawShapes();
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
        Point2D newPoint = new Point2D(event.getX(), event.getY());
        pool.add(newPoint);
        drawPoint(newPoint);
        if (pool.size() == currentLimit) {
            newShape();
            pool.clear();
            drawShapes();
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
        Color fill = fillPicker.getValue();
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
    }

    private void drawPoint(Point2D p) {
        gc.setFill(Color.DARKGRAY);
        double s = 4, t = s/2;
        gc.fillOval(p.getX()-t, p.getY()-t, s, s);
    }

    private void drawShapes() {
        gc.setFill(Color.WHITE);
        gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
        for (Shape shape : shapes) {
            shape.draw(gc);
        }
    }

    @FXML private void clear() {
        shapes.clear();
        drawShapes();
    }
}
