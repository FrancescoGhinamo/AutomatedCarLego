package lego.robot.components;

import lego.robot.components.movements.Verse;
import lego.robot.exceptions.UnsupportedOptionException;
import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.robotics.navigation.DifferentialPilot;

/**
 * Transmission group of the robot
 * @author Programming
 *
 */
@SuppressWarnings("deprecation")
public class Transmission {


	/**
	 * DifferentialPilot
	 */
	
	private DifferentialPilot pilot;
	/**
	 * Conversation factor between inches and centimeters
	 */
	private static final float IN_TO_CM = 2.54f;
	/**
	 * Width of the track of the robot in inches
	 */
	private float trackWidthIn;
	/**
	 * Diameter of the wheels in inches
	 */
	@SuppressWarnings("unused")
	private float wheelDiameterIn;



	/**
	 * Constructor of the transmission with default motor options
	 * @param trackWidthCm: distance between wheels in cm
	 */
	public Transmission(float wheelDiameter, float trackWidthCm) {
		pilot = new DifferentialPilot(wheelDiameter / IN_TO_CM, trackWidthCm / IN_TO_CM, Motor.B, Motor.C, false);
		trackWidthIn = trackWidthCm / IN_TO_CM;
		wheelDiameterIn = wheelDiameter / IN_TO_CM;
		
	}

	
	/**
	 * The method waits for the action of the transmission is completed
	 */
	public void waitCompleteAction() {
		do {
			//waits
		}
		while(pilot.isMoving());
	}


	/**
	 * The robot starts moving in the specified verse
	 * @param d: verse of the movement
	 * 
	 */
	public void move(Verse d) throws UnsupportedOptionException {
		switch(d) {
		case FORWARD:
			pilot.forward();
			break;

		case BACKWARD:
			pilot.backward();
			break;
		default:
			throw new UnsupportedOptionException();

		}

	}

	/**
	 * The robot stops moving
	 */
	public void stop() {
		pilot.stop();
	}
	
	/**
	 * Starts the robot moving forward along a curved path.
	 * 
	 * A positive value means that center of the turn is on the left. If the robot is traveling toward the top of the page the arc looks like this: ). 
	 * A negative value means that center of the turn is on the right so the arc looks this: (. 
	 * @param turnRate: The turnRate specifies the sharpness of the turn. Use values between -200 and +200.
	 */
	public void steer(double turnRate) {
		pilot.steer(turnRate);
	}

	/**
	 * The robot turns for a specific number of degrees
	 * Wheels turn in opposite directions producing a zero radius turn
	 * @param degrees: positive angle rotate left (anti-clockwise), negative right
	 * @param immediateReturn: if true this method returns immediately
	 */
	public void turnOnDegrees(float degrees, boolean immediateReturn) {
		pilot.rotate(degrees, immediateReturn);
	}


	/**
	 * The robot turns for a specified number of degrees with a specified radius
	 * @param degrees: degrees of rotation
	 * @param radius: radius of the curve in cm
	 * @param backOrFwd: verse of movement
	 * @param leftOrRight: direction of the turn
	 */
	public void turnOnDegreesAndRadius(float degrees, float radiusCm, Verse backOrFwd, Verse leftOrRight) throws UnsupportedOptionException {
		degrees = Math.abs(degrees);
		radiusCm /= IN_TO_CM;
		switch(backOrFwd) {
		case FORWARD:
			switch(leftOrRight) {
			case RIGHT:
				pilot.arc(-1 * radiusCm, degrees);
				break;
				
			case LEFT:
				pilot.arc(radiusCm, degrees);
				break;
			default:
				throw new UnsupportedOptionException();
				
			}
			
			break;

		case BACKWARD:
			switch(leftOrRight) {
			case RIGHT:
				pilot.arc(-1 * radiusCm, -1 * degrees);
				break;
				
			case LEFT:
				pilot.arc(radiusCm, -1 * degrees);
				break;
			default:
				throw new UnsupportedOptionException();
			}
		default:
			throw new UnsupportedOptionException();
			

		}
	}

	/**
	 * The robot turns on an arc - shaped path with a specified radius
	 * @param radius: radius of the curve in cm
	 * @param backOrFwd: verse of movement
	 * @param leftOrRight: direction of the turn
	 */
	public void arc(float radiusCm, Verse backOrFwd, Verse leftOrRight) throws UnsupportedOptionException {
		radiusCm /= IN_TO_CM;
		switch(backOrFwd) {
		case BACKWARD:
			switch(leftOrRight) {
			case LEFT:
				pilot.arcBackward(radiusCm);
				break;
			case RIGHT:
				pilot.arcBackward(-radiusCm);
				break;
			default:
				throw new UnsupportedOptionException();
			}
			
			break;
			
		case FORWARD:
			switch(leftOrRight) {
			case LEFT:
				pilot.arcForward(radiusCm);
				break;
			case RIGHT:
				pilot.arcForward(-radiusCm);
				break;
			default:
				throw new UnsupportedOptionException();
			}
			
			break;
		
		default:
			throw new UnsupportedOptionException();
		}
	}



	/**
	 * The robot moves until one button of the brick is pressed
	 * @param d: verse forward or backward
	 */
	public void moveUntilButtonBrkPress(Verse d) throws UnsupportedOptionException {
		switch(d) {
		case FORWARD:
			pilot.forward();
			break;

		case BACKWARD:
			pilot.backward();
			break;
		default:
			throw new UnsupportedOptionException();
			
		}
		Button.waitForAnyPress();
		pilot.stop();
		

	}



	/**
	 * The robot for a given distance
	 * @param distanceCm: chosen distance in cm
	 */
	public void moveOnDistance(float distanceCm) {
		distanceCm /= IN_TO_CM;
		pilot.travel(distanceCm);		
	}

	/**
	 * Sets the linear speed of the robot
	 * @param speed: speed in cm/s
	 */
	public void setLinearSpeed(double speed) {
		pilot.setLinearSpeed(speed / IN_TO_CM);
	}
	
	/**
	 * Sets the linear acceleration of the robot
	 * @param acc: speed in cm/s^2
	 */
	public void setLinearAcceleration(double acc) {
		pilot.setLinearSpeed(acc / IN_TO_CM);
	}
	
	/**
	 * Sets rotation speed of the robot
	 * @param speed: speed in degrees / sec
	 */
	public void setAngularSpeed(double speed) {
		pilot.setAngularSpeed(speed);
	}
	
	/**
	 * Returns the linear speed of the robot
	 * @return Returns the linear speed of the robot
	 */
	public double getLinearSpeed() {
		return pilot.getLinearSpeed() * IN_TO_CM;
	}
	/**
	 * Returns the linear acceleration of the robot
	 * @return Returns the linear acceleration of the robot
	 */
	public double getLinearAcceleration() {
		return pilot.getLinearAcceleration() * IN_TO_CM;
	}

	/**
	 * Return the rotation speed of the robot
	 * @return Return the rotation speed of the robot in degrees / sec
	 */
	public double getAngularSpeed() {
		return pilot.getAngularSpeed();
	}
	/**
	 * Returns the DifferentialPilot of the transmission
	 * @return Returns the DifferentialPilot of the transmission
	 */
	public DifferentialPilot getPilot() {
		return pilot;
	}

	/**
	 * Return the distance between wheels in inches
	 * @return Return the distance between wheels in inches
	 */
	public float getTrackWidthIn() {
		return trackWidthIn;
	}
	



}
