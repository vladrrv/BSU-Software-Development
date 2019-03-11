package sample;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Rhombus extends Parallelogram {
	public static int getnPoints() {
		return 3;
	}
	public Rhombus(ArrayList<Point2D> points, Color strokeColor, Color fillColor, double lineWidth){
		super(points, strokeColor, fillColor, lineWidth);
	}

	@Override
	public void draw(GraphicsContext gc) {
		if (getPoints().size() == 3) {
			Point2D center = getPoints().get(0), p1 = getPoints().get(1), p2 = getPoints().get(2),
					newPoint2;
			//line between vertexes: y = k1x+b1
			double k1 = (p1.getY() - p2.getY())/(p1.getX() - p2.getX()), b1 = p1.getY() - k1*p1.getX();
			//line between vertex and center: y = k2x+b2
			double k2 = (p1.getY() - center.getY())/(p1.getX() - center.getX()), b2 = p1.getY() - k2*p1.getX();
			//normal to line between vertex and center at center: y - y1 = -1/k2(x-x1) y = -1/k2*x+y1+1/k2*x1 = k3x+b3;
			double k3 = -(double)1/k2, b3 = center.getY() + (double)1/k2*center.getX();
			//intersection point
			double x = (b3-b1)/(k1-k3), y = k1*x+b1;
			newPoint2 = new Point2D(x,y);
			ArrayList<Point2D> points = new ArrayList<>();
			points.add(center);
			points.add(p1);
			points.add(newPoint2);
			super.setPoints(points);
		}
		super.draw(gc);
	}
}