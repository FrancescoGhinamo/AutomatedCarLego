package automatedDrive.configuration;

/**
 * Interface containing parameters for movements
 * @author Programming
 *
 */
public interface MovementParameters {

	/**
	 * Value to make the robot steer right on its center
	 */
	static final int STEER_RIGHT = -200;
	/**
	 * Value to make the robot steer left on its center
	 */
	static final int STEER_LEFT = 200;
	/**
	 * Angular speed of the robot for rotations in degrees / sec
	 */
	static final double ANGULAR_SPEED = 5;
	/**
	 * Speed for a single motor
	 */
	static final double MOTOR_SPEED = 20;
	
	/**
	 * Linear speed of the robot in cm / s
	 */
	static final double LINEAR_SPEED = 40;
	/**
	 * Linear acceleration of the robot in cm / s^2
	 */
	static final double LINEAR_ACCELERATION = 2;
	/**
	 * Tolerance set to the heading in degrees
	 */
	static final float HEADING_TOLERANCE = 1f;
	/**
	 * Minimum distance the robot can reach when is approaching an object
	 */
	static final int MIN_DISTANCE = 40;
}
