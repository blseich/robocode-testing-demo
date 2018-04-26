package testdriving;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;
import testdriving.Aimer;
import testdriving.Cannon;
import testdriving.Motor;
import testdriving.Radar;
import testdriving.TargetAcquisition;
import testdriving.TrackerRefactored;

@RunWith(MockitoJUnitRunner.class)
public class TrackerRefactoredTest {
	
	@Mock TargetAcquisition targetAcquisition;
	@Mock Aimer aimer;
	@Mock Motor motor;
	@Mock Cannon cannon;
	@Mock Radar radar;
	@Mock PrintStream out;
	
	@Captor ArgumentCaptor<String> stringCapture;
	@Captor ArgumentCaptor<Double> doubleCapture;
	
	@Mock ScannedRobotEvent scannedEvent;
	HitRobotEvent hitEvent = new HitRobotEvent("target", 0.0, 100.0, false);
	
	@InjectMocks TrackerRefactored underTest;
	
	@Before public void setup() {
		when(scannedEvent.getName()).thenReturn("target");
		when(scannedEvent.getBearing()).thenReturn(135.0);
		when(targetAcquisition.isCurrentTarget("target")).thenReturn(true);
	}

	
	@Test public void shouldReturnIfScannedRobotIsNotCurrentTarget(){
		when(scannedEvent.getName()).thenReturn("not target");
		when(targetAcquisition.isCurrentTarget("not target")).thenReturn(false);
		underTest.onScannedRobot(scannedEvent);
		verifyZeroInteractions(aimer);
		verifyZeroInteractions(motor);
		verifyZeroInteractions(cannon);
	}
	
	@Test public void shouldAcquireTargetIfIsCurrentTarget() {
		underTest.onScannedRobot(scannedEvent);
		verify(targetAcquisition).acquireTarget(stringCapture.capture());
		assertThat(stringCapture.getValue(), equalTo("target"));
	}
	
	@Test public void shouldTurnTowardTargetIfAcquiredIs150PixelsOrFartherAway() {
		when(scannedEvent.getDistance()).thenReturn(151.0);
		underTest.onScannedRobot(scannedEvent);
		verify(motor).turnTo(doubleCapture.capture());
		assertThat(doubleCapture.getValue(), equalTo(135.0));
	}
	
	@Test public void shouldMoveTowardTargetIfAcquiredIs150pOrFartherAway() {
		when(scannedEvent.getDistance()).thenReturn(151.0);
		underTest.onScannedRobot(scannedEvent);
		verify(motor).goTo(doubleCapture.capture());
		assertThat(doubleCapture.getValue(), equalTo(151.0 - 140.0));
	}
	
	@Test public void shouldTurnGunTowardAcquiredTarget() {
		underTest.onScannedRobot(scannedEvent);
		verify(aimer).gunTo(doubleCapture.capture());
		assertThat(doubleCapture.getValue(), equalTo(135.0));
	}
	
	@Test public void shouldFireCannonIfWithin150pOfTarget() {
		when(scannedEvent.getDistance()).thenReturn(149.0);
		underTest.onScannedRobot(scannedEvent);
		verify(cannon).fire();
	}
	
	@Test public void shouldBackupIfWithin100pOfTargetAndTargetsBearingIsInFrontOfRobot() {
		when(scannedEvent.getDistance()).thenReturn(99.0);
		when(scannedEvent.getBearing()).thenReturn(0.0);
		underTest.onScannedRobot(scannedEvent);
		verify(motor).goTo(doubleCapture.capture());
		assertThat(doubleCapture.getValue(), equalTo(-40.0));
	}
	
	@Test public void shouldBackupIfWithin100pOfTargetAndTargetsBearingIsInBehindOfRobot() {
		when(scannedEvent.getDistance()).thenReturn(99.0);
		when(scannedEvent.getBearing()).thenReturn(180.0);
		underTest.onScannedRobot(scannedEvent);
		verify(motor).goTo(doubleCapture.capture());
		assertThat(doubleCapture.getValue(), equalTo(40.0));
	}
	
	@Test public void shouldAcquireNewTargetIfCollisionOccurs() {
		HitRobotEvent newTargetHitEvent = new HitRobotEvent("new target", 0.0, 100.0, false);
		when(targetAcquisition.isCurrentTarget("new target")).thenReturn(false);
		underTest.onHitRobot(newTargetHitEvent);
		verify(targetAcquisition).acquireTarget(stringCapture.capture());
		assertThat(stringCapture.getValue(), equalTo("new target"));
	}
	
	@Test public void shouldTurnGunToNewTargetIfCollisionOccurs() {
		underTest.onHitRobot(hitEvent);
		verify(aimer).gunTo(doubleCapture.capture());
		assertThat(doubleCapture.getValue(), equalTo(0.0));
	}
	
	@Test public void shouldFireCannonIfCollisionOccurs() {
		underTest.onHitRobot(hitEvent);
		verify(aimer).gunTo(doubleCapture.capture());
		assertThat(doubleCapture.getValue(), equalTo(0.0));
	}
	
	@Test public void shouldBackup50pIfCollisionOccurs() {
		underTest.onHitRobot(hitEvent);
		verify(motor).goTo(doubleCapture.capture());
		assertThat(doubleCapture.getValue(), equalTo(-50.0));
	}
}
