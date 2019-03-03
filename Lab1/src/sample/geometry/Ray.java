package sample.geometry;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;


public class Ray extends Segment {

	public Ray(){
		super();
	}

	public Ray(Point2D startPoint, Point2D endPoint) {
		super(startPoint, endPoint);
	}

	public Ray(Point2D startPoint, Point2D endPoint, Color strokeColor) {
		super(startPoint, endPoint, strokeColor);
	}

	public Ray(Point2D startPoint, Point2D endPoint, Color strokeColor, int lineStyle) {
		super(startPoint, endPoint, strokeColor, lineStyle);
	}
}