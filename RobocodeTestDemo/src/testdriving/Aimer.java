package testdriving;

import static robocode.util.Utils.normalRelativeAngleDegrees;

import robocode.Robot;

public class Aimer {

	private Robot robot = null;
	
	public Aimer(Robot robot) {
		this.robot = robot;
	}

	public void gunTo(double bearing) {
		robot.turnGunRight(normalRelativeAngleDegrees(bearing + (robot.getHeading() - robot.getRadarHeading())));
	}

	public void turn(double gunTurnAmt) {
		robot.turnGunRight(gunTurnAmt);
	}

}


