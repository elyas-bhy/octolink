package octolink.gameframework.game;

import java.awt.Point;

import gameframework.base.MoveStrategy;
import gameframework.base.SpeedVector;
import gameframework.base.SpeedVectorDefaultImpl;

public class ImmobileMoveStrategy implements MoveStrategy {

	@Override
	public SpeedVector getSpeedVector() {
		return new SpeedVectorDefaultImpl(new Point());
	}

}
