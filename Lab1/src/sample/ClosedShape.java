package sample;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * @version 1.0
 * @created 03-Mar-2019 3:07:23 PM
 */
public abstract class ClosedShape extends Shape {

	private Color fillColor;
	private ArrayList<Point2D> points;

	public ClosedShape(){

	}

	public Color getColor(){
		return null;
	}

	public ArrayList<Point2D> getPoints(){
		return null;
	}

	/**
	 * 
	 * @param color
	 */
	public void setColor(Color color){

	}

	/**
	 * 
	 * @param points
	 */
	public void setPoints(ArrayList<Point2D> points){

	}
}//end ClosedShape