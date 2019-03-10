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
public class Polygon extends ClosedShape {

	/*public static int getnPoints() {
		return getPoints().size();
	}*/
	public Polygon(ArrayList<Point2D> points, Color strokeColor, Color fillColor, double lineWidth){
		super(points, strokeColor, fillColor, lineWidth);
	}


	public void draw(GraphicsContext gc){
		int len = getPoints().size();
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
}//end Polygon