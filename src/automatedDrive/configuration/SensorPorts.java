package automatedDrive.configuration;

import lego.robot.components.TRSensorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;

/**
 * Interface containing the ports to which sensors are connected
 * @author Programming
 *
 */
public interface SensorPorts {

	/**
	 * Port of gyro sensor
	 */
	static final TRSensorPort GYRO_PORT = TRSensorPort.PORT_2;
	/**
	 * Port of the infrared distance sensor
	 */
	static final TRSensorPort IR_PORT = TRSensorPort.PORT_4;
	
	
	
	
	/**
	 * Port of gyro sensor (leJOS)
	 */
	static final Port GYRO_leJOS = SensorPort.S2;
	/**
	 * Port of the infrared distance sensor (leJOS)
	 */
	static final Port IR_leJOS = SensorPort.S4;
}
