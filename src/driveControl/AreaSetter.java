package driveControl;

import lego.robot.interfaces.ButtonIDs;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.hardware.lcd.LCD;
import localization.referenceSystem.Area;

/**
 * Object that allows to interface the robot to the rest of the program
 * in the phase of setting robot path
 * @author Programming
 *
 */
public class AreaSetter implements KeyListener, ButtonIDs {
	
	/**
	 * AreaSetter to use Singleton pattern
	 */
	private static AreaSetter as;

	/**
	 * Registers if an action has been done
	 */
	private boolean actionDone = false;
	/**
	 * Indicates whether the program can continue
	 */
	private boolean run = false;
	
	/**
	 * Value the user is inserting from buttons
	 */
	private int val = 0;
	
	/**
	 * Constructor to create a single instance of AreaSetter
	 */
	private AreaSetter() {
		
	}
	
	/**
	 * Gets the instance of the single AreaSetter
	 * @return
	 */
	public static AreaSetter getAreaSetter() {
		if(as == null) {
			as = new AreaSetter();
		}
		return as;
	}
	
	/**
	 * Gets from user an area
	 * @return An area
	 */
	public Area getArea() {
		actionDone = false;
		run = false;
		val = 0;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println(e.toString());
		}
		System.out.println("\n\n\n Insert area x: ");
		while(!run) { }
		int x = val;
		val = 0;
		System.out.println(" Insert area y: ");
		run = false;
		while(!run) { }
		int y = val;
		
		return new Area(x, y);		
	}
	
	@Override
	public void keyPressed(Key k) {
		if(run == false) {
			if(k.getId() == UP) {
				actionDone = true;
				val++;
				LCD.clearDisplay();
				System.out.println("\n\t"+val);
			}
			
			if(k.getId() == DOWN) {
				actionDone = true;
				val--;
				LCD.clearDisplay();
				System.out.println("\n\t"+val);
			}
			
			if(k.getId() == ENTER) {
				LCD.clearDisplay();
				if(actionDone) {
					run = true;
				}
			}
			
			if(k.getId() == ESCAPE) {
				System.exit(1);
			}
		}
		
	}

	@Override
	public void keyReleased(Key k) {
		// TODO Auto-generated method stub
//		System.out.println("ID: "+k.getId());
		
	}

	
}
