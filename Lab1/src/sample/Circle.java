package sample;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Circle extends Ellipse {

	public static int getnPoints() {
		return 2;
	}

	public Circle(ArrayList<Point2D> points, Color strokeColor, Color fillColor, double lineWidth) {
		super(points, strokeColor, fillColor, lineWidth);
	}

	@Override
	public void draw(GraphicsContext gc) {
		Point2D p1 = getPoints().get(0), p2 = getPoints().get(1);
		Point2D newPoint2;
		double r, x, y;
		if (p1.getX() <= p2.getX()) {
			if (p1.getY() <= p2.getY()) {
				r = p2.getY() - p1.getY();
				x = p1.getX() + r;
				y = p2.getY();
			} else {
				r = p2.getX() - p1.getX();
				x = p2.getX();
				y = p1.getY() - r;
			}
		}
		else {
			if (p1.getY() <= p2.getY()) {
					r = p2.getY() - p1.getY();
					x = p1.getX() - r;
					y = p2.getY();
			} else {
				r = -(p2.getX() - p1.getX());
				x = p2.getX();
				y = p1.getY() - r;
			}
		}
		newPoint2 = new Point2D(x,y);
		getPoints().set(1, newPoint2);
		super.draw(gc);
	}
}