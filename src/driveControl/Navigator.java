package driveControl;

import java.util.Vector;

import automatedDrive.car.AutomatedCar;
import automatedDrive.configuration.ArrayIndexes;
import automatedDrive.eventManagers.GeneralEnvironmentListener;
import automatedDrive.extensions.ClosedLoopControlTransmission;
import automatedDrive.threads.PositionTracker;
import driveControl.configuration.ItemsIndexes;
import lego.robot.GUI.StatusBar;
import lego.robot.interfaces.EV3HardwareInfo;
import lejos.hardware.lcd.LCD;
import lejos.utility.TextMenu;
import localization.referenceSystem.Area;

/**
 * Class to control the AutomatedCar to follow a path
 * @author Programming
 *
 */
public class Navigator implements ArrayIndexes, ItemsIndexes, EV3HardwareInfo {
	
	
	/**
	 * Controlled car
	 */
	private AutomatedCar car;
	/**
	 * leJOS menu to chose navigator option
	 */
	private TextMenu menu;
	
	/**
	 * Object to track the current position of the robot
	 */
	private PositionTracker trackPosition;
	/**
	 * Field to register information about the index of the position in the places array in which is stored
	 * the Area the robot is traveling to
	 */
	private int travelingTo;
	

	/**
	 * List of areas where to travel to
	 */
	private Vector<Area> places;
	
	/**
	 * Constructor for the navigator
	 * @param car: car to be controlled
	 */
	public Navigator(AutomatedCar car) {
		StatusBar bar = StatusBar.getStatusBar();
		bar.setProgress(100);
		
//		System.out.println("\n Creating\n Navigator");
		this.car = car;
		((GeneralEnvironmentListener) car.getSensorArray()[IR_SENS].getListener()).setNav(this);
		trackPosition = new PositionTracker(car);
		trackPosition.start();
		travelingTo = 0;
		places = new Vector<Area>();
		String[] menuItems = new String[4];
		menuItems[SET_DEST] = "Set destination";
		menuItems[ADD_POINT] = "Add way point";
		menuItems[TRAVEL] = "Travel to dest.";
		menu = new TextMenu(menuItems, 1, "Navigator");
	}
	
	/**
	 * Method to set the path to follow for the robot
	 */
	public void setPathAndNavigate() {
		
		
		//chosen item
		int item = 0;
		do {
			System.out.println("\n\n\n\n\n\n\n\n");
			LCD.clearDisplay();
			item = menu.select();
			
			switch(item) {
			case SET_DEST:
				setDestination();
				break;
				
			case ADD_POINT:
				if(!places.isEmpty()) {
					addWayPoint();
				}
				break;
				
			case TRAVEL:
				if(places.isEmpty()) {
					//to make insert another way point
					item = 9;
				}
				break;
			}
			
		}
		while(item != TRAVEL);
		((ClosedLoopControlTransmission) car.getTransmission()).getgSens().reset();
		LCD.clearDisplay();
		System.out.println("\n\n  TRAVELING");
		travel();
	}
	
	/**
	 * Adds a place at the end of the list of Areas to pass through
	 */
	public void setDestination() {
		AreaSetter as = AreaSetter.getAreaSetter();
		places.add(as.getArea());
	}
	
	/**
	 * Adds a way point before the destination
	 */
	public void addWayPoint() {
		AreaSetter as = AreaSetter.getAreaSetter();
		int index = places.size() - 1;
		places.add(index, as.getArea());
	}
	
	/**
	 * Controls the robot to make it travel along the set path
	 */
	public void travel() {
		for(int i = 0; i < places.size(); i++) {
			this.travelingTo = i;
			//travel to the area
			car.travelToAreaDirectly(places.elementAt(i));
		}
	}
	
	/**
	 * Resumes the travel of the robot starting from a given point
	 * @param iBegin: index that defines the Area to travel to, resuming the motion
	 */
	public void resumeTravel(int iBegin) {
		for(int i = iBegin; i < places.size(); i++) {
			this.travelingTo = i;
			//travel to the area
			car.travelToAreaDirectly(places.elementAt(i));
		}
	}
	
	/**
	 * Returns the index of the places array containing the Area the robot is traveling to
	 * @return Returns the index of the places array containing the Area the robot is traveling to
	 */
	public int getTravelingTo() {
		return travelingTo;
	}

	/**
	 * Getter for the places Vector
	 * @return the places the robot is traveling to
	 */
	public Vector<Area> getPlaces() {
		return places;
	}
	
	
	
}
