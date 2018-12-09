package testing;

import automatedDrive.car.AutomatedCar;
import driveControl.Navigator;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import localization.referenceSystem.Area;
import threadCommunication.singletons.CurrentPositionSingleton;

/**
 * Class to test the Navigator class
 * @author Programming
 * 
 */
public class NavigatorProTest {

	public static void main(String[] args) {
		System.out.println("\n\n INITIALIZING...\n\n");
		AutomatedCar car = AutomatedCar.getAutomatedCar(new Area(0, 0), 0.0);
		Navigator navigator = new Navigator(car);
		Sound.beepSequenceUp();
		navigator.setPathAndNavigate();
		waitForCompleteTravel(navigator);
		Sound.beepSequence();
		System.out.println("\n\n\n\n\n\n   PROGRAM\n    ENDED\n");
		Button.waitForAnyPress();
		

	}
	/**
	 * The method keeps the robot wait until it has reached the final destination
	 */
	public static void waitForCompleteTravel(Navigator nav) {
		while(nav.getPlaces().elementAt(nav.getPlaces().size() - 1).equals(CurrentPositionSingleton.getCurrentPositionSingleton().getCurrentPosition())) {
			//waiting
		}
	}
}