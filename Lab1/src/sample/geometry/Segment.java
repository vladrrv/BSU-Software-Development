package sample.geometry;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;


public class Segment extends OpenShape {

	private Point2D startPoint;
	private Point2D endPoint;

	public Segment(){
		this.endPoint = new Point2D(0,0);
		this.startPoint = new Point2D(0,0);
	}

	public Segment(Point2D startPoint, Point2D endPoint) {
		super();
		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}

	public Segment(Point2D startPoint, Point2D endPoint, Color strokeColor) {
		super(strokeColor);
		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}

	public Segment(Point2D startPoint, Point2D endPoint, Color strokeColor, int lineStyle) {
		super(strokeColor, lineStyle);
		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}

	public Segment(Point2D startPoint, Point2D endPoint, Color strokeColor, double lineWidth, int lineStyle) {
		super(strokeColor, lineWidth, lineStyle);
		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}

	public void draw(GraphicsContext gc) {
		gc.setStroke(this.getStrokeColor());
		gc.setLineWidth(this.getLineWidth());
		gc.setLineDashes(this.getLineDashes());
		gc.strokeLine(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
	}

	public Point2D getStartPoint(){
		return startPoint;
	}
	public Point2D getEndPoint(){
		return endPoint;
	}

	public void setStartPoint(Point2D startPoint) {
		this.startPoint = startPoint;
	}
	public void setEndPoint(Point2D endPoint) {
		this.endPoint = endPoint;
	}

}