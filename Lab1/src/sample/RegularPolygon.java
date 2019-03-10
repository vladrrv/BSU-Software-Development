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

	private int nVertices = 3;
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
			points.add(p1);
			double r = center.distance(p1);
			Point2D v1 = new Point2D(center.getX() - r, center.getY() - r), v2 = new Point2D(center.getX() + r, center.getY() + r);
			ArrayList<Point2D> cPoints = new ArrayList<>();
			cPoints.add(v1);
			cPoints.add(v2);
			Circle circle = new Circle(cPoints, getStrokeColor(), getFillColor(), getLineWidth());
			circle.draw(gc);
			double k1 = (p1.getY() - center.getY())/(p1.getX() - center.getX());
			double theta = 2*Math.PI/nVertices, theta0 = Math.atan(-k1);
			double x, y;
			System.out.println("Point " + "0" + ": " + p1.getX() + " " + p1.getY() + "Theta: " + Math.toDegrees(theta0));
			for (int i = 1; i < nVertices; i++) {
				x = center.getX() + Math.cos(Math.toDegrees(theta*i + theta0))*r;
				y = center.getY() + Math.sin(Math.toDegrees(theta*i + theta0))*r;
				points.add(new Point2D(x,y));
				System.out.println("Point " + i + ": " + x + " " + y + "Theta: " + Math.toDegrees(theta*i + theta0));
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