package sample;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Polyline extends OpenShape {

	private ArrayList<Segment> segments;

	public Polyline(ArrayList<Point2D> points) {
		this.segments = new ArrayList<>();
		for (int i = 1; i < points.size(); ++i) {
			this.segments.add(new Segment(points.get(i-1), points.get(i)));
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