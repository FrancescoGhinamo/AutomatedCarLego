package lego.robot;

import lego.robot.components.BrickButton;
import lego.robot.components.Sensor;
import lego.robot.components.TRMotor;
import lego.robot.components.Transmission;
import lego.robot.components.movements.Verse;
import lego.robot.exceptions.UnsupportedOptionException;

/**
 * Extension of LegoRobot to define a turtle robot
 * The listener for the sensors must be implemented in this or in another auxiliary class
 * @author Programming
 *
 */
public class TurtleRobot extends LegoRobot {
	
	/**
	 * Maximum number of sensor available for the robot
	 */
	private static final int SENS_NUM = 4;
	/**
	 * Maximum number of auxiliary motors
	 */
	private static final int AUX_MOT_NUM = 2;
	/**
	 * Maximum number of buttons
	 */
	private static final int MAX_BUTTONS = 6;
	/**
	 * Fixed distance between wheels in inches
	 */
	private static final float TRACK_WIDTH_CM = 11.5f;
	/**
	 * Diameter of the wheel
	 */
	private static final float WHEEL_DIAMETER_CM = 5.6f;
	
	/**
	 * Transmission of the turtle robot
	 */
	private Transmission transmission;
	/**
	 * Array of sensor available for the robot
	 */
	private Sensor[] sensorArray;
	/**
	 * Array of auxiliary motors
	 */
	private TRMotor [] auxiliaryMotorArray;
	/**
	 * Array of buttons used
	 */
	private BrickButton[] buttonArray;

	/**
	 * Constructor of the TurtleRobot
	 * @param transmission: specific Transmission for the robot
	 * @param installedSensors: specific sensors installed on the robot
	 * @param auxiliaryMotorArray: auxiliary motors installed
	 * @param buttonArray: initialized buttons of the brick
	 */
	public TurtleRobot(Transmission transmission, Sensor[] installedSensors, TRMotor[] auxiliaryMotorArray, BrickButton[] buttonArray) {
		this.transmission = transmission;
		this.sensorArray = installedSensors;
		this.auxiliaryMotorArray  =auxiliaryMotorArray;
		this.buttonArray = buttonArray;
		
		startSensors();
		startButtons();
	}
	
	/**
	 * Starts sensors monitoring for each sensor
	 */
	private void startSensors() {
		for(int i = 0; i < SENS_NUM; i++) {
			if(sensorArray[i] != null) {
				sensorArray[i].start();
			}
		}
	}
	
	/**
	 * Starts brick buttons monitoring for each button
	 */
	private void startButtons() {
		for(int i = 0; i < MAX_BUTTONS; i++) {
			if(buttonArray[i] != null) {
				buttonArray[i].start();
			}
		}
	}
	
	/**
	 * The robot start moving in the specified verse
	 * @param d: verse of movement
	 */
	public void move(Verse d) {
		try {
			transmission.move(d);
		} catch (UnsupportedOptionException e) {
			System.out.println(e);
		}
	}
		
	/**
	 * The robot moves for a given number of milliseconds
	 * @param milliseconds: number of milliseconds of moving
	 * @param d: verse of the movement
	 */
	public void moveOnTime(long milliseconds, Verse d) {
		try {
			transmission.move(d);
		} catch (UnsupportedOptionException e1) {
			System.out.println(e1);
		}
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			System.out.println(e);
		}
		transmission.stop();
	}
	
	
	/**
	 * The robot moves for a given distance in cm
	 * @param distanceCm: length of the movement
	 */
	public void moveOnDistance(float distanceCm) {
		transmission.moveOnDistance(distanceCm);
	}
	
	/**
	 * The robot moves for a given distance in cm, slowing down at the end of the movement
	 * @param distanceCm: length of the movement
	 */
	public void moveOnDistanceSlowingDown(float distanceCm) {
//		TRMotor motorTest = new TRMotor(MotorPort.PORT_B);
//		motorTest.resetTachoCount();
		moveOnDistance(distanceCm);
		while(getLinearSpeed() > 5) {
			if(distanceCm - getTraveledDistence() < getLinearSpeed()) {
				setLinearSpeed(distanceCm - getTraveledDistence());
			}
		}
		
	}
	
	
	/**
	 * Gets the traveled distance of the robot on a linear path, you must reset motor's tacho count
	 * before you start the movement if you want to use this method
	 * @return traveled distance
	 */
	@SuppressWarnings("deprecation")
	public double getTraveledDistence() {
		return this.transmission.getPilot().getMovement().getDistanceTraveled();
	}
	
	/**
	 * Starts the robot moving forward along a curved path.
	 * 
	 * A positive value means that center of the turn is on the left. If the robot is traveling toward the top of the page the arc looks like this: ). 
	 * A negative value means that center of the turn is on the right so the arc looks this: (. 
	 * @param turnRate: The turnRate specifies the sharpness of the turn. Use values between -200 and +200.
	 */
	public void steer(double turnRate) {
		transmission.steer(turnRate);
	}
	
	/**
	 * Makes the robot move on a spiral path
	 * @param loR: this parameter specifies if the robot has tu turn left or right when doing the spiral
	 */
	public void spiral(Verse loR) throws UnsupportedOptionException {
		switch(loR) {
		case LEFT:
			for(int i = 0; i <= 200; i++) {
				steer(i);
			}
			break;
			
		case RIGHT:
			for(int i = 0; i <= 200; i++) {
				steer(-i);
			}
			break;
		default:
			throw new UnsupportedOptionException();
		
		}
	}
	
	/**
	 * The robot turns for a specific number of degrees
	 * Wheels turn in opposite directions producing a zero radius turn
	 * @param degrees: positive angle rotate left (anti-clockwise), negative right
	 * @param immediateReturn: if true this method returns immediately
	 */
	public void turnOnDegrees(float degrees, boolean immediateReturn) {
		transmission.turnOnDegrees(degrees, immediateReturn);
	}
	
	
	/**
	 * The robot turns for a specified number of degrees with a specified radius
	 * @param degrees: degrees of rotation
	 * @param radius: radius of the curve in cm
	 * @param backOrFwd: verse of movement
	 * @param leftOrRight: direction of the turn
	 */
	public void turnOnDegreesAndRadius(float degrees, float radius, Verse backOrFwd, Verse leftOrRight) {
		try {
			transmission.turnOnDegreesAndRadius(degrees, radius, backOrFwd, leftOrRight);
		} catch (UnsupportedOptionException e) {
			System.out.println(e);
		}
	}
	
	/**
	 * The robot turns on an arc - shaped path with a specified radius
	 * @param radius: radius of the curve in cm
	 * @param backOrFwd: verse of movement
	 * @param leftOrRight: direction of the turn
	 */
	public void arc(float radiusCm, Verse backOrFwd, Verse leftOrRight) {
		try {
			transmission.arc(radiusCm, backOrFwd, leftOrRight);
		} catch (UnsupportedOptionException e) {
			System.out.println(e);
		}
	}
	
	
	/**
	 * The robot moves until one button of the brick is pressed
	 * @param d: verse of the movement
	 */
	public void moveUntilButtonBrkPress(Verse d) {
		try {
			transmission.moveUntilButtonBrkPress(d);
		} catch (UnsupportedOptionException e) {
			System.out.println(e);
		}
		
	}
	
	/**
	 * The robot stops moving
	 */
	public void stop() {
		transmission.stop();
	}
	
	
	/**
	 * The method waits for the action of the transmission is completed
	 */
	public void waitCompleteAction() {
		transmission.waitCompleteAction();
	}
	
	
	/**
	 * Sets the linear speed of the robot
	 * @param speed: speed in cm/s
	 */
	public void setLinearSpeed(double speed) {
		transmission.setLinearSpeed(speed);
	}
	
	/**
	 * Sets the linear acceleration of the robot
	 * @param acc: speed in cm/s^2
	 */
	public void setLinearAcceleration(double acc) {
		transmission.setLinearSpeed(acc);
	}
	/**
	 * Sets rotation speed of the robot
	 * @param speed: speed in degrees / sec
	 */
	public void setAngularSpeed(double speed) {
		transmission.setAngularSpeed(speed);
	}
	
	/**
	 * Returns the linear speed of the robot
	 * @return Returns the linear speed of the robot
	 */
	public double getLinearSpeed() {
		return transmission.getLinearSpeed();
	}
	/**
	 * Returns the linear acceleration of the robot
	 * @return Returns the linear acceleration of the robot
	 */
	public double getLinearAcceleration() {
		return transmission.getLinearAcceleration();
	}
	
	/**
	 * Return the rotation speed of the robot
	 * @return Return the rotation speed of the robot in degrees / sec
	 */
	public double getAngularSpeed() {
		return transmission.getAngularSpeed();
	}
	
	/**
	 * Sets the transmission to the robot
	 * @param _t: new transmission
	 */
	protected void setTransmission(Transmission _t) {
		this.transmission = _t;
	}


	/**
	 * Return the distance between the wheels
	 * @return Return the distance between the wheels
	 */
	public static float getTrackWidthCm() {
		return TRACK_WIDTH_CM;
	}
	/**
	 * Returns the diameter of the wheels
	 * @return Returns the diameter of the wheels
	 */
	public static float getWheelDiameterCm() {
		return WHEEL_DIAMETER_CM;
	}




	/**
	 * Returns the constant number of sensors
	 * @return Returns the constant number of sensors
	 */
	public static int getSensNum() {
		return SENS_NUM;
	}

	
	/**
	 * Returns the constant number of auxiliary motors
	 * @return Returns the constant number of auxiliary motors
	 */
	public static int getAuxMotNum() {
		return AUX_MOT_NUM;
	}
	
	/**
	 * Returns the constant number of buttons
	 * @return Returns the constant number of buttons
	 */
	public static int getMaxButtons() {
		return MAX_BUTTONS;
	}

	/**
	 * Returns the transmission group of the robot
	 * @return Returns the transmission group of the robot
	 */
	public Transmission getTransmission() {
		return transmission;
	}


	/**
	 * Returns the list of sensors
	 * @return Returns the list of sensors
	 */
	public Sensor[] getSensorArray() {
		return sensorArray;
	}
	
	/**
	 * Returns the list of auxiliary motors
	 * @return Returns the list of auxiliary motors
	 */
	public TRMotor[] getAuxiliaryMotorArray() {
		return auxiliaryMotorArray;
	}

	/**
	 * Returns the list of buttons
	 * @return Returns the list of buttons
	 */
	public BrickButton[] getButtonArray() {
		return buttonArray;
	}
	
	

	
	
	

	
}
