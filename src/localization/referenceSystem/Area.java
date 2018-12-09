package localization.referenceSystem;

import localization.configuration.Units;

/**
 * One of the pieces in which the surface around the robot is divided into
 * @author Programming
 *
 */
public class Area implements Units {
	
	/**
	 * Length of a unit of measurement for the area
	 */
	private static double unitLengthCm = UNIT_LENGTH_CM	; 

	/**
	 * x coordinate of the area
	 */
	private double xCoord;
	/**
	 * x coordinate of the area
	 */
	private double yCoord;
	
	/**
	 * Default constructor: x and y are 0
	 */
	public Area() {
		xCoord = 0;
		yCoord = 0;
	}
	
	
	
	/**
	 * Constructor from given coordinates of the wanted area
	 * @param x: x axis of the area
	 * @param y: y axis of the area
	 */
	public Area(double x, double y) {
		xCoord = x;
		yCoord = y;
	}


	
	/**
	 * @return the unitLengthCm
	 */
	public static double getUnitLengthCm() {
		return unitLengthCm;
	}

	/**
	 * @return the xCoord
	 */
	public double getxCoord() {
		return xCoord;
	}

	/**
	 * @return the yCoord
	 */
	public double getyCoord() {
		return yCoord;
	}
	
	/**
	 * Equals method to test if to instances of Area refer to the same place
	 * @param obj: second area to test
	 * @return true: both Areas refer to the same position, false: they don't
	 */
	public boolean equals(Object obj) {
		return ((Area) obj).getxCoord() == xCoord && ((Area) obj).getyCoord() == yCoord ? true : false;
	}
	
	
	
	
	
	
	
}
