package sample.geometry;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @version 1.0
 * @created 03-Mar-2019 3:07:24 PM
 */
public abstract class Shape {

	private Color strokeColor;
	private Point2D theCenter;

	public Shape(){

	}

	public abstract void draw(GraphicsContext gc);

	public Point2D getCenter(){
		return null;
	}

	public Color getStrokeColor(){
		return null;
	}

	public Point2D location(){
		return null;
	}

	/**
	 * 
	 * @param newCenter
	 */
	public void move(Point2D newCenter){

	}

	/**
	 * 
	 * @param center
	 */
	public void setCenter(Point2D center){

	}

	/**
	 * 
	 * @param color
	 */
	public void setStrokeColor(Color color){

	}
}//end Shape