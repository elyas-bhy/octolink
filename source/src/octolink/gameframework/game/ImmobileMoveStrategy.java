package octolink.gameframework.game;

import gameframework.base.MoveStrategy;
import gameframework.base.SpeedVector;
import gameframework.base.SpeedVectorDefaultImpl;

public class ImmobileMoveStrategy implements MoveStrategy {

	@Override
	public SpeedVector getSpeedVector() {
		return SpeedVectorDefaultImpl.createNullVector();
	}

}
