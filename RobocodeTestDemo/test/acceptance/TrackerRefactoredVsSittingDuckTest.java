package acceptance;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import robocode.BattleResults;
import robocode.control.events.BattleCompletedEvent;


public class TrackerRefactoredVsSittingDuckTest extends RobotTestBed {	
	
	@Override
	public String getRobotNames() {
		return "sample.SittingDuck,testdriving.TrackerRefactored";
	}

	@Override
	public int getNumRounds(){
		return 100;
	}
	
	@Override
	public void onBattleCompleted(BattleCompletedEvent event) {
		BattleResults[] battleResults = event.getIndexedResults();
		BattleResults trackerRefactored = battleResults[1];
		String robotName = trackerRefactored.getTeamLeaderName();
		System.out.println(robotName + " firsts = " + trackerRefactored.getFirsts());
		MatcherAssert.assertThat(robotName, Matchers.equalTo("testdriving.TrackerRefactored*"));
		MatcherAssert.assertThat(trackerRefactored.getFirsts(), Matchers.greaterThan(50));
	}
	
}
