package threadCommunication.singletons;

import localization.referenceSystem.Area;

/**
 * Singleton to share information about the current position of the robot
 * @author Programming
 *
 */
public class CurrentPositionSingleton {
	
	/**
	 * Local instance of the singleton
	 */
	private static CurrentPositionSingleton singleton;
	
	/**
	 * Current area of the robot
	 */
	private Area currentPosition;
	
	private CurrentPositionSingleton() {
		this.currentPosition = new Area(0, 0);
	}
	
	/**
	 * Method to get the instance of the singleton
	 * @return
	 */
	public static synchronized CurrentPositionSingleton getCurrentPositionSingleton() {
		if(singleton == null) {
			singleton = new CurrentPositionSingleton();
		}
		return singleton;
	}

	

	/**
	 * Getter for the last calculated current position
	 * @return the currentPosition
	 */
	public Area getCurrentPosition() {
		return currentPosition;
	}

	/**
	 * Setter for the just calculated current position
	 * @param currentPosition the currentPosition to set
	 */
	public void setCurrentPosition(Area currentPosition) {
		this.currentPosition = currentPosition;
	}
	
	
	
	
	

}
