package sample.geometry;

import javafx.scene.paint.Color;


public abstract class OpenShape extends Shape {

	protected enum LineStyle {
		SOLID(0), DASH(1), DOT(2);
		double[] dashes;
		LineStyle(int v) {
			switch (v) {
				case 0: dashes = new double[]{}; break;
				case 1: dashes = new double[]{4, 4}; break;
				case 2: dashes = new double[]{1, 4}; break;
			}
		}
	}

	private LineStyle lineStyle;


	public OpenShape(){
		super();
		this.lineStyle = LineStyle.SOLID;
	}

	public OpenShape(Color strokeColor) {
		super(strokeColor);
		this.lineStyle = LineStyle.SOLID;
	}

	public OpenShape(Color strokeColor, double lineWidth) {
		super(strokeColor, lineWidth);
	}

	public OpenShape(Color strokeColor, int lineStyle) {
		super(strokeColor);
		this.lineStyle = LineStyle.values()[lineStyle];
	}

	public OpenShape(Color strokeColor, double lineWidth, int lineStyle) {
		super(strokeColor, lineWidth);
		this.lineStyle = LineStyle.values()[lineStyle];
	}

	public int getLineStyle(){
		return lineStyle.ordinal();
	}

	double[] getLineDashes() {
		return lineStyle.dashes;
	}

	public void setLineStyle(int lineStyle){
		this.lineStyle = LineStyle.values()[lineStyle];
	}
}