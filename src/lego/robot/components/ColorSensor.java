package lego.robot.components;

import lego.robot.components.sensorModes.ColorModes;
import lego.robot.exceptions.NullListenerException;
import lego.robot.interfaces.SensorPortListener;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;


/**
 * Extension of Sensor to define a color sensor
 * @author Programming
 *
 */
public class ColorSensor extends Sensor {
	
	/**
	 * EV3 leJOS color sensor
	 */
	private EV3ColorSensor cSens;
	/**
	 * Mode for the sensor
	 */
	private ColorModes mode;
	
	/**
	 * Constructor of the sensor
	 * @param port: port to which the sensor is connected
	 * @param listener: listener for the sensor
	 */
	public ColorSensor(TRSensorPort port, SensorPortListener listener, ColorModes mode) {
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
		cSens = new EV3ColorSensor(LocalEV3.get().getPort(portName));
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
	 */
	public float readValue() {
		// get an instance of this sensor in measurement mode
		SampleProvider col = null;
		switch(mode) {
		case AMBIENT:
			col = cSens.getAmbientMode();
			break;
		case COLOR_ID:
			col = cSens.getColorIDMode();
			break;
		case RED:
			col = cSens.getRedMode();
			break;
		case RGB:
			col = cSens.getRGBMode();
			break;
		default:
			break;
		

		}
		// initialize an array of floats for fetching samples. 
		// Ask the SampleProvider how long the array should be
		//int size = touch.sampleSize();
//		System.out.println(col.sampleSize());
		float[] sample = new float[col.sampleSize()];
		// fetch a sample
		col.fetchSample(sample, 0);
		return (int) (sample[0] * 100);
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
	 * Returns the EV3ColorSensor of the object
	 * @return Returns the EV3ColorSensor of the object
	 */
	public EV3ColorSensor getsSens() {
		return cSens;
	}

	/**
	 * Returns the selected mode of the sensor
	 * @return Returns the selected mode of the sensor
	 */
	public ColorModes getMode() {
		return mode;
	}

}
