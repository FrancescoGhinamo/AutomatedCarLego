package automatedDrive.threads;

import automatedDrive.car.AutomatedCar;
import automatedDrive.enums.Action;
import lego.robot.GUI.StatusBar;
import lego.robot.interfaces.EV3HardwareInfo;
import localization.referenceSystem.Area;
import threadCommunication.singletons.CurrentPositionSingleton;
import threadCommunication.singletons.PartialMovExtremesSingleton;

/**
 * Thread to track the position of the robot during its movements
 * @author Programming
 *
 */
public class PositionTracker extends Thread implements EV3HardwareInfo {
	
	/**
	 * Car to track
	 */
	private AutomatedCar car;
	
	/**
	 * Singleton to get information about the extremes of the current movement
	 */
	private PartialMovExtremesSingleton extSingleton;
	
	/**
	 * Singleton to share information about the calculated current position
	 */
	private CurrentPositionSingleton currSingleton;
	
	/**
	 * Constructor of the tracker from given car
	 * @param car: AutomatedCar to track
	 */
	public PositionTracker(AutomatedCar car) {
		
		StatusBar bar = StatusBar.getStatusBar();
		bar.increaseProgress(100 / INSTANCE_ELEMENTS);
		
		
		this.car = car;
		extSingleton = PartialMovExtremesSingleton.getPartialMovExtremesSingleton();
		currSingleton = CurrentPositionSingleton.getCurrentPositionSingleton();
		this.setDaemon(true);
	}
	
	/**
	 * Main method of the thread, communicates the calculated current position
	 */
	public void run() {
		//forever
		while(true) {
			//update of the current position via singleton
			currSingleton.setCurrentPosition(calculateCurrentArea());
			//to debug
//			System.out.println("("+currSingleton.getCurrentPosition().getxCoord()+", "+currSingleton.getCurrentPosition().getyCoord()+")");
		}
	}
	
	/**
	 * Method to calculate the current position of the robot
	 * @return: Area with the calculated position
	 */
	public Area calculateCurrentArea() {
		double currX = 0;
		double currY = 0;
		
		Area _current = null;
		
		if(car.currentAction == Action.STEERING) {
			_current = extSingleton.getBegin();
		}
		else {
			//x side
			if(extSingleton.getBegin().getxCoord() < extSingleton.getPartialTarget().getxCoord()) {
				//if the destination is on the right of the beginning point
				currX = extSingleton.getBegin().getxCoord() + calulateTaveledX();
			}
			else {
				if(extSingleton.getBegin().getxCoord() > extSingleton.getPartialTarget().getxCoord()) {
					//if the destination is on the left of the beginning point
					currX = extSingleton.getBegin().getxCoord() - calulateTaveledX();
				}
				else {
					//if the destination is horizontally aligned with the beginning point
					currX = extSingleton.getBegin().getxCoord();
				}
			}

			//y side
			if(extSingleton.getBegin().getyCoord() < extSingleton.getPartialTarget().getyCoord()) {
				//if the destination is ahead the beginning point
				currY = extSingleton.getBegin().getyCoord() + calulateTaveledY();
			}
			else {
				if(extSingleton.getBegin().getyCoord() > extSingleton.getPartialTarget().getyCoord()) {
					//if the destination is behind of the beginning point
					currY = extSingleton.getBegin().getyCoord() - calulateTaveledY();
				}
				else {
					//if the destination is vertically aligned with the beginning point
					currY = extSingleton.getBegin().getyCoord();
				}
			}
			_current = new Area(currX, currY);
		}
		
		
		return _current;
	}
	
	/**
	 * Calculus of the traveled distance on x axis
	 * @return traveled distance
	 */
	@SuppressWarnings("deprecation")
	public double calulateTaveledX() {
		return car.getTransmission().getPilot().getMovement().getDistanceTraveled() * Math.cos(calculateAngle());
	}
	
	/**
	 * Calculus of the traveled distance on y axis
	 * @return traveled distance
	 */
	@SuppressWarnings("deprecation")
	public double calulateTaveledY() {
		return car.getTransmission().getPilot().getMovement().getDistanceTraveled() * Math.sin(calculateAngle());
	}
	
	
	/**
	 * Calculus of the angle between the direction of movement and the x side
	 * @return angle in radiants
	 */
	public double calculateAngle() {
		return Math.atan(calculateYSide() / calculateXSide());
	}
	
	/**
	 * Calculus of the horizontal component of the current movement
	 * @return length of the side
	 */
	public double calculateXSide() {
		return Math.abs(extSingleton.getPartialTarget().getxCoord() - extSingleton.getBegin().getyCoord());
	}
	
	/**
	 * Calculus of the vertical component of the current movement
	 * @return length of the side
	 */
	public double calculateYSide() {
		return Math.abs(extSingleton.getPartialTarget().getyCoord() - extSingleton.getBegin().getyCoord());
	}

}
