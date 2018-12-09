package lego.robot.components;


import lego.robot.exceptions.NullListenerException;
import lego.robot.interfaces.SensorPortListener;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;

/**
 * Extension of Sensor to define a touch sensor
 * @author Programming
 *
 */
public class TouchSensor extends Sensor {

	/**
	 * leJOS EV3 touch sensor
	 */
	private EV3TouchSensor tSens;

	/**
	 * Constructor of the sensor
	 * @param port: port to which the sensor is connected
	 * @param listener: listener for the sensor
	 */
	public TouchSensor(TRSensorPort port, SensorPortListener listener) {
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
		tSens = new EV3TouchSensor(LocalEV3.get().getPort(portName));
		this.setListener(listener);
		this.setDaemon(true);
//		Thread touchSensor = new Thread();
//		touchSensor.setDaemon(true);
//		touchSensor.start();
//		System.out.println("Thread has been started");
	}
	
	@Override
	/**
	 * Reads the value from the sensor
	 * touch: 0 or 1
	 * @return read value
	 */
	public float readValue() {
		// get an instance of this sensor in measurement mode
		SampleProvider touch= tSens.getMode("Touch");
		// initialize an array of floats for fetching samples. 
		// Ask the SampleProvider how long the array should be
		//int size = touch.sampleSize();
		float[] sample = new float[touch.sampleSize()];
		// fetch a sample
		touch.fetchSample(sample, 0);
		return (int) sample[0];
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
			if(oldV != newV) {
				try {
					super.generateEvent(oldV, newV);
				} catch (NullListenerException e) {
					
				}
			}
			oldV = newV;
		}

	}

	/**
	 * Returns the EV3TouchSensor of the object
	 * @return Returns the EV3TouchSensor of the object
	 */
	public EV3TouchSensor gettSens() {
		return tSens;
	}
}
