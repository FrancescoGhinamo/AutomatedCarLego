package lego.robot.components;


import lego.robot.GUI.StatusBar;
import lego.robot.components.sensorModes.GyroModes;
import lego.robot.exceptions.NullListenerException;
import lego.robot.interfaces.EV3HardwareInfo;
import lego.robot.interfaces.SensorPortListener;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.robotics.SampleProvider;

/**
 * Extension of Sensor to define a color sensor
 * @author Programming
 *
 */
public class GyroSensor extends Sensor implements EV3HardwareInfo {
	/**
	 * EV3 leJOS gyro sensor
	 */
	private EV3GyroSensor gSens;
	/**
	 * Mode for the sensor
	 */
	private GyroModes mode;
	
	/**
	 * Constructor of the sensor
	 * @param port: port to which the sensor is connected
	 * @param listener: listener for the sensor
	 */
	public GyroSensor(TRSensorPort port, SensorPortListener listener, GyroModes mode) {
		StatusBar bar = StatusBar.getStatusBar();
		bar.increaseProgress(100 / INSTANCE_ELEMENTS);
		
//		System.out.println(" Creating\n GyroSensor\n");
		String portName = "";
		switch(port) {
		case PORT_1:
			portName = "S1";
			setPort(SensorPort.S1);
			break;
		case PORT_2:
			portName = "S2";
			setPort(SensorPort.S2);
			break;
		case PORT_3:
			portName = "S3";
			setPort(SensorPort.S3);
			break;
		case PORT_4:
			portName = "S4";
			setPort(SensorPort.S4);
			break;
		default:
			break;

		}
		gSens = new EV3GyroSensor(LocalEV3.get().getPort(portName));
		this.setListener(listener);		
		this.mode = mode;
		this.setDaemon(true);
//		Thread touchSensor = new Thread();
//		touchSensor.setDaemon(true);
//		touchSensor.start();
//		System.out.println("Thread has been started");
	}
	
	@Override
	/**
	 * Returns the value associated with the mode
	 * angle: degrees
	 * rate: degree/second
	 */
	public float readValue() {
		// get an instance of this sensor in measurement mode
		SampleProvider g = null;
		switch(mode) {
		case ANGLE:
			g = gSens.getAngleMode();
			break;
		case RATE:
			g = gSens.getRateMode();
			break;
		default:
			break;
		
		}
		// initialize an array of floats for fetching samples. 
		// Ask the SampleProvider how long the array should be
		//int size = touch.sampleSize();
//		System.out.println(g.sampleSize());
		float[] sample = new float[g.sampleSize()];
		// fetch a sample
		g.fetchSample(sample, 0);
		return sample[0];
	}
	

	@Override
	/**
	 * Main method of the thread: checks the sensor status
	 */
	public void run() {
		float oldV, newV;
		oldV = readValue();
		//forever
		while(true) {
			newV = readValue();
//			System.out.println("Fetched value: "+newV);
//			System.out.println(newV);
			if(oldV != newV) {
//				System.out.println("Different value");
				try {
					super.generateEvent(oldV, newV);
				} catch (NullListenerException e) {
					
				}
			}
			oldV = newV;
		}
	}
	
	/**
	 * Resets the accumulation of angle
	 */
	public void reset() {
		gSens.reset();
	}
	
	/**
	 * Returns the EV3GyroSensor of the object
	 * @return Returns the EV3GyroSensor of the object
	 */
	public EV3GyroSensor getsSens() {
		return gSens;
	}

	/**
	 * Returns the selected mode of the sensor
	 * @return Returns the selected mode of the sensor
	 */
	public GyroModes getMode() {
		return mode;
	}
}
