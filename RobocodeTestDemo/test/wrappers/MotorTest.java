package wrappers;

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

import robocode.Robot;
import wrappers.Motor;

public class MotorTest {
	private static final double TARGET_BEARING = 120.0;
	private static final double SMALL_DISTANCE = 15.0;
	
	@Mock Robot robot;
	@Captor ArgumentCaptor<Double> doubleCapture;
	
	Motor underTest;
	
	@Before public void setup() {
		initMocks(this);
		underTest = new Motor(robot);
	}
	
	@Test public void shouldMoveAheadSetAmount(){
		underTest.goTo(15.0);
		verify(robot).ahead(doubleCapture.capture());
		assertThat(doubleCapture.getValue(), equalTo(SMALL_DISTANCE));
	}
	
	@Test public void shouldTurnToBearingBasedOnCurrentHeadingAndRadarPosition(){
		underTest.turnTo(TARGET_BEARING);
		verify(robot).turnRight(doubleCapture.capture());
		assertThat(doubleCapture.getValue(), equalTo(TARGET_BEARING));
	}
}
