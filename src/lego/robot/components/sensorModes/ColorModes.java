package lego.robot.components.sensorModes;

/**
 * Available modes for the sensor
 * @author Programming
 *
 */
public enum ColorModes {
	/**
	 * Color ID mode
	 */
	COLOR_ID,
	/**
	 * Reflected red light intensity mode
	 */
	RED,
	/**
	 * RGB color of a surface
	 */
	RGB,
	/**
	 * Ambient light intensity
	 * the sample fetched requires three fields
	 */
	AMBIENT;

}
