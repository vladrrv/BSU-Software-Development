package sample;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public abstract class ClosedShape extends Shape {

	private Color fillColor;
	private ArrayList<Point2D> points;

	public ClosedShape() {
		super();
		this.fillColor = Color.WHITE;
		this.points = new ArrayList<>();
	}

	public ClosedShape(ArrayList<Point2D> points, Color strokeColor, Color fillColor, double lineWidth) {
		super(strokeColor, lineWidth);
		this.fillColor = fillColor;
		this.points = new ArrayList<>();
		this.points.addAll(points);
	}

	public Color getFillColor() {
		return fillColor;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	public ArrayList<Point2D> getPoints() {
		return points;
	}

	public void setPoints(ArrayList<Point2D> points) {
		this.points = points;
	}
}