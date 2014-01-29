package octolink.entity.link;

import gameframework.base.MoveStrategyStraightLine;
import gameframework.base.SpeedVector;
import gameframework.game.GameMovableDriver;
import gameframework.game.GameMovableDriverDefaultImpl;

import java.awt.Point;

import octolink.entity.Creep;

public class DefenderState extends AbstractState {

	@Override
	public String getValue() {
		return "shield-";
	}

	@Override
	public int parry(int damage) {
		return 0;
	}

	@Override
	public void collideFront(Link l, Creep c) {
		Point p = l.getPosition();
		SpeedVector creepSpeedVector = c.getSpeedVector();
		GameMovableDriver oldLinkDriver = l.getDriver();

		GameMovableDriverDefaultImpl linkDriver = new GameMovableDriverDefaultImpl();
		Point destination = new Point((int) (p.getX() + creepSpeedVector
				.getDirection().getX() * 20),
				(int) (p.getY() + creepSpeedVector.getDirection().getY() * 20));

		linkDriver.setStrategy(new MoveStrategyStraightLine(p, destination));
		l.setDriver(linkDriver);
		for (int i = 0; i < 3; ++i)
			l.oneStepMove();
		l.setDriver(oldLinkDriver);
	}

	@Override
	public int strike() {
		return 0;
	}
	
}
