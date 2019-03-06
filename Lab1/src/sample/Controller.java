package sample;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
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
import java.util.Optional;


public class Controller {
    @FXML private Canvas canvas;
    @FXML private ColorPicker strokePicker;
    @FXML private ColorPicker fillPicker;
    @FXML private Label coordLabel;
    @FXML private GridPane shapeSelectionPane;
    @FXML private TextField lineWidthTf;
    @FXML private ComboBox<String> lineStyleCb;

    enum ShapeType {
        SEGMENT(0), RAY(1), LINE(2);
        String fullName;
        ShapeType(int v) {
            switch (v) {
                case 0: {
                    fullName = "Segment";
                    break;
                }
                case 1: {
                    fullName = "Ray";
                    break;
                }
                case 2: {
                    fullName = "Line";
                    break;
                }
            }
        }

        public String getFullName() {
            return fullName;
        }
    }

    private GraphicsContext gc;
    private boolean isDrawing;
    private ArrayList<Point2D> pool;
    private ShapeType currentShape;
    private int currentLimit;
    private final String
            strSolid = "Solid",
            strDashed = "Dashed",
            strDotted = "Dotted";

    private ArrayList<Shape> shapes;

    private void setInitialControls() {
        coordLabel.setText("");
        strokePicker.setValue(Color.BLACK);
        lineStyleCb.getItems().addAll(strSolid, strDashed, strDotted);
        lineStyleCb.setValue(strSolid);

        int i = 0, c = shapeSelectionPane.getColumnCount();
        ToggleGroup gr = new ToggleGroup();
        for (ShapeType v : ShapeType.values()) {
            if (i % c == 0) shapeSelectionPane.addRow(i / c);
            String name = v.name();
            RadioButton rb = new RadioButton();
            rb.setToggleGroup(gr);
            rb.setCursor(Cursor.HAND);
            rb.getStyleClass().remove("radio-button");
            rb.getStyleClass().add("toggle-button");
            ImageView iv = new ImageView(new Image("file:res/icon_"+name+".png"));
            iv.setPreserveRatio(true);
            iv.setFitHeight(32);
            rb.setGraphic(iv);
            rb.setTooltip(new Tooltip(v.getFullName()));
            rb.setOnAction(event -> {
                pool.clear();
                drawShapes();
                currentShape = v;
                currentLimit = 2;
            });
            shapeSelectionPane.add(rb, i % c, i++ / c);
            GridPane.setHalignment(rb, HPos.CENTER);
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

    private Dialog<Pair<Double, Double>> createDialog() {
        Dialog<Pair<Double, Double>> dialog = new Dialog<>();
        dialog.setTitle("New Canvas");
        dialog.setHeaderText("Please, enter dimensions of the new canvas.");

        //dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));

        ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField widthTf = new TextField();
        widthTf.setPromptText("Width");
        TextField heightTf = new TextField();
        heightTf.setPromptText("Height");

        grid.add(new Label("Width:"), 0, 0);
        grid.add(widthTf, 1, 0);
        grid.add(new Label("Height:"), 0, 1);
        grid.add(heightTf, 1, 1);

        Node createButton = dialog.getDialogPane().lookupButton(createButtonType);
        createButton.setDisable(true);

        ChangeListener<String> cl = (observable, oldValue, newValue)->{
            String r = "-?\\d+(\\.\\d+)?";
            createButton.setDisable(!widthTf.getText().trim().matches(r)||!heightTf.getText().trim().matches(r));
        };
        widthTf.textProperty().addListener(cl);
        heightTf.textProperty().addListener(cl);

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(widthTf::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType) {
                return new Pair<>(Double.valueOf(widthTf.getText()), Double.valueOf(heightTf.getText()));
            }
            return null;
        });

        return dialog;
    }

    @FXML private void newCanvas() {
        Dialog<Pair<Double, Double>> dialog = createDialog();
        Optional<Pair<Double, Double>> result = dialog.showAndWait();

        result.ifPresent(widthHeight -> {
            canvas.setWidth(widthHeight.getKey());
            canvas.setHeight(widthHeight.getValue());
            clear();
        });
    }

    @FXML private void initialize() {
        setInitialControls();
        setVariables();
    }

    @FXML private void showCursorCoord(MouseEvent event) {
        double x = event.getX(), y = event.getY();
        coordLabel.setText(String.format("X: %.1f, Y: %.1f", x, y));
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

    @FXML private void close() {
        Stage stage = (Stage) canvas.getScene().getWindow();
        stage.close();
    }
}
