package lego.robot.components;

import lego.robot.components.sensorModes.AccModes;
import lego.robot.interfaces.SensorPortListener;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.HiTechnicAccelerometer;
import lejos.robotics.SampleProvider;
/**
 * Extension of Sensor to define an accelerometer sensor
 * @author Programming
 *
 */
public class AccelerometerSensor extends Sensor {
	/**
	 * leJOS accel sensor
	 */
	private HiTechnicAccelerometer gSens;
	/**
	 * Mode for the sensor
	 */
	private AccModes mode;
	
	/**
	 * Constructor of the sensor
	 * @param port: port to which the sensor is connected
	 * @param listener: listener for the sensor
	 */
	public AccelerometerSensor(TRSensorPort port, SensorPortListener listener, AccModes mode) {
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
		gSens = new HiTechnicAccelerometer(LocalEV3.get().getPort(portName));
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
	 * acceleration: m/s^2
	 */
	public float readValue() {
		// get an instance of this sensor in measurement mode
		SampleProvider g = null;
		g = gSens.getAccelerationMode();
		// initialize an array of floats for fetching samples. 
		// Ask the SampleProvider how long the array should be
		//int size = touch.sampleSize();
//		System.out.println(g.sampleSize());
		float[] sample = new float[g.sampleSize()];
		// fetch a sample
		g.fetchSample(sample, 0);
		int val = 0;
		switch(mode) {
		case X_AXIS:
			val = (int) sample[0];
			break;
		case Y_AXIS:
			val = (int) sample[1];
			break;
		case Z_AXIS:
			val = (int) sample[2];
			break;
		default:
			break;
		
		}
		return val;
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
				this.getListener().stateChanged(getPort(), oldV, newV);

			}
			oldV = newV;
		}
	}

	
	/**
	 * Returns the HiTechnicAccelerometer of the object
	 * @return Returns the HiTechnicAccelerometer of the object
	 */
	public HiTechnicAccelerometer getsSens() {
		return gSens;
	}

	/**
	 * Returns the selected mode of the sensor
	 * @return Returns the selected mode of the sensor
	 */
	public AccModes getMode() {
		return mode;
	}

}
