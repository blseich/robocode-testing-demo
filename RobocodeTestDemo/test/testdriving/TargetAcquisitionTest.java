package testdriving;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Before;
import org.junit.Test;

import testdriving.TargetAcquisition;

public class TargetAcquisitionTest {
		
	private static final String CURRENT_TARGET = "current target";
	private static final String NEW_TARGET = "new target";
	TargetAcquisition underTest;
	
	@Before public void setup() {
		underTest = new TargetAcquisition();
	}
	
	@Test public void shouldReturnFalseIfNoTargetDefined() {
		assertThat(underTest.isCurrentTarget(NEW_TARGET), equalTo(true));
	}
	
	@Test public void shouldReturnTrueIfTargetIsAlreadyAcquired() {
		underTest.acquireTarget(CURRENT_TARGET);
		assertThat(underTest.isCurrentTarget(CURRENT_TARGET), equalTo(true));
	}
	
	@Test public void shouldReturnFalseIfTargetIsNotTheAlreadyAcquiredTarget() {
		underTest.acquireTarget(CURRENT_TARGET);
		assertThat(underTest.isCurrentTarget(NEW_TARGET), equalTo(false));
	}
	
	@Test public void shouldEraseCurrentTarget() {
		underTest.acquireTarget(CURRENT_TARGET);
		underTest.loseTarget();
		assertThat(underTest.isCurrentTarget(NEW_TARGET), equalTo(true));
	}
}
