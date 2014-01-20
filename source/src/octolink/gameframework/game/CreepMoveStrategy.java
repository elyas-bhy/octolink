package octolink.gameframework.game;

import gameframework.base.MoveStrategy;
import gameframework.base.SpeedVector;
import gameframework.base.SpeedVectorDefaultImpl;

import java.awt.Point;
import java.util.Random;

import octolink.entity.WarriorCreep;

public class CreepMoveStrategy implements MoveStrategy {
	
	private SpeedVector currentMove = new SpeedVectorDefaultImpl(new Point(0, 0));
	private WarriorCreep creep;
	
	public CreepMoveStrategy(WarriorCreep c) {
		creep = c;
	}

	static Random random = new Random();

	public SpeedVector getSpeedVector() {
		int i = random.nextInt(5);

		switch (i) {
		case 0:
			currentMove.setDirection(new Point(1, 0));
			break;
		case 1:
			currentMove.setDirection(new Point(-1, 0));
			break;
		case 2:
			currentMove.setDirection(new Point(0, -1));
			break;
		case 3:
			currentMove.setDirection(new Point(0, 1));
			break;
		}
		return currentMove;
	}
}
