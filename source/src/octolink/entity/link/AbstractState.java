package octolink.entity.link;

import gameframework.base.MoveStrategyStraightLine;
import gameframework.base.SpeedVector;
import gameframework.game.GameMovableDriver;
import gameframework.game.GameMovableDriverDefaultImpl;

import java.awt.Point;
import java.awt.Rectangle;

import octolink.entity.Creep;

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
	
	@Override
	public void collide(Link l, Creep c) {
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
	public void collideFront(Link l, Creep c) {
		collide(l, c);		
	}

	@Override
	public Rectangle getBoundingBox(int spriteWidth, int spriteHeight, float renderingScale, Point p) {
		return new Rectangle(
				(int) (spriteWidth * renderingScale),   // boundingBox x1 sprite offset
				(int) (spriteHeight * renderingScale),  // boundingBox y1 sprite offset
				(int) (spriteWidth * renderingScale),   // boundingBox width
				(int) (spriteHeight * renderingScale)); // boundingBox height
	}
}
