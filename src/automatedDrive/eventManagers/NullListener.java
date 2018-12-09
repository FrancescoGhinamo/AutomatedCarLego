package automatedDrive.eventManagers;

import lego.robot.interfaces.SensorPortListener;
import lejos.hardware.port.Port;

/**
 * Class created as a default listener that doesn't give replies to events
 * @author Programming
 *
 */
public class NullListener implements SensorPortListener {

	@Override
	public void stateChanged(Port aSource, float aOldValue, float aNewValue) {
		// TODO Auto-generated method stub

	}

}
