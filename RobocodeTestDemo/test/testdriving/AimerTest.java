package testdriving;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static robocode.util.Utils.normalRelativeAngleDegrees;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;

import robocode.Robot;
import testdriving.Aimer;

public class AimerTest {

	private static final double TARGET_BEARING = 120.0;
	private static final double CURRENT_RADAR_HEADING = 50.0;
	private static final double CURRENT_ROBOT_HEADING = 80.0;
	private static final double DEGREES_TO_TARGET = TARGET_BEARING + (CURRENT_ROBOT_HEADING - CURRENT_RADAR_HEADING);
	private static final double SMALL_GUN_TURN = 15.0;
	
	@Mock Robot robot;
	@Captor ArgumentCaptor<Double> doubleCapture;
	
	Aimer underTest;
	
	@Before public void setup() {
		initMocks(this);
		underTest = new Aimer(robot);
	}
	
	@Test public void shouldTurnRightSetAmount(){
		underTest.turn(15.0);
		verify(robot).turnGunRight(doubleCapture.capture());
		assertThat(doubleCapture.getValue(), equalTo(SMALL_GUN_TURN));
	}
	
	@Test public void shouldTurnToBearingBasedOnCurrentHeadingAndRadarPosition(){
		when(robot.getHeading()).thenReturn(CURRENT_ROBOT_HEADING);
		when(robot.getRadarHeading()).thenReturn(CURRENT_RADAR_HEADING);
		underTest.gunTo(TARGET_BEARING);
		verify(robot).turnGunRight(doubleCapture.capture());
		assertThat(doubleCapture.getValue(), equalTo(normalRelativeAngleDegrees(DEGREES_TO_TARGET)));
	}
}
