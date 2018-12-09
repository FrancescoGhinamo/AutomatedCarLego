package lego.robot.components;

import lego.robot.GUI.StatusBar;
import lego.robot.components.sensorModes.IRModes;
import lego.robot.exceptions.NullListenerException;
import lego.robot.interfaces.EV3HardwareInfo;
import lego.robot.interfaces.SensorPortListener;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.SampleProvider;

/**
 * Extension of Sensor to define an infrared sensor
 * @author Programming
 *
 */
public class InfraredSensor extends Sensor implements EV3HardwareInfo {
	
	/**
	 * EV3 leJOS sensor
	 */
	private EV3IRSensor irSens;
	/**
	 * Mode for the sensor
	 */
	private IRModes mode;
	
	/**
	 * Constructor of the sensor
	 * @param port: port to which the sensor is connected
	 * @param listener: listener for the sensor
	 */
	public InfraredSensor(TRSensorPort port, SensorPortListener listener, IRModes mode) {
		StatusBar bar = StatusBar.getStatusBar();
		bar.increaseProgress(100 / INSTANCE_ELEMENTS);
		
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
		irSens = new EV3IRSensor(LocalEV3.get().getPort(portName));
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
	 * distance: cm
	 */
	public float readValue() {
		// get an instance of this sensor in measurement mode
		SampleProvider ir = null;
		switch(mode) {
		case IR_DISTANCE:
			ir = irSens.getDistanceMode();
			break;
		case IR_SEEK:
			ir = irSens.getSeekMode();
			break;
		default:
			break;

		}
		// initialize an array of floats for fetching samples. 
		// Ask the SampleProvider how long the array should be
		//int size = touch.sampleSize();
//		System.out.println(ir.sampleSize());
		float[] sample = new float[ir.sampleSize()];
		// fetch a sample
		ir.fetchSample(sample, 0);
		return (int) sample[0];
	}

	@Override
	/**
	 * Returns the value associated with the mode
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
	 * Returns the EV3IRSensor of the object
	 * @return Returns the EV3IRSensor of the object
	 */
	public EV3IRSensor getsSens() {
		return irSens;
	}

	/**
	 * Returns the selected mode of the sensor
	 * @return Returns the selected mode of the sensor
	 */
	public IRModes getMode() {
		return mode;
	}



}
