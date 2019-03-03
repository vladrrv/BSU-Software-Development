package sample.geometry;

/**
 * @author User
 * @version 1.0
 * @created 03-Mar-2019 3:07:23 PM
 */
public abstract class OpenShape extends Shape {

	private int lineStyle;

	public OpenShape(){

	}

	public int getLineStyle(){
		return 0;
	}

	/**
	 * 
	 * @param lineStyle
	 */
	public void setLineStyle(int lineStyle){

	}
}//end OpenShape