package sample;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;

import javax.imageio.ImageIO;
import java.io.File;
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
    @FXML private ListView<Shape> shapesLv;
    @FXML private CheckBox moveButton;

    enum ShapeType {
        SEGMENT(0), RAY(1), LINE(2), POLYLINE(3),
        ELLIPSE(4), CIRCLE(5),
        POLYGON(6), RECTANGLE(7), REGPOLYGON(8), PARALLELOGRAM(9), RHOMBUS(10);

        private String fullName;
        private int nPoints;
        private boolean hasLineStyle;

        ShapeType(int v) {
            switch (v) {
                case 0: {
                    fullName = "Segment";
                    nPoints = Segment.getnPoints();
                    hasLineStyle = true;
                    break;
                }
                case 1: {
                    fullName = "Ray";
                    nPoints = Ray.getnPoints();
                    hasLineStyle = true;
                    break;
                }
                case 2: {
                    fullName = "Line";
                    nPoints = Line.getnPoints();
                    hasLineStyle = true;
                    break;
                }
                case 3: {
                    fullName = "Polyline";
                    nPoints = Polyline.getnPoints();
                    hasLineStyle = true;
                    break;
                }
                case 4: {
                    fullName = "Ellipse";
                    nPoints = Ellipse.getnPoints();
                    hasLineStyle = false;
                    break;
                }
                case 5: {
                    fullName = "Circle";
                    nPoints = Circle.getnPoints();
                    hasLineStyle = false;
                    break;
                }
                case 6: {
                    fullName = "Polygon";
                    nPoints = Polygon.getnPoints();
                    hasLineStyle = false;
                    break;
                }
                case 7: {
                    fullName = "Rectangle";
                    nPoints = Rectangle.getnPoints();
                    hasLineStyle = false;
                    break;
                }
                case 8: {
                    fullName = "Regular Polygon";
                    nPoints = RegularPolygon.getnPoints();
                    hasLineStyle = false;
                    break;
                }
                case 9: {
                    fullName = "Parallelogram";
                    nPoints = Parallelogram.getnPoints();
                    hasLineStyle = false;
                    break;
                }
                case 10: {
                    fullName = "Rhombus";
                    nPoints = Rhombus.getnPoints();
                    hasLineStyle = false;
                    break;
                }
            }
        }

        public String getFullName() {
            return fullName;
        }
        public int getnPoints() {
            return nPoints;
        }

        public boolean hasLineStyle() {
            return hasLineStyle;
        }
    }

    private GraphicsContext gc;
    private ArrayList<Point2D> pool;
    private ShapeType currentShape;
    private int currentLimit, nVertices;
    private final int NO_DRAW = -2, UNLIMITED = -1;
    private final String
            strSolid = "Solid",
            strDashed = "Dashed",
            strDotted = "Dotted";

    private ObservableList<Shape> shapes;

    private TextInputDialog createNVerticesDialog() {
        TextInputDialog dialog = new TextInputDialog("5");
        dialog.setTitle("Enter number of vertices");
        dialog.setHeaderText("Specify number of vertices of regular polygon");
        dialog.setContentText("Enter integer: ");
        return dialog;
    }

    private Dialog<Pair<Double, Double>> createCanvasDialog() {
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
        Shape shape = null;
        Color stroke = strokePicker.getValue();
        Color fill = fillPicker.getValue();
        double lineWidth = Double.valueOf(lineWidthTf.getText());
        int lineStyle = selectStyle();

        switch (currentShape) {
            case SEGMENT: {
                shape = new Segment(pool.get(0), pool.get(1), stroke, lineWidth, lineStyle);
                break;
            }
            case RAY: {
                shape = new Ray(pool.get(0), pool.get(1), stroke, lineWidth, lineStyle);
                break;
            }
            case LINE: {
                shape = new Line(pool.get(0), pool.get(1), stroke, lineWidth, lineStyle);
                break;
            }
            case POLYLINE: {
                shape = new Polyline(pool, stroke, lineWidth, lineStyle);
                break;
            }
            case ELLIPSE: {
                shape = new Ellipse(pool, stroke, fill, lineWidth);
                break;
            }
            case CIRCLE: {
                shape = new Circle(pool, stroke, fill, lineWidth);
                break;
            }
            case POLYGON: {
                shape = new Polygon(pool, stroke, fill, lineWidth);
                break;
            }
            case RECTANGLE: {
                shape = new Rectangle(pool, stroke, fill, lineWidth);
                break;
            }
            case REGPOLYGON: {
                shape = new RegularPolygon(nVertices, pool, stroke, fill, lineWidth);
                break;
            }
            case PARALLELOGRAM: {
                shape = new Parallelogram(pool, stroke, fill, lineWidth);
                break;
            }
            case RHOMBUS: {
                shape = new Rhombus(pool, stroke, fill, lineWidth);
                break;
            }
        }
        shapes.add(shape);
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
            try {
                ImageView iv = new ImageView(
                        new Image(getClass().getResourceAsStream("res/icon_"+name.toLowerCase()+".png"))
                );
                iv.setPreserveRatio(true);
                iv.setFitHeight(32);
                DropShadow shadow = new DropShadow();
                shadow.setRadius(4);
                shadow.setOffsetX(1);
                shadow.setOffsetY(1);
                shadow.setColor(Color.GRAY);
                iv.setEffect(shadow);
                rb.setGraphic(iv);
            }
            catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Could not load icons");
                alert.setContentText(ex.getLocalizedMessage());
                alert.showAndWait();
            }
            rb.setTooltip(new Tooltip(v.getFullName()));
            rb.setOnAction(event -> {
                pool.clear();
                drawShapes();
                currentShape = v;
                currentLimit = v.getnPoints();
                lineStyleCb.setDisable(!v.hasLineStyle());
                if (v == ShapeType.REGPOLYGON) {
                    TextInputDialog dialog = createNVerticesDialog();
                    Optional<String> result = dialog.showAndWait();
                    if (result.isPresent())
                        nVertices = Integer.valueOf(result.get());
                    else {
                        rb.setSelected(false);
                        currentLimit = NO_DRAW;
                    }
                }
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

        moveButton.getStyleClass().remove("check-box");
        moveButton.getStyleClass().add("toggle-button");
        shapesLv.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue)-> {
                    moveButton.setDisable(newValue == null);
                    System.out.println(newValue);
                }
        );
    }

    private void setVariables() {
        pool = new ArrayList<>();
        currentLimit = NO_DRAW;
        shapes = shapesLv.getItems();
    }

    @FXML private void initialize() {
        setInitialControls();
        setVariables();
    }

    @FXML private void newCanvas() {
        Dialog<Pair<Double, Double>> dialog = createCanvasDialog();
        Optional<Pair<Double, Double>> result = dialog.showAndWait();

        result.ifPresent(widthHeight -> {
            canvas.setWidth(widthHeight.getKey());
            canvas.setHeight(widthHeight.getValue());
            clear();
        });
    }

    @FXML private void showCursorCoord(MouseEvent event) {
        double x = event.getX(), y = event.getY();
        coordLabel.setText(String.format("X: %.1f, Y: %.1f", x, y));
    }

    @FXML private void clickOnCanvas(MouseEvent event) {
        MouseButton mb = event.getButton();
        Point2D newPoint = new Point2D(event.getX(), event.getY());
        if (mb.equals(MouseButton.PRIMARY)) {
            if (moveButton.isSelected()) {
                pool.clear();
                Shape shape = shapesLv.getSelectionModel().getSelectedItem();
                shape.move(newPoint);
                shapesLv.refresh();
                moveButton.setSelected(false);
                drawShapes();
                return;
            }
            if (currentLimit != NO_DRAW) {
                if (event.getClickCount() == 1) {
                    pool.add(newPoint);
                    drawPoint(newPoint);
                }
                if (pool.size() == currentLimit || event.getClickCount() == 2 && currentLimit == UNLIMITED) {
                    newShape();
                    pool.clear();
                    drawShapes();
                }
            }
        }
    }

    @FXML private void clear() {
        shapes.clear();
        drawShapes();
    }

    @FXML private void about() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Draw Shapes! Application");
        alert.setContentText("This is an application for drawing different shapes.");
        ImageView iv = new ImageView(new Image(getClass().getResourceAsStream("res/icon_app.png")));
        iv.setPreserveRatio(true);
        iv.setFitHeight(64);
        alert.setGraphic(iv);
        alert.showAndWait();
    }

    @FXML private void close() {
        Stage stage = (Stage) canvas.getScene().getWindow();
        stage.close();
    }

    @FXML private void save() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save as");
        FileChooser.ExtensionFilter ef = new FileChooser.ExtensionFilter("PNG Files", "*.png");
        fc.getExtensionFilters().add(ef);
        fc.setSelectedExtensionFilter(ef);
        File file = fc.showSaveDialog(canvas.getScene().getWindow());
        if (file != null) {
            WritableImage wim = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
            canvas.snapshot(null, wim);
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(wim, null), "png", file);
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Could not save file.");
                alert.setContentText(ex.getLocalizedMessage());
            }
        }
    }
}
