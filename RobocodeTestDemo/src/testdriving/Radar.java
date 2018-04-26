package testdriving;

import robocode.Robot;

public class Radar {

	private Robot robot;

	public Radar(Robot robot) {
		this.robot = robot;
	}

	public void scan() {
		robot.scan();
	}

}
