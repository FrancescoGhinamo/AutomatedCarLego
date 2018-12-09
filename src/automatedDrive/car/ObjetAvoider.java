package automatedDrive.car;

import driveControl.Navigator;
import localization.referenceSystem.Area;
import localization.referenceSystem.GeneratedArea;
import threadCommunication.singletons.CurrentPositionSingleton;

/**
 * Object capable of making the car avoid an obstacle
 * @author Programming
 *
 */
public class ObjetAvoider {
	
	/**
	 * Controlled car
	 */
	private AutomatedCar car;
	
	/**
	 * Navigator of the car
	 */
	private Navigator nav;

	/**
	 * Constructor for the ObjectAvoider
	 * @param car: controlled car
	 * @param nav: navigator
	 */
	public ObjetAvoider(AutomatedCar car, Navigator nav) {
		this.car = car;
		this.nav = nav;
	}
	
	
	
	/**
	 * This method generates the Area to add to robot path to try to avoid the object
	 * @return Area to the path to try to avoid the object
	 */
	public GeneratedArea generateArea() {
		//position in which the robot stopped
		Area _stopArea = CurrentPositionSingleton.getCurrentPositionSingleton().getCurrentPosition();
		int _newX = 0;
		int _newY = 0;
		if(car.getTargetHeading() == 90 || car.getTargetHeading() == -90 || car.getTargetHeading() == 270) {
			_newX = (int) _stopArea.getxCoord();
			_newY = (int) (_stopArea.getyCoord() - 1);
		}
		else {
			_newX = (int) (_stopArea.getxCoord() - 1);
			_newY = (int) _stopArea.getyCoord();
		}
		return new GeneratedArea(_newX, _newY);		
	}
	
	/**
	 * Recalculates the path of the robot in order to avoid the obstacle
	 * @param gArea: GeneratedArea to add to the path
	 */
	public void recalculatePath(GeneratedArea gArea) {
		//Area the robot was traveling to
		int index = nav.getTravelingTo();
		//adding the GnertedArea to the places Vector
		nav.getPlaces().insertElementAt(gArea, index);
		//cleaning the vector from other GeneratedArea
		for(int _i = index + 1; _i < nav.getPlaces().size(); _i++) {
			if(nav.getPlaces().elementAt(_i) instanceof GeneratedArea) {
				nav.getPlaces().remove(_i);
			}
		}
		
	}
	
	

}
