package automatedDrive.car;

import automatedDrive.configuration.ArrayIndexes;
import automatedDrive.configuration.MovementParameters;
import automatedDrive.configuration.SensorPorts;
import automatedDrive.enums.Action;
import automatedDrive.eventManagers.GeneralEnvironmentListener;
import automatedDrive.eventManagers.NullListener;
import automatedDrive.extensions.ClosedLoopControlTransmission;
import driveControl.AreaSetter;
import lego.robot.TurtleRobot;
import lego.robot.GUI.StatusBar;
import lego.robot.components.BrickButton;
import lego.robot.components.InfraredSensor;
import lego.robot.components.MotorPort;
import lego.robot.components.Sensor;
import lego.robot.components.TRMotor;
import lego.robot.components.Transmission;
import lego.robot.components.buttons.ButtonType;
import lego.robot.components.sensorModes.IRModes;
import lego.robot.interfaces.EV3HardwareInfo;
import localization.referenceSystem.Area;
import threadCommunication.singletons.CurrentPositionSingleton;
import threadCommunication.singletons.PartialMovExtremesSingleton;

/**
 * Extension of TurleRobot to represent an automated car
 * @author Programming
 *
 */
public class AutomatedCar extends TurtleRobot implements ArrayIndexes, MovementParameters, SensorPorts, EV3HardwareInfo {
	
	
	/**
	 * Value to convert a measure of an angle from radiant to degrees
	 */
	public static final double RAD_TO_DEG = 180 / Math.PI;

	/**
	 * Action the robot is doing
	 */
	public Action currentAction;

	/**
	 * Initial area of a movement
	 */
	private Area initialArea;
	/**
	 * Initial heading of a movement
	 */
	private double initialHeading;
	/**
	 * Step area for the movement
	 */
	private Area _targetArea;	
	/**
	 * Area to be reached with the movement
	 */
	private Area targetArea;
	/**
	 * Heading to reach with a rotation
	 */
	private double targetHeading;


	/**
	 * Inherited constructor from superclass
	 * @param transmission: specific Transmission for the robot
	 * @param installedSensors: specific sensors installed on the robot
	 * @param auxiliaryMotorArray: auxiliary motors installed
	 * @param buttonArray: initialized buttons of the brick
	 */
	public AutomatedCar(Transmission transmission, Sensor[] installedSensors, TRMotor[] auxiliaryMotorArray,
			BrickButton[] buttonArray) {
		super(transmission, installedSensors, auxiliaryMotorArray, buttonArray);
		this.currentAction = Action.STANDING;

	}

	/**
	 * Best constructor for the AutomatedCar
	 * The transmission is of type CloseLoopTransmission by default
	 * @param installedSensors: specific sensors installed on the robot
	 * @param auxiliaryMotorArray: auxiliary motors installed
	 * @param buttonArray: initialized buttons of the brick
	 * @param startPosition: start position of the robot
	 */
	private AutomatedCar(Sensor[] installedSensors, TRMotor[] auxiliaryMotorArray,
			BrickButton[] buttonArray, Area startPosition, double startHeading) {
		super(null, installedSensors, auxiliaryMotorArray, buttonArray);
		
		StatusBar bar = StatusBar.getStatusBar();
		bar.increaseProgress(100 / INSTANCE_ELEMENTS);
		
//		System.out.println(" Creating Car\n");
		Transmission _t = new ClosedLoopControlTransmission(TurtleRobot.getWheelDiameterCm(), TurtleRobot.getTrackWidthCm(), GYRO_PORT, this);
		_t.setLinearSpeed(LINEAR_SPEED);
		_t.setLinearAcceleration(LINEAR_ACCELERATION);
		this.setTransmission(_t);
		
		
		//setting the final listener to the sensors
		this.getSensorArray()[IR_SENS].setListener(new GeneralEnvironmentListener(this));
		

		this.initialArea = startPosition;
		this.targetArea = new Area(0, 0);
		this.initialHeading = startHeading;
		this.currentAction = Action.STANDING;
	}

	/**
	 * Builder for an automated car
	 * @param buttonArray: initialized buttons of the brick
	 * @param startPosition: start position of the robot
	 * @return New automated car reference to the same single instance
	 */
	public static AutomatedCar getAutomatedCar(Area startPosition, double startHeading) {

		Sensor[] sensors = new Sensor[TurtleRobot.getSensNum()];
		
		sensors[IR_SENS] = new InfraredSensor(IR_PORT, new NullListener(), IRModes.IR_DISTANCE);

		BrickButton[] buttons = new BrickButton[TurtleRobot.getMaxButtons()];

		//use the same instance of the listener or you'll have problems
		buttons[ENTER_BUTTON] = new BrickButton(ButtonType.ENTER, AreaSetter.getAreaSetter());
		buttons[UP_BUTTON] = new BrickButton(ButtonType.UP, AreaSetter.getAreaSetter());
		buttons[DOWN_BUTTON] = new BrickButton(ButtonType.DOWN, AreaSetter.getAreaSetter());
		buttons[ESCAPE_BUTTON] = new BrickButton(ButtonType.ESCAPE, AreaSetter.getAreaSetter());

		//these instances of the motors are useful
		TRMotor[] motors = new TRMotor[TurtleRobot.getAuxMotNum()];
		motors[LEFT_MOTOR] = new TRMotor(MotorPort.PORT_B);
		motors[RIGHT_MOTOR] = new TRMotor(MotorPort.PORT_C);
		return new AutomatedCar(sensors, motors, buttons, startPosition, startHeading);
	}
	
	
	@Deprecated
	/**
	 * The method makes the robot move from the place where the robot is to a destination area
	 * The movement is composed of two phases:
	 * 1 - horizontal movement to adjust x component
	 * 2 - vertical movement to adjust y component
	 * @param destitaion: destination of the movement
	 */
	public void travelToArea(Area destination) {
		//update of class fields
		targetArea = destination;
		//calculus of the mean point of the movement, where I switch between the two types of movement
		_targetArea = new Area(destination.getxCoord(), initialArea.getyCoord());
		//first approaching movement
		makeMovement();

		_targetArea = targetArea;

		//final movement
		makeMovement();
		this.waitCompleteAction();


	}

	/**
	 * The robot travels directly to the area
	 * without following a L path
	 * @param destitaion: destination of the movement
	 */
	@SuppressWarnings("deprecation")
	public void travelToAreaDirectly(Area destination) {
		this.getTransmission().getPilot().reset();
		targetArea = destination;
		//components of the movement
		double xSide = Math.abs(destination.getxCoord() - initialArea.getxCoord());
		double ySide = Math.abs(destination.getyCoord() - initialArea.getyCoord());
		//distance to be done to move directly to the destination
		double dist = Math.sqrt(Math.pow(xSide, 2) + Math.pow(ySide, 2));
		
		//update of the current partial start position
		PartialMovExtremesSingleton.getPartialMovExtremesSingleton().setPartialTarget(initialArea);
		//update of the current partial destination
		PartialMovExtremesSingleton.getPartialMovExtremesSingleton().setPartialTarget(targetArea);
		calculateTargetHeadingDirectly(dist, xSide);
		rotateToTargetHeading();
		this.waitCompleteAction();
		this.getTransmission().getPilot().reset();
		initialHeading = targetHeading;
		travelStraightToArea(dist);
		initialArea = targetArea;
		this.getTransmission().getPilot().reset();
		CurrentPositionSingleton.getCurrentPositionSingleton().setCurrentPosition(targetArea);
		
	}

	/**
	 * Method to make one of the two movements to reach the final target
	 * The method updates the area of localization of the robot and its heading at the end of it
	 */
	public void makeMovement() {
		//calculus of the heading of the target
		calculateTargetHeading();
		//		System.out.println("Target head: "+targetHeading);
		//rotating to reach the target heading
		rotateToTargetHeading();
		//waits for the robot to finish steering
		this.waitCompleteAction();
		//reaching the first target
		travelStrainghtToTargetArea();
		this.waitCompleteAction();
		//update of fields
		initialArea = _targetArea;
		initialHeading = targetHeading;
	}
	

	/**
	 * Calculus of the heading to reach to move to the target
	 */
	public void calculateTargetHeading() {

		if(initialArea.getxCoord() == _targetArea.getxCoord()) {
			if(initialArea.getyCoord() > _targetArea.getyCoord()) {
				//the robot moves vertically "downwards"
				targetHeading = 180;
			}
			else if(initialArea.getyCoord() < _targetArea.getyCoord()) {

				//the robot moves vertically "upwards"
				targetHeading = 0;
			}
		}
		else if (initialArea.getyCoord() == _targetArea.getyCoord()) {
			if(initialArea.getxCoord() > _targetArea.getxCoord()) {
				//the robot moves horizontally to the left
				targetHeading = -90;
			}
			else if(initialArea.getxCoord() < _targetArea.getxCoord()) {
				////the robot moves horizontally to the lright
				targetHeading = 90;
			}
		}

	}
	
	/**
	 * Calculus of the heading to reach to move to the target directly
	 * without moving on a L path 
	 * @param distance: distance between the two points
	 * @param xSide: x component of the movement
	 */
	public void calculateTargetHeadingDirectly(double distance, double xSide) {
		if(targetArea.getxCoord() > initialArea.getxCoord())  {
			if(targetArea.getyCoord() > initialArea.getxCoord()) {
				targetHeading = 90 - (Math.acos(xSide / distance) * RAD_TO_DEG);
			}
			else if(targetArea.getyCoord() < initialArea.getyCoord()) {
				targetHeading = 90 + (Math.acos(xSide / distance) * RAD_TO_DEG);
			}
			else {
				targetHeading = 90;
			}
		}
		else if(targetArea.getxCoord() < initialArea.getxCoord()) {
			
			if(targetArea.getyCoord() > initialArea.getxCoord()) {
				targetHeading = - 90 + (Math.acos(xSide / distance) * RAD_TO_DEG);
			}
			else if(targetArea.getyCoord() < initialArea.getyCoord()) {
				targetHeading = -90 - (Math.acos(xSide / distance) * RAD_TO_DEG);
			}
			else {
				targetHeading = -90;
			}
			
		}
		else {
			if(targetArea.getyCoord() > initialArea.getyCoord()) {
				targetHeading = 0;
			}
			else {
				targetHeading = 180;
			}
		}
 	}

	/**
	 * The robot start rotating until it reaches the target heading
	 */
	public void rotateToTargetHeading() {
		this.currentAction = Action.STEERING;
		((ClosedLoopControlTransmission) this.getTransmission()).steerToHeading(targetHeading);
		
	}

	/**
	 * The robot travels straightly to the target area, set as _targetArea
	 * The heading of the robot must be adjusted before using this method
	 */
	public void travelStrainghtToTargetArea() {
		double areasToTravel = 0.0;
		if(initialArea.getxCoord() == _targetArea.getxCoord()) {
			//move on y difference
			areasToTravel = Math.abs(_targetArea.getyCoord() - initialArea.getyCoord());
		}
		else if (initialArea.getyCoord() == _targetArea.getyCoord()) {
			//move on x difference
			areasToTravel = Math.abs(_targetArea.getxCoord() - initialArea.getxCoord());
		}
		else {
			//do not move
			areasToTravel = 0.0;
		}
		this.currentAction = Action.TRAVELING;
		this.moveOnDistanceSlowingDown((float) (areasToTravel * Area.getUnitLengthCm()));
		
	}
	
	/**
	 * The robot travels straightly to the target area
	 * The heading of the robot must be adjusted before using this method
	 * @param distance: length of the movement
	 */
	public void travelStraightToArea(double distance) {
		this.currentAction = Action.TRAVELING;
		this.moveOnDistanceSlowingDown((float) (distance * Area.getUnitLengthCm()));
		
	}
	
	






	/**
	 * @param initialArea the initialArea to set
	 */
	public void setInitialArea(Area initialArea) {
		this.initialArea = initialArea;
	}

	/**
	 * @param initialHeading the initialHeading to set
	 */
	public void setInitialHeading(double initialHeading) {
		this.initialHeading = initialHeading;
	}

	/**
	 * @param targetArea the targetArea to set
	 */
	public void setTargetArea(Area targetArea) {
		this.targetArea = targetArea;
	}

	/**
	 * @param targetHeading the targetHeading to set
	 */
	public void setTargetHeading(double targetHeading) {
		this.targetHeading = targetHeading;
	}

	/**
	 * @return the initialArea
	 */
	public Area getInitialArea() {
		return initialArea;
	}

	/**
	 * @return the initialHeading
	 */
	public double getInitialHeading() {
		return initialHeading;
	}

	/**
	 * @return the targetArea
	 */
	public Area getTargetArea() {
		return targetArea;
	}

	/**
	 * @return the targetHeading
	 */
	public double getTargetHeading() {
		return targetHeading;
	}


}
