package lego.robot.components;

import lego.robot.GUI.StatusBar;
import lego.robot.components.buttons.ButtonType;
import lego.robot.interfaces.EV3HardwareInfo;
import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;

/**
 * Class to manage a button of the brick
 * @author Programming
 *
 */
public class BrickButton extends Thread implements EV3HardwareInfo {
	
	/**
	 * Type of the button
	 */
	private ButtonType t;
	/**
	 * Listener of events for the button
	 */
	private KeyListener listener;
	
	/**
	 * Constructor of the button
	 * @param bType: type of button to be initialized
	 */
	public BrickButton(ButtonType bType, KeyListener listener) {
		StatusBar bar = StatusBar.getStatusBar();
		bar.increaseProgress(100 / INSTANCE_ELEMENTS);
		
//		System.out.println(" Creating\n Button\n");
		
		this.t = bType;
		this.addKeyListener(listener);
		this.setDaemon(true);

	}
	
	
	public int getButtonId() {
		int id = 0;
		switch(t) {
		case DOWN:
			id = Button.ID_DOWN;
			break;
		case ENTER:
			id = Button.ID_ENTER;
			break;
		case ESCAPE:
			id = Button.ID_ESCAPE;
			break;
		case LEFT:
			id = Button.ID_LEFT;
			break;
		case RIGHT:
			id = Button.ID_RIGHT;
			break;
		case UP:
			id = Button.ID_UP;
			break;
		}
		return id;
	}


	public boolean isDown() {
		boolean down = false;
		switch(t) {
		case DOWN:
			down = Button.DOWN.isDown();
			break;
		case ENTER:
			down = Button.ENTER.isDown();
			break;
		case ESCAPE:
			down = Button.ESCAPE.isDown();
			break;
		case LEFT:
			down = Button.LEFT.isDown();
			break;
		case RIGHT:
			down = Button.RIGHT.isDown();
			break;
		case UP:
			down = Button.UP.isDown();
			break;
		}
		return down;
	}


	public boolean isUp() {
		boolean up = false;
		switch(t) {
		case DOWN:
			up = Button.DOWN.isUp();
			break;
		case ENTER:
			up = Button.ENTER.isUp();
			break;
		case ESCAPE:
			up = Button.ESCAPE.isUp();
			break;
		case LEFT:
			up = Button.LEFT.isUp();
			break;
		case RIGHT:
			up = Button.RIGHT.isUp();
			break;
		case UP:
			up = Button.UP.isUp();
			break;
		}
		return up;
	}


	public void waitForPress() {
		switch(t) {
		case DOWN:
			Button.DOWN.waitForPress();
			break;
		case ENTER:
			Button.ENTER.waitForPress();
			break;
		case ESCAPE:
			Button.ESCAPE.waitForPress();
			break;
		case LEFT:
			Button.LEFT.waitForPress();
			break;
		case RIGHT:
			Button.RIGHT.waitForPress();
			break;
		case UP:
			Button.UP.waitForPress();
			break;
		}
	}

	public void waitForPressAndRelease() {
		switch(t) {
		case DOWN:
			Button.DOWN.waitForPressAndRelease();
			break;
		case ENTER:
			Button.ENTER.waitForPressAndRelease();
			break;
		case ESCAPE:
			Button.ESCAPE.waitForPressAndRelease();
			break;
		case LEFT:
			Button.LEFT.waitForPressAndRelease();
			break;
		case RIGHT:
			Button.RIGHT.waitForPressAndRelease();
			break;
		case UP:
			Button.UP.waitForPressAndRelease();
			break;
		}
	}


	public void addKeyListener(KeyListener listener) {
		this.listener = listener;

	}


	/**
	 * @param event: 0 -> Press event; 1 -> Release event
	 */
	public void simulateEvent(int event) {
		Key k = null;
		switch(t) {
		case DOWN:
			k = Button.DOWN;
			break;
		case ENTER:
			k = Button.ENTER;
			break;
		case ESCAPE:
			k = Button.ESCAPE;
			break;
		case LEFT:
			k = Button.LEFT;
			break;
		case RIGHT:
			k = Button.RIGHT;
			break;
		case UP:
			k = Button.UP;
			break;
		}
		
		switch(event) {
		case 0:
			listener.keyPressed(k);
			break;
	
		case 1:
			listener.keyReleased(k);
			break;
		default:
			listener.keyPressed(k);
		}

	}


	public String getButtonName() {
		String name = "";
		switch(t) {
		case DOWN:
			name = "Down";
			break;
		case ENTER:
			name = "Enter";
			break;
		case ESCAPE:
			name = "Escape";
			break;
		case LEFT:
			name = "Left";
			break;
		case RIGHT:
			name = "Right";
			break;
		case UP:
			name = "Up";
			break;
		}
		return name;
	}
	
	@Override
	/**
	 * Main method of the thread: checks the sensor status
	 */
	public void run() {
	
		boolean wasDown;
		wasDown = false;
		Key k = null;
		switch(t) {
		case DOWN:
			k = Button.DOWN;
			break;
		case ENTER:
			k = Button.ENTER;
			break;
		case ESCAPE:
			k = Button.ESCAPE;
			break;
		case LEFT:
			k = Button.LEFT;
			break;
		case RIGHT:
			k = Button.RIGHT;
			break;
		case UP:
			k = Button.UP;
			break;
		}
		
		while(true) {
			if((this.isDown()) && (wasDown == false)) {
				wasDown = true;
				listener.keyPressed(k);
			}
			
			if((this.isUp()) && (wasDown == true)) {
				wasDown = false;
				listener.keyReleased(k);
			}
			
		}
	}

}
