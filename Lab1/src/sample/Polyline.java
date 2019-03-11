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
		for (Segment segment : segments) {
			segment.draw(gc);
		}
	}

	public ArrayList<Segment> getSegments() {
		return segments;
	}

	public void setSegments(ArrayList<Segment> segments) {
		this.segments = segments;
	}
}