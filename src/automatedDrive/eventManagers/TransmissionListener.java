package automatedDrive.eventManagers;

import automatedDrive.car.AutomatedCar;
import automatedDrive.configuration.MovementParameters;
import automatedDrive.configuration.SensorPorts;
import lego.robot.interfaces.SensorPortListener;
import lejos.hardware.port.Port;
import threadCommunication.singletons.HeadingSingleton;

/**
 * Listener for events generated when the robot is rotating on its center
 * @author Programming
 *
 */
public class TransmissionListener implements SensorPortListener, MovementParameters, SensorPorts {

	/**
	 * Controlled car
	 */
	private AutomatedCar ac;

	/**
	 * Singleton pattern to share car heading
	 */
	private HeadingSingleton currentHeading;
	
	/**
	 * Constructor of the listener
	 * @param car: controlled car
	 */
	public TransmissionListener(AutomatedCar car) {
		this.ac = car;
		currentHeading = HeadingSingleton.getHeadingSingleton(0);
	}

	@Override
	public void stateChanged(Port aSource, float aOldValue, float aNewValue) {

		//if the event was generated from the gyro sensor
		if(aSource.equals(GYRO_leJOS)) {
//			System.out.println(aNewValue);
			currentHeading.setHeading(aNewValue);
			switch(ac.currentAction) {
			case STANDING:
				break;
			case STEERING:
//				if((aNewValue >= ac.getTargetHeading() - HEADING_TOLERANCE) && (aNewValue <= ac.getTargetHeading() + HEADING_TOLERANCE)) {
//					ac.stop();
//				}

				if((int) (-1 * aNewValue) == (int) ac.getTargetHeading()) {
					ac.stop();
				}
				
				break;
			case TRAVELING:
				break;
				
			default:
				break;

			}
		}

	}

}
