package lego.robot.components.sensorModes;
/**
 * Possible modes for the IR sensor
 * @author Programming
 *
 */
public enum IRModes {

	/**
	 * Measuring distance mode
	 */
	IR_DISTANCE,
	/**
	 * Beacon mode
	 * the sample fetched requires eight fields
	 */
	IR_SEEK;
	
}
