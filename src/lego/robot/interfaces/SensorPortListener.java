package lego.robot.interfaces;

import lejos.hardware.port.Port;


/**
 * Listener to catch an event from a sensor
 * @author Programming
 *
 */
public interface SensorPortListener {

	/**
	 * 
	 * @param aSource: source of the event
	 * @param aOldValue: old value of the sensor
	 * @param aNewValue: new value of the sensor
	 */
	public void stateChanged(Port aSource, float oldV, float newV);
	
}
