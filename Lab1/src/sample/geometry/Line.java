package sample.geometry;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Line extends Segment {

	public Line() {
		super();
	}

	public Line(Point2D startPoint, Point2D endPoint) {
		super(startPoint, endPoint);
	}

	public Line(Point2D startPoint, Point2D endPoint, Color strokeColor) {
		super(startPoint, endPoint, strokeColor);
	}

	public Line(Point2D startPoint, Point2D endPoint, Color strokeColor, int lineStyle) {
		super(startPoint, endPoint, strokeColor, lineStyle);
	}

	public Line(Point2D startPoint, Point2D endPoint, Color strokeColor, double lineWidth, int lineStyle) {
		super(startPoint, endPoint, strokeColor, lineWidth, lineStyle);
	}

	@Override
	public void draw(GraphicsContext gc) {
		Point2D oldStart = this.getStartPoint(),
				oldEnd = this.getEndPoint(),
				newStart,
				newEnd;
		double w = gc.getCanvas().getWidth(), h = gc.getCanvas().getHeight(),
				ox1 = oldStart.getX(), oy1 = oldStart.getY(),
				ox2 = oldEnd.getX(), oy2 = oldEnd.getY();
		if (ox1 == ox2) {
			newStart = new Point2D(ox1, 0);
			newEnd = new Point2D(ox1, h);
		} else if (oy1 == oy2) {
			newStart = new Point2D(0, oy1);
			newEnd = new Point2D(w, oy1);
		} else {
			double k = (oy2 - oy1)/(ox2 - ox1),
					x1 = 0,
					y1 = oy1 + k*(x1-ox1),
					y2 = 0,
					x2 = ox2 + 1.0/k*(y2-oy2);
			if (y1 >= h || y1 < 0) {
				y1 = h;
				x1 = ox1 + 1.0/k*(y1-oy1);
			}
			if (x2 >= w || x2 < 0) {
				x2 = w;
				y2 = oy2 + k*(x2-ox2);
			}
			newStart = new Point2D(x1, y1);
			newEnd = new Point2D(x2, y2);
		}

		this.setStartPoint(newStart);
		this.setEndPoint(newEnd);

		super.draw(gc);

		this.setStartPoint(oldStart);
		this.setEndPoint(oldEnd);
	}
}