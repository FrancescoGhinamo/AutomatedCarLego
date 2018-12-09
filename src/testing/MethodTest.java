package testing;

import automatedDrive.car.AutomatedCar;
import lejos.hardware.Button;
import localization.referenceSystem.Area;

public class MethodTest {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		AutomatedCar ac = AutomatedCar.getAutomatedCar(new Area(), 0.0);
		
		ac.travelToArea(new Area(20 ,30));
		Button.waitForAnyPress();
	}

}
