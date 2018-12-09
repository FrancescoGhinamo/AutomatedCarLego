package lego.robot.GUI;

import lego.robot.interfaces.EV3HardwareInfo;
import lejos.hardware.lcd.LCD;

/**
 * This class represent a status bar to show the progress of an operation
 * it uses Singleton pattern
 * @author Programming
 *
 */
public class StatusBar implements EV3HardwareInfo {

	/**
	 * X coordinate of the bar (top left corner)
	 */
	public static final int X_COORD = 0;
	/**
	 * Y coordinate of the bar (top left corner)
	 */
	public static final int Y_COORD = 100;
	/**
	 * Height of the bar
	 */
	public static final int HEIGHT = 15;
	/**
	 * Quantity to increase of 1% the progress on the status bar
	 */
	public static final float INCREASE_1_PERCENT = (float) SCREEN_WIDTH / 100;
	
	
	/**
	 * Local static instance of the StatusBar
	 */
	private static StatusBar singletonBar;
	/**
	 * Width of the bar
	 */
	private int width;
	
	/**
	 * Default constructor
	 */
	private StatusBar() {
		width = 0;
	}
	
	/**
	 * Returns the single instance of the status bar or creates a new one if there is no
	 * @return Returns the single instance of the status bar
	 */
	public static synchronized StatusBar getStatusBar() {
		if(singletonBar == null) {
			singletonBar = new StatusBar();
		}
		return singletonBar;
	}
	
	/**
	 * Increases the percentage of the progress of the specified percentage quantity
	 * @param quantity
	 */
	public void increaseProgress(float quantity) {
		clearRect(X_COORD, Y_COORD, SCREEN_WIDTH - 1, HEIGHT);
		drawRect(X_COORD, Y_COORD, SCREEN_WIDTH - 1, HEIGHT);
		width = (int) ((getProgress() + quantity) * INCREASE_1_PERCENT);
		fillRect(X_COORD, Y_COORD, width, HEIGHT);
		
	}
	
	/**
	 * Method to set the progress of the process on the status bar
	 * @param state: percentage (0 - 100) indicating the state of the process
	 */
	public void setProgress(float state) {
		clearRect(X_COORD, Y_COORD, SCREEN_WIDTH - 1, HEIGHT);
		drawRect(X_COORD, Y_COORD, SCREEN_WIDTH - 1, HEIGHT);
		width = (int) (state * INCREASE_1_PERCENT);
		fillRect(X_COORD, Y_COORD, width, HEIGHT);
	}
	
	/**
	 * Returns the percentage of the advancing of the process
	 * @return Returns the percentage of the advancing of the process (0 - 100)
	 */
	public float getProgress() {
		return (float) (width / INCREASE_1_PERCENT);
	}

	/**
	 * Method to draw a filled rectangle on the screen
	 * @param x: x coordinate of the top left corner
	 * @param y: y coordinate of the top left corner
	 * @param w: width of the rectangle
	 * @param h: height of the rectangle
	 */
	public void fillRect(int x, int y, int w, int h) {
		
		for(int _y = y; _y <= y + h; _y++) {
			for(int _x = x; _x <= x + w; _x++) {
				LCD.setPixel(_x, _y, 1);
			}
		}
		
	}
	
	/**
	 * Method to draw an empty rectangle on the screen
	 * @param x: x coordinate of the top left corner
	 * @param y: y coordinate of the top left corner
	 * @param w: width of the rectangle
	 * @param h: height of the rectangle
	 */
	public void drawRect(int x, int y, int w, int h) {
		//first line of the rectangle, y is constant
		for(int _x = x; _x <= x + w; _x++) {
			LCD.setPixel(_x, y, 1);
		}
		//extreme points of the rectangle (vertical sides), y changes
		for(int _y = y; _y <= y + h; _y++) {
			LCD.setPixel(x, _y, 1);
			LCD.setPixel(x + w, _y, 1);
		}
		//last line of the rectangle, y is constant
		for(int _x = x; _x <= x + w; _x++) {
			LCD.setPixel(_x, y + h, 1);
		}
	}
	
	/**
	 * Method to clear the region of the status bar
	 * @param x: x coordinate of the top left corner
	 * @param y: y coordinate of the top left corner
	 * @param w: width of the rectangle
	 * @param h: height of the rectangle
	 */
	public void clearRect(int x, int y, int w, int h) {
		for(int _y = y; _y <= y + h; _y++) {
			for(int _x = x; _x <= x + w; _x++) {
				LCD.setPixel(_x, _y, 0);
			}
		}
	}
	
}
