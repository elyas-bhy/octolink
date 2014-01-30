package octolink.entity.link;

import gameframework.base.SpeedVector;
import gameframework.base.SpeedVectorDefaultImpl;

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
		SpeedVector creepSpeedVector = c.getSpeedVector();
		c.setSpeedVector(new SpeedVectorDefaultImpl(
				new Point((int)-creepSpeedVector.getDirection().getX(),
						  (int)-creepSpeedVector.getDirection().getY()),
						  creepSpeedVector.getSpeed()*10));

		for (int i = 0; i < 3; ++i) {
			c.oneStepMove();
		}
		Point invertDirection = new Point((int)-c.getSpeedVector().getDirection().getX(),
										(int)-c.getSpeedVector().getDirection().getY());
		c.setSpeedVector(new SpeedVectorDefaultImpl(invertDirection, creepSpeedVector.getSpeed()));
		l.parryFront(c.damage());
	}
	
}
