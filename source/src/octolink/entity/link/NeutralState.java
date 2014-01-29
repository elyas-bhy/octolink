package octolink.entity.link;

import gameframework.base.MoveStrategyStraightLine;
import gameframework.base.SpeedVector;
import gameframework.game.GameMovableDriver;
import gameframework.game.GameMovableDriverDefaultImpl;

import java.awt.Point;

import octolink.entity.Creep;

public class NeutralState extends AbstractState {

	@Override
	public String getValue() {
		return "";
	}

	@Override
	public void collide(AbstractLink l, Creep c) {
		Point a = l.getPosition();
		SpeedVector creepSpeedVector = c.getSpeedVector();
		GameMovableDriver oldLinkDriver = l.getDriver();
		
		GameMovableDriverDefaultImpl linkDriver = new GameMovableDriverDefaultImpl();
		Point destination = new Point(
					(int) (a.getX() + creepSpeedVector.getDirection().getX() * 100),
					(int) (a.getY() + creepSpeedVector.getDirection().getY() * 100));

		linkDriver.setStrategy(new MoveStrategyStraightLine(a, destination));
		l.setDriver(linkDriver);
		for(int i = 0; i < 3; ++i)
			l.oneStepMove();
		l.setDriver(oldLinkDriver);
	}

	@Override
	public int parry(int damage) {
		return 0 - damage;
	}

	@Override
	public int strike() {
		return 0;
	}

	@Override
	public void collideFront(AbstractLink l, Creep c) {
		collide(l, c);		
	}

}
