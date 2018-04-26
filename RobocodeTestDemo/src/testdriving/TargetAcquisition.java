package testdriving;

import robocode.Robot;
import robocode.ScannedRobotEvent;

public class TargetAcquisition {

	private String target = "";
	
	public boolean isCurrentTarget(String scannedRobotName) {
		 return target == "" || scannedRobotName.equals(target);
	}

	public void acquireTarget(String targetName) {
		target = targetName;
	}

	public void loseTarget() {
		target = "";
	}
	
}
