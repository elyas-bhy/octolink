package octolink.entity.link;

import gameframework.base.MoveStrategyStraightLine;
import gameframework.base.SpeedVector;
import gameframework.base.SpeedVectorDefaultImpl;
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
	public int strike() {
		return 0;
	}

	@Override
	public int parryFront(int damage) {
		return 0;
	}

	@Override
	public void collideFront(Link l, Creep c) {
		Point p = l.getPosition();
		SpeedVector creepSpeedVector = c.getSpeedVector();
		
		GameMovableDriver oldCreepDriver = c.getDriver();

		c.setSpeedVector(new SpeedVectorDefaultImpl(
				new Point((int)-creepSpeedVector.getDirection().getX(),
						(int)-creepSpeedVector.getDirection().getY()),
						creepSpeedVector.getSpeed()*10));

		for (int i = 0; i < 3; ++i) {
			c.oneStepMove();
		}
		
		c.setDriver(oldCreepDriver);
		c.setSpeedVector(creepSpeedVector);
		l.parryFront(c.damage());
	}
	
}
