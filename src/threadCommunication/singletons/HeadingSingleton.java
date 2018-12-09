package threadCommunication.singletons;

/**
 * Sharing of current heading of the robot using Singleton pattern
 * @author Programming
 *
 */
public class HeadingSingleton {

	/**
	 * Local instance of the Singleton
	 */
	private static HeadingSingleton hs = null;
	/**
	 * Heading value
	 */
	private double heading;
	
	/**
	 * Constructor
	 * @param heading: heading value
	 */
	private HeadingSingleton(double heading) {
		this.heading = heading;
	}
	
	/**
	 * Method to get the instance of the singleton
	 * @param heading: heading value
	 * @return The instance of the singleton
	 */
	public static synchronized HeadingSingleton getHeadingSingleton(double heading) {
		if(hs == null) {
			hs = new HeadingSingleton(heading);
		}
		return hs;
	}
	
	/**
	 * Sets a new heading to the singleton
	 * @param heading
	 */
	public void setHeading(double heading) {
		this.heading = heading;
	}
	
	/**
	 * Gets the current registered heading
	 * @return Current heading
	 */
	public double getHeading() {
		return this.heading;
	}
	
	
	
}
