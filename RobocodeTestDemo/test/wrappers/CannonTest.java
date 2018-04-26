package wrappers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import robocode.Robot;
import wrappers.Cannon;

public class CannonTest {
	@Mock Robot robot;
	@Captor ArgumentCaptor<Double> doubleCapture;
	
	Cannon underTest;
	
	@Before public void setup() {
		initMocks(this);
		underTest = new Cannon(robot);
	}
	
	@Test public void shouldFireWithPower3() {
		underTest.fire();
		verify(robot).fire(doubleCapture.capture());
		assertThat(doubleCapture.getValue(), equalTo(3.0));
	}
	
}
