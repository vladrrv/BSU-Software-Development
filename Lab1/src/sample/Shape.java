package sample;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Shape {

	private Color strokeColor;
	private double lineWidth;
	private Point2D center;

	public Shape() {
		this.strokeColor = Color.BLACK;
		this.center = new Point2D(0,0);
	}

	public Shape(Color strokeColor, double lineWidth) {
		this.strokeColor = strokeColor;
		this.lineWidth = lineWidth;
		this.center = new Point2D(0,0);
	}

	public abstract void draw(GraphicsContext gc);

	public Point2D getCenter() {
		return center;
	}

	public Color getStrokeColor() {
		return strokeColor;
	}

	public Point2D location() {
		return null;
	}

	public void move(Point2D newCenter) {

	}

	public void setCenter(Point2D center) {
		this.center = center;
	}

	public void setStrokeColor(Color color) {
		this.strokeColor = color;
	}

	public double getLineWidth() {
		return lineWidth;
	}
}