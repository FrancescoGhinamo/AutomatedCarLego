package lego.robot.components;

import lego.robot.GUI.StatusBar;
import lego.robot.components.movements.RotationVerse;
import lego.robot.interfaces.EV3HardwareInfo;
import lejos.hardware.motor.Motor;

/**
 * Single EV3 motor connected to port A, B, C or D
 * @author Programming
 *
 */
public class TRMotor implements EV3HardwareInfo {
	/**
	 * Number of degrees in a circle
	 */
	private static final int DEG_IN_CIRC = 360;

	/**
	 * Port of the robot where the motor is connected
	 */
	private MotorPort port;
	
	
	
	/**
	 * Constructor of the motor, give the side where it's placed
	 * @param port: side of the robot
	 */
	public TRMotor(MotorPort port) {
		StatusBar bar = StatusBar.getStatusBar();
		bar.increaseProgress(100 / INSTANCE_ELEMENTS);
		
//		System.out.println(" Creating motor\n");
		this.port = port;
	}
	
	/**
	 * The motor starts rotating in the specified verse
	 * @param v: verse of rotation
	 */
	public void rotate(RotationVerse v) {
		
		switch(port) {
		case PORT_A:
			switch(v) {
			case CLOCKWISE:
				Motor.A.forward();
				break;
				
			case COUNTERCLOCKWISE:
				Motor.A.backward();
				break;
			}
			break;
			
		case PORT_B:
			switch(v) {
			case CLOCKWISE:
				Motor.B.forward();
				break;
				
			case COUNTERCLOCKWISE:
				Motor.B.backward();
				break;
			}
			
			break;
			
		case PORT_C:
			switch(v) {
			case CLOCKWISE:
				Motor.C.forward();
				break;
				
			case COUNTERCLOCKWISE:
				Motor.C.backward();
				break;
			}
			break;
		case PORT_D:
			break;
		default:
			switch(v) {
			case CLOCKWISE:
				Motor.D.forward();
				break;
				
			case COUNTERCLOCKWISE:
				Motor.D.backward();
				break;
			}
			break;

		}

	}
	
	/**
	 * The motor stops rotating
	 */
	public void stop() {
		
		switch(port) {
		case PORT_A:
			Motor.A.stop();
			break;
			
		case PORT_B:
			Motor.B.stop();
			break;
		case PORT_C:
			Motor.C.stop();
			break;
		case PORT_D:
			Motor.D.stop();
			break;
		default:
			break;
		
		}
	}
	
	/**
	 * The motor rotates for a specific number of revolutions
	 * @param rev
	 */
	public void moveOnRevolutions(float rev) {
		int angle = (int) (rev * DEG_IN_CIRC);
		switch(port) {
		case PORT_A:
			Motor.A.rotate(angle, true);
			break;
		case PORT_B:
			Motor.B.rotate(angle, true);
			break;
		case PORT_C:
			Motor.C.rotate(angle, true);
			break;
		case PORT_D:
			Motor.D.rotate(angle, true);
			break;
		default:
			break;
		
		}
		
	}
	
	/**
	 * Sets the speed to the motor in degrees per second
	 * @param motorSpeed
	 */
	public void setSpeed(float motorSpeed) {
		switch(port) {
		case PORT_A:
			Motor.A.setSpeed(motorSpeed);
			break;
		case PORT_B:
			Motor.B.setSpeed(motorSpeed);
			break;
		case PORT_C:
			Motor.C.setSpeed(motorSpeed);
			break;
		case PORT_D:
			Motor.D.setSpeed(motorSpeed);
			break;
		default:
			break;
		
		}
	}
	
	/**
	 * Gets the speed of the motor
	 * @return Returns the speed of the motor
	 */
	public float getSpeed() {
		float speed = 0;
		switch(port) {
		case PORT_A:
			speed = Motor.A.getSpeed();
			break;
		case PORT_B:
			speed = Motor.B.getSpeed();
			break;
		case PORT_C:
			speed = Motor.C.getSpeed();
			break;
		case PORT_D:
			speed = Motor.D.getSpeed();
			break;
		default:
			break;	
		}
		return speed;
	}
	
	/**
	 * Returns the number of degrees after the last reset point
	 * @return Returns the number of degrees after the last reset point
	 */
	public int getTachoCount() {
		int t = 0;
		switch(port) {
		case PORT_A:
			t = Motor.A.getTachoCount();
			break;
		case PORT_B:
			t = Motor.B.getTachoCount();
			break;
		case PORT_C:
			t = Motor.C.getTachoCount();
			break;
		case PORT_D:
			t = Motor.D.getTachoCount();
			break;
		default:
			break;
		
		}
		return t;
	}
	
	/**
	 * Resets the count of degrees done by the motor
	 */
	public void resetTachoCount() {
		switch(port) {
		case PORT_A:
			Motor.A.resetTachoCount();
			break;
		case PORT_B:
			Motor.B.resetTachoCount();
			break;
		case PORT_C:
			Motor.C.resetTachoCount();
			break;
		case PORT_D:
			Motor.D.resetTachoCount();
			break;
		default:
			break;
		
		}
	}
	
	/**
	 * Returns the status of the motor
	 * @return true: the motor is moving; false: the motor isn't moving
	 */
	public boolean isMoving() {
		boolean mov = true;
		switch(port) {
		case PORT_A:
			mov = Motor.A.isMoving();
			break;
		case PORT_B:
			mov = Motor.B.isMoving();
			break;
		case PORT_C:
			mov = Motor.C.isMoving();
			break;
		case PORT_D:
			mov = Motor.D.isMoving();
			break;
		default:
			break;
		
		}
		return mov;
	}
}
