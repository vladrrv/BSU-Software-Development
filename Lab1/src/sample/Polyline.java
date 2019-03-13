package sample;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Polyline extends OpenShape {

	public static int getnPoints() {
		return -1;
	}

	private ArrayList<Segment> segments;

	public Polyline(ArrayList<Point2D> points, Color strokeColor, double lineWidth, int lineStyle) {
		super(strokeColor, lineWidth, lineStyle);
		this.segments = new ArrayList<>();
		for (int i = 1; i < points.size(); ++i) {
			this.segments.add(new Segment(points.get(i-1), points.get(i), strokeColor, lineWidth, lineStyle));
		}
	}

	public void draw(GraphicsContext gc) {
		double x_center = 0, y_center = 0;
		for (Segment segment : segments) {
			x_center += segment.getStartPoint().getX();
			y_center += segment.getStartPoint().getY();
		}
		int len = segments.size();
		x_center += segments.get(len - 1).getEndPoint().getX();
		y_center += segments.get(len - 1).getEndPoint().getY();
		Point2D center = new Point2D(x_center/(len+1), y_center/(len+1));
		setCenter(center);
		for (Segment segment : segments) {
			segment.draw(gc);
			segment.setCenter(center);
		}
	}

	@Override
	public void move(Point2D newCenter) {
		for (Segment segment : segments) {
			segment.move(newCenter);
		}
	}

	public ArrayList<Segment> getSegments() {
		return segments;
	}

	public void setSegments(ArrayList<Segment> segments) {
		this.segments = segments;
	}
}