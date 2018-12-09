package testing;

import lego.robot.GUI.StatusBar;
import lejos.hardware.Button;

/**
 * Class to test the behavior of the StatusBar
 * @author Programming
 *
 */
public class StatusBarTest {

	public static void main(String[] args) {
		
		StatusBar bar = StatusBar.getStatusBar();
		bar.setProgress(50);
		Button.waitForAnyPress();
		bar.increaseProgress(10);
		Button.waitForAnyPress();
		bar.setProgress(100);
		Button.waitForAnyPress();
		bar.setProgress(75);
		Button.waitForAnyPress();

	}

}
