package sample;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Ellipse extends ClosedShape {

	public static int getnPoints() {
		return 2;
	}

	public Ellipse(ArrayList<Point2D> points, Color strokeColor, Color fillColor, double lineWidth) {
		super(points, strokeColor, fillColor, lineWidth);
	}

	public void draw(GraphicsContext gc) {
		Point2D p1 = getPoints().get(0), p2 = getPoints().get(1);
		double
				x = Double.min(p1.getX(), p2.getX()),
				y = Double.min(p1.getY(), p2.getY()),
				dx = p1.getX() - p2.getX(), w = Double.max(dx, -dx),
				dy = p1.getY() - p2.getY(), h = Double.max(dy, -dy),
				lw = getLineWidth();
		setCenter(new Point2D((p1.getX() + p2.getX())/2, (p1.getY() + p2.getY())/2));
		gc.setFill(getFillColor());
		gc.setStroke(getStrokeColor());
		gc.setLineWidth(lw);
		gc.setLineDashes();
		gc.fillOval(x, y, w, h);
		if (lw > 0) gc.strokeOval(x, y, w, h);
	}
}