package testing;

import automatedDrive.car.AutomatedCar;
import driveControl.AreaSetter;
import lejos.hardware.Button;
import localization.referenceSystem.Area;

/**
 * Test of the navigator function of the robot
 * @author Programming
 *
 */
public class NavigatorTest {
	
	/**
	 * To interface the car to the rest of the program
	 */
	private AreaSetter setPath;
	
	public NavigatorTest() {
		System.out.println(" Creating tester\n");
		setPath = AreaSetter.getAreaSetter();
	}

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		System.out.println("\n\n  INITIALIZING...\n");
		NavigatorTest test = new NavigatorTest();
		AutomatedCar ac = AutomatedCar.getAutomatedCar(new Area(), 0.0);
		
		ac.travelToArea(test.getSetPath().getArea());
		Button.waitForAnyPress();
	}

	/**
	 * @return the setPath
	 */
	public AreaSetter getSetPath() {
		return setPath;
	}

	/**
	 * @param setPath the setPath to set
	 */
	public void setSetPath(AreaSetter setPath) {
		this.setPath = setPath;
	}
	
	

}
