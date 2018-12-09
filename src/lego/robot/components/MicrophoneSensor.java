package lego.robot.components;

import lego.robot.components.sensorModes.SoundModes;
import lego.robot.exceptions.NullListenerException;
import lego.robot.interfaces.SensorPortListener;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.NXTSoundSensor;
import lejos.robotics.SampleProvider;

/**
 * Extension of Sensor to define a microphone sensor
 * @author Programming
 *
 */
public class MicrophoneSensor extends Sensor {

	/**
	 * NXT sound sensor
	 */
	private NXTSoundSensor sSens;
	/**
	 * Mode set to the sensor
	 */
	private SoundModes mode;
	
	/**
	 * Constructor of the sensor
	 * @param port: port to which the sensor is connected
	 * @param listener: listener for the sensor
	 */
	public MicrophoneSensor(TRSensorPort port, SensorPortListener listener, SoundModes mode) {
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
		sSens = new NXTSoundSensor(LocalEV3.get().getPort(portName));
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
	 * Reads the sound value
	 * DB: dB
	 * DBA: dBA
	 */
	public float readValue() {
		// get an instance of this sensor in measurement mode
		SampleProvider sound = null;
		switch(mode) {
		case DB:
			sound = sSens.getDBMode();
			break;
		case DBA:
			sound = sSens.getDBAMode();
			break;
		default:
			break;

		}
		// initialize an array of floats for fetching samples. 
		// Ask the SampleProvider how long the array should be
		//int size = touch.sampleSize();
		float[] sample = new float[sound.sampleSize()];
		// fetch a sample
		sound.fetchSample(sample, 0);
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
	 * Returns the NXTSoundSensor of the object
	 * @return Returns the NXTSoundSensor of the object
	 */
	public NXTSoundSensor getsSens() {
		return sSens;
	}

	/**
	 * Returns the selected mode of the sensor
	 * @return Returns the selected mode of the sensor
	 */
	public SoundModes getMode() {
		return mode;
	}

	
	

}
