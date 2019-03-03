package sample.geometry;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public abstract class Shape {

	private Color strokeColor;
	private Point2D theCenter;

	public Shape() {
		this.strokeColor = new Color(0,0,0, 1);
		this.theCenter = new Point2D(0,0);
	}

	public Shape(Color strokeColor) {
		this.strokeColor = strokeColor;
		this.theCenter = new Point2D(0,0);
	}

	public abstract void draw(GraphicsContext gc);

	public Point2D getCenter(){
		return theCenter;
	}

	public Color getStrokeColor(){
		return strokeColor;
	}

	public Point2D location(){
		return null;
	}

	public void move(Point2D newCenter){

	}

	public void setCenter(Point2D center){
		this.theCenter = center;
	}

	public void setStrokeColor(Color color){
		this.strokeColor = color;
	}
}