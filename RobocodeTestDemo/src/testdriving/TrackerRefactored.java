/**
 * Copyright (c) 2001-2018 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 */
package testdriving;


import java.awt.Color;

import robocode.HitRobotEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;


/**
 * Tracker - a sample robot by Mathew Nelson.
 * <p>
 * Locks onto a robot, moves close, fires when close.
 *
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 */
public class TrackerRefactored extends Robot {
	int count = 0; 
	double gunTurnAmt;
	private TargetAcquisition targetAcquisition = new TargetAcquisition();
	private Aimer aimer = new Aimer(this);
	private Motor motor = new Motor(this);
	private Cannon cannon = new Cannon(this);
	private Radar radar = new Radar(this);

	/**
	 * run:  Tracker's main run function
	 */
	public void run() {
		setBodyColor(new Color(128, 128, 50));
		setGunColor(new Color(50, 50, 20));
		setRadarColor(new Color(200, 200, 70));
		setScanColor(Color.white);
		setBulletColor(Color.blue);

		setAdjustGunForRobotTurn(true);
		gunTurnAmt = 10;
		
		while (true) {
			aimer.turn(gunTurnAmt);
			count++;
			if (count > 2) {
				gunTurnAmt = -10;
			}
			if (count > 5) {
				gunTurnAmt = 10;
			}
			if (count > 11) {
				targetAcquisition.loseTarget();
			}
		}
	}

	/**
	 * onScannedRobot:  Here's the good stuff
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		
		getHeading();

		if (!targetAcquisition.isCurrentTarget(e.getName())) {
			return;
		} else {
			targetAcquisition.acquireTarget(e.getName());
		}

		count = 0;
		if (e.getDistance() > 150) {
			aimer.gunTo(e.getBearing());
			motor.turnTo(e.getBearing());
			motor.goTo(e.getDistance() - 140);
			return;
		}

		aimer.gunTo(e.getBearing());
		cannon.fire();

		if (e.getDistance() < 100) {
			if (e.getBearing() > -90 && e.getBearing() <= 90) {
				motor.goTo(-40);
			} else {
				motor.goTo(40);
			}
		}
		radar.scan();
	}

	/**
	 * onHitRobot:  Set him as our new target
	 */
	public void onHitRobot(HitRobotEvent e) {
		if (!targetAcquisition.isCurrentTarget(e.getName())) {
			out.println("Tracking " + e.getName() + " due to collision");
		}
		targetAcquisition.acquireTarget(e.getName());

		aimer.gunTo(e.getBearing());
		cannon.fire();
		motor.goTo(-50);
	}

	/**
	 * onWin:  Do a victory dance
	 */
	public void onWin(WinEvent e) {
		for (int i = 0; i < 50; i++) {
			turnRight(30);
			turnLeft(30);
		}
	}
}
