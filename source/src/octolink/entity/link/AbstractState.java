package octolink.entity.link;

import gameframework.base.MoveStrategyStraightLine;
import gameframework.base.SpeedVector;
import gameframework.game.GameMovableDriver;
import gameframework.game.GameMovableDriverDefaultImpl;

import java.awt.Point;
import java.awt.Rectangle;

import octolink.entity.Creep;
import octolink.util.Sprite;

public abstract class AbstractState implements LinkState {

	protected int transitionType;

	@Override
	public int getTransitionType() {
		return transitionType;
	}

	@Override
	public void setTransitionType(int type) {
		transitionType = type;
	}

	public int parry(int damage) {
		return damage;
	}

	public int parryFront(int damage) {
		return parry(damage);
	}

	@Override
	public void collide(Link l, Creep c) {
		if(l.getInvulnerableTicks() == 0) {
			Point p = l.getPosition();
			SpeedVector creepSpeedVector = c.getSpeedVector();
			GameMovableDriver oldLinkDriver = l.getDriver();

			GameMovableDriverDefaultImpl linkDriver = new GameMovableDriverDefaultImpl();
			Point destination = new Point(
					(int) (p.getX() + creepSpeedVector.getDirection().getX() * 100),
					(int) (p.getY() + creepSpeedVector.getDirection().getY() * 100));

			linkDriver.setStrategy(new MoveStrategyStraightLine(p, destination));
			l.setDriver(linkDriver);
			for (int i = 0; i < 3; ++i)
				l.oneStepMove();
			l.setDriver(oldLinkDriver);
			l.parry(c.damage());
			l.setInvulnerableTicks();
		}
	}

	@Override
	public void collideFront(Link l, Creep c) {
		collide(l, c);		
	}

	@Override
	public Rectangle getBoundingBox(Sprite sprite, Point p) {
		return new Rectangle(
				(int) (sprite.getWidth() * sprite.getScale()),   // boundingBox x1 sprite offset
				(int) (sprite.getHeight() * sprite.getScale()),  // boundingBox y1 sprite offset
				(int) (sprite.getWidth() * sprite.getScale()),   // boundingBox width
				(int) (sprite.getHeight() * sprite.getScale())); // boundingBox height
	}
}
