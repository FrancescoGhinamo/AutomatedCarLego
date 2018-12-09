package automatedDrive.extensions;

import automatedDrive.car.AutomatedCar;
import automatedDrive.configuration.MovementParameters;
import automatedDrive.eventManagers.TransmissionListener;
import lego.robot.GUI.StatusBar;
import lego.robot.components.GyroSensor;
import lego.robot.components.TRMotor;
import lego.robot.components.MotorPort;
import lego.robot.components.TRSensorPort;
import lego.robot.components.Transmission;
import lego.robot.components.movements.RotationVerse;
import lego.robot.components.sensorModes.GyroModes;
import lego.robot.interfaces.EV3HardwareInfo;


/**
 * Extension of Transmission to create an closed loop controlled transmission
 * @author Programming
 *
 */
public class ClosedLoopControlTransmission extends Transmission implements MovementParameters, EV3HardwareInfo {
	
	/**
	 * Left motor of the transmission
	 */
	private TRMotor leftMotor = new TRMotor(MotorPort.PORT_B);
	/**
	 * Right motor of the transmission
	 */
	private TRMotor rightMotor = new TRMotor(MotorPort.PORT_C);
	
	/**
	 * Gyro sensor to transmission control
	 */
	private GyroSensor gSens;
	/**
	 * Controlled car
	 */
	private AutomatedCar controlledCar;
	

	/**
	 * Constructor
	 * @param wheelDiameter {@link Transmission}
	 * @param trackWidthCm  {@link Transmission}
	 * @param gSensPorts: port to which the the gyro sensor is connected
	 */
	public ClosedLoopControlTransmission(float wheelDiameter, float trackWidthCm, TRSensorPort gSensPort, AutomatedCar controlledCar) {
		super(wheelDiameter, trackWidthCm);
		
		StatusBar bar = StatusBar.getStatusBar();
		bar.increaseProgress(100 / INSTANCE_ELEMENTS);
		
//		System.out.println(" Creating CLC\n Transmission\n");
		this.controlledCar = controlledCar;
		gSens = new GyroSensor(gSensPort, new TransmissionListener(controlledCar), GyroModes.ANGLE);
		//max priority to increase frequency of sensor polling
		gSens.setPriority(Thread.MAX_PRIORITY);
		gSens.start();
		
	}
	
	/**
	 * Makes the robot steer to the given heading
	 * @param heading: heading to reach
	 */
	public void steerToHeading(double heading) {
		leftMotor.setSpeed((float) MOTOR_SPEED);
		rightMotor.setSpeed((float) MOTOR_SPEED);
		if(controlledCar.getTargetHeading() > controlledCar.getInitialHeading()) {
			//steer right
			leftMotor.rotate(RotationVerse.CLOCKWISE);
			rightMotor.rotate(RotationVerse.COUNTERCLOCKWISE);
			
		}
		else if(controlledCar.getTargetHeading() < controlledCar.getInitialHeading()) {
			//steer left
			leftMotor.rotate(RotationVerse.COUNTERCLOCKWISE);
			rightMotor.rotate(RotationVerse.CLOCKWISE);
		}
	}

	/**
	 * @return the gSens
	 */
	public GyroSensor getgSens() {
		return gSens;
	}
	
	

}
