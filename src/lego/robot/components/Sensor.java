package lego.robot.components;

import lego.robot.exceptions.NullListenerException;
import lego.robot.interfaces.SensorPortListener;
import lejos.hardware.port.Port;

/**
 * Abstract class to represent a general EV3 sensor
 * @author Programming
 *
 */
public abstract class Sensor extends Thread {
	
	/**
	 * Port to which the sensor is connected
	 */
	private Port port;
	/**
	 * Listener for the sensor
	 */
	private SensorPortListener listener;
	
	
	
	
	/**
	 * Sets the port to the sensor
	 * @param port: port to which the sensor is connected
	 */
	public void setPort(Port port) {
		this.port = port;
	}
	/**
	 * Returns the port to which the sensor is connected
	 * @return Returns the port to which the sensor is connected
	 */ 
	public Port getPort() {
		return port;
	}

	/**
	 * Returns the SensorPortListener
	 * @return Returns the SensorPortListener
	 */ 
	public SensorPortListener getListener() {
		return listener;
	}

	/**
	 * Sets the listener to the object
	 * @param listener: listener object
	 */
	public void setListener(SensorPortListener listener) {
		this.listener = listener;
	}
	
	/**
	 * Reads the value from the sensor
	 * @return read value
	 */
	public abstract float readValue();
	
	/**
	 * Generates the event from the values of the sensor
	 * @throws NullListenerException
	 * @param oldV: old value of the sensor
	 * @param newV: new value of the sensor
	 */
	public void generateEvent(float oldV, float newV) throws NullListenerException {
		if(listener != null) {
			listener.stateChanged(getPort(), oldV, newV);
		}
		else {
			throw new NullListenerException();
		}
	}

	
	

}
