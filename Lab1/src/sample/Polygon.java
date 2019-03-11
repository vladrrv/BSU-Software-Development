package sample;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


import java.util.ArrayList;


public class Polygon extends ClosedShape {

	public static int getnPoints() {
		return -1;
	}

	public Polygon(ArrayList<Point2D> points, Color strokeColor, Color fillColor, double lineWidth){
		super(points, strokeColor, fillColor, lineWidth);
	}

	public void draw(GraphicsContext gc){
		int len = getPoints().size();
		double[] xPoly = new double[len];
		double[] yPoly = new double[len];
		double x_center = 0, y_center = 0;
		for (int i = 0; i < len; i++) {
			xPoly[i] = getPoints().get(i).getX();
			yPoly[i] = getPoints().get(i).getY();
			x_center += xPoly[i];
			y_center += yPoly[i];
		}
		setCenter(new Point2D(x_center/len, y_center/len));
		gc.setFill(getFillColor());
		gc.setStroke(getStrokeColor());
		gc.setLineWidth(getLineWidth());
		gc.setLineDashes();
		gc.fillPolygon(xPoly, yPoly, len);
		gc.strokePolygon(xPoly, yPoly, len);
	}
}