package testdriving;

import static robocode.util.Utils.normalRelativeAngleDegrees;

import robocode.Robot;

public class Motor {
	
	private Robot robot = null;
	
	public Motor(Robot robot) {
		this.robot = robot;
	}

	public void turnTo(double bearing) {
		robot.turnRight(bearing);
	}

	public void goTo(double distance) {
		robot.ahead(distance);
	}

}
