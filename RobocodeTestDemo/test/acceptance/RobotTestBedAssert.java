package acceptance;

import robocode.util.Utils;


/**
 * Provides some additional Assert methods for use in Robocode.
 *
 * @author Philip Johnson (original)
 * @author Pavel Savara (contributor)
 */
public class RobotTestBedAssert extends org.junit.Assert {

	/**
	 * Asserts that the two values are "sufficiently close".
	 * Define sufficiently close using Utils.NEAR_DELTA.
	 *
	 * @param value1 First value.
	 * @param value2 Second value.
	 */
	public static void assertNear(double value1, double value2) {
		org.junit.Assert.assertEquals(value1, value2, Utils.NEAR_DELTA);
	}
}