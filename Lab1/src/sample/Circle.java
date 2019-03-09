package sample;


import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Circle extends Ellipse {

	public Circle(ArrayList<Point2D> points, Color strokeColor, Color fillColor, double lineWidth) {
		super(points, strokeColor, fillColor, lineWidth);
	}
}