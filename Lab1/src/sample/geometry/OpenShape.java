package sample.geometry;

import javafx.scene.paint.Color;


public abstract class OpenShape extends Shape {

	private int lineStyle;

	public OpenShape(){
		super();
		this.lineStyle = 0;
	}

	public OpenShape(Color strokeColor) {
		super(strokeColor);
		this.lineStyle = 0;
	}

	public OpenShape(Color strokeColor, int lineStyle) {
		super(strokeColor);
		this.lineStyle = lineStyle;
	}

	public int getLineStyle(){
		return lineStyle;
	}

	public void setLineStyle(int lineStyle){
		this.lineStyle = lineStyle;
	}
}