package sample;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Segment extends OpenShape {

	public static int getnPoints() {
		return 2;
	}

	private Point2D startPoint;
	private Point2D endPoint;

	public Segment() {
		this.endPoint = Point2D.ZERO;
		this.startPoint = Point2D.ZERO;
		this.setCenter(Point2D.ZERO);
	}

	public Segment(Point2D startPoint, Point2D endPoint, Color strokeColor, double lineWidth, int lineStyle) {
		super(strokeColor, lineWidth, lineStyle);
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.setCenter(endPoint.add(startPoint).multiply(0.5));
	}

	public void draw(GraphicsContext gc) {
		gc.setStroke(this.getStrokeColor());
		gc.setLineWidth(this.getLineWidth());
		gc.setLineDashes(this.getLineDashes());
		gc.strokeLine(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
	}

	@Override
	public void move(Point2D newCenter) {
		Point2D oldCenter = getCenter(), sp = getStartPoint(), ep = getEndPoint();
		double dx = newCenter.getX() - oldCenter.getX(), dy = newCenter.getY() - oldCenter.getY();
		setStartPoint(new Point2D(sp.getX()+dx, sp.getY()+dy));
		setEndPoint(new Point2D(ep.getX()+dx, ep.getY()+dy));
		setCenter(newCenter);
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