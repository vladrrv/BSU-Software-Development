package sample;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * @author User
 * @version 1.0
 * @created 03-Mar-2019 3:07:23 PM
 */
public class Parallelogram extends Polygon {
	public static int getnPoints() {
		return 3;
	}
	public Parallelogram(ArrayList<Point2D> points, Color strokeColor, Color fillColor, double lineWidth){
		super(points, strokeColor, fillColor, lineWidth);
	}
	public void draw(GraphicsContext gc) {
		if (getPoints().size() == 3) {
			Point2D center = getPoints().get(0), p1 = getPoints().get(1), p2 = getPoints().get(2),
					p3 = new Point2D(center.getX() + (center.getX() - p1.getX()), center.getY() + (center.getY() - p1.getY())),
					p4 = new Point2D(center.getX() + (center.getX() - p2.getX()), center.getY() + (center.getY() - p2.getY()));
			ArrayList<Point2D> points = new ArrayList<>();
			points.add(p1);
			points.add(p2);
			points.add(p3);
			points.add(p4);
			super.setPoints(points);
		}
		//super.draw(gc);
		int len = 4;
		double[] xPoly = new double[len];
		double[] yPoly = new double[len];
		for (int i = 0; i < len; i++) {
			xPoly[i] = getPoints().get(i).getX();
			yPoly[i] = getPoints().get(i).getY();
		}
		gc.setFill(getFillColor());
		gc.setStroke(getStrokeColor());
		gc.setLineWidth(getLineWidth());
		gc.strokePolygon(xPoly, yPoly, len);
	}

}//end Parallelogram