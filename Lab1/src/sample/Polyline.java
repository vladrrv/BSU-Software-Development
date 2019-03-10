package sample;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Polyline extends OpenShape {

	private ArrayList<Segment> segments;

	public Polyline() {

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