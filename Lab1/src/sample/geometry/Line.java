package sample.geometry;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Line extends Segment {

	public Line() {
		super();
	}

	public Line(Point2D startPoint, Point2D endPoint) {
		super(startPoint, endPoint);
	}

	public Line(Point2D startPoint, Point2D endPoint, Color strokeColor) {
		super(startPoint, endPoint, strokeColor);
	}

	public Line(Point2D startPoint, Point2D endPoint, Color strokeColor, int lineStyle) {
		super(startPoint, endPoint, strokeColor, lineStyle);
	}

	@Override
	public void draw(GraphicsContext gc) {
		super.draw(gc);
	}
}