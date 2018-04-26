package wrappers;

import robocode.Robot;

public class Cannon {

	private Robot robot;

	public Cannon(Robot robot) {
		this.robot = robot;
	}

	public void fire() {
		robot.fire(3);
	}

}
