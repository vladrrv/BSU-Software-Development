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
public class Rectangle extends Parallelogram {

	public static int getnPoints() {
		return 2;
	}

	public Rectangle(ArrayList<Point2D> points, Color strokeColor, Color fillColor, double lineWidth){
		super(points, strokeColor, fillColor, lineWidth);
	}

	@Override
	public void draw(GraphicsContext gc) {
		if (getPoints().size() == 2) {
			Point2D center = getPoints().get(0), p1 = getPoints().get(1),
					p2 = new Point2D(center.getX() + (center.getX() - p1.getX()), p1.getY());
			ArrayList<Point2D> points = new ArrayList<>();
			points.add(center);
			points.add(p1);
			points.add(p2);
			super.setPoints(points);
		}
		super.draw(gc);
		/*double
				x = Double.min(p1.getX(), p2.getX()),
				y = Double.min(p1.getY(), p2.getY()),
				dx = p1.getX() - p2.getX(), w = Double.max(dx, -dx),
				dy = p1.getY() - p2.getY(), h = Double.max(dy, -dy),
				lw = getLineWidth();
		gc.setFill(getFillColor());
		gc.setStroke(getStrokeColor());
		gc.setLineWidth(lw);
		gc.fillRect(x, y, w, h);
		gc.strokeRect(x, y, w, h);*/
	}
}//end Rectangle