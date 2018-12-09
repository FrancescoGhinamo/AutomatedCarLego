package threadCommunication.singletons;

import localization.referenceSystem.Area;

/**
 * Singleton to share info about the current extremes of the movement
 * @author Programming
 *
 */
public class PartialMovExtremesSingleton {
	
	/**
	 * Single instance of the instance
	 */
	private static PartialMovExtremesSingleton singleton = null;
	
	/**
	 * Start position of the robot
	 */
	private Area begin;
	/**
	 * Target of the robot
	 */
	private Area target;
	
	/**
	 * Private constructor of the singleton
	 */
	private PartialMovExtremesSingleton() {
		begin = new Area(0, 0);
		target = new Area(0, 0);
	}
	
	/**
	 * Method to get the single instance of the singleton
	 * @return instance of the singleton
	 */
	public static synchronized PartialMovExtremesSingleton getPartialMovExtremesSingleton() {
		if(singleton == null) {
			singleton = new PartialMovExtremesSingleton();
		}
		return singleton;
	}
	
	/**
	 * Getter for the current target
	 * @return current target
	 */
	public Area getPartialTarget() {
		return target;
	}
	
	/**
	 * Setter for the current target
	 * @param pT: new current target
	 */
	public void setPartialTarget(Area pT) {
		this.target = pT;
	}

	/**
	 * Getter for the begin position
	 * @return the begin
	 */
	public Area getBegin() {
		return begin;
	}

	/**
	 * Setter for the begin position
	 * @param begin the begin to set
	 */
	public void setBegin(Area begin) {
		this.begin = begin;
	}
	
	
	


}
