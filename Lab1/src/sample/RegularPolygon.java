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
public class RegularPolygon extends Polygon {

	private int nVertices = 12;
	public static int getnPoints() {
		return 2;
	}
	public RegularPolygon(ArrayList<Point2D> points, Color strokeColor, Color fillColor, double lineWidth){
		super(points, strokeColor, fillColor, lineWidth);
	}

	@Override
	public void draw(GraphicsContext gc) {
		if (getPoints().size() == 2) {
			Point2D center = getPoints().get(0), p1 = getPoints().get(1);
			ArrayList<Point2D> points = new ArrayList<>();
			double r = center.distance(p1);
			double sin0 = (center.getY() - p1.getY())/r;
			double cos0 = (p1.getX() - center.getX())/r;
			double theta = 2*Math.PI/nVertices, theta0 = Math.atan(sin0/cos0);
			if (cos0 < 0)
				theta0 += Math.PI;
			double x, y;
			for (int i = 0; i < nVertices; i++) {
				x = center.getX() + Math.cos(theta * i + theta0) * r;
				y = center.getY() - Math.sin(theta * i + theta0) * r;
				points.add(new Point2D(x,y));
			}
			setPoints(points);
		}
		super.draw(gc);
	}

	public int getNVertices(){
		return nVertices;
	}

	/**
	 * 
	 * @param nVertices
	 */
	public void setNVertices(int nVertices){

	}
}//end RegularPolygon