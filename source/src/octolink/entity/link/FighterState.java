package octolink.entity.link;

import gameframework.base.MoveStrategyStraightLine;
import gameframework.base.SpeedVector;
import gameframework.base.SpeedVectorDefaultImpl;
import gameframework.game.GameMovableDriver;
import gameframework.game.GameMovableDriverDefaultImpl;

import java.awt.Point;
import java.awt.Rectangle;

import octolink.entity.Creep;
import octolink.util.Sprite;
import octolink.util.Utils;

public class FighterState extends AbstractState {

	@Override
	public String getValue() {
		return "sword-";
	}
	
	@Override
	public int strike() {
		return 3;
	}
	
	public void collide(Link l, Creep c) {
		Point p = l.getPosition();
		SpeedVector creepSpeedVector = c.getSpeedVector();
		GameMovableDriver oldLinkDriver = l.getDriver();

		GameMovableDriverDefaultImpl linkDriver = new GameMovableDriverDefaultImpl();
		Point destination = new Point(
				(int) (p.getX() + creepSpeedVector.getDirection().getX() * 20),
				(int) (p.getY() + creepSpeedVector.getDirection().getY() * 20));

		linkDriver.setStrategy(new MoveStrategyStraightLine(p, destination));
		l.setDriver(linkDriver);
		for (int i = 0; i < 3; ++i)
			l.oneStepMove();
		l.setDriver(oldLinkDriver);
	}

	@Override
	public void collideFront(Link abstractLink, Creep c) {
		SpeedVector creepSpeedVector = c.getSpeedVector();
		GameMovableDriver oldCreepDriver = c.getDriver();

		c.setSpeedVector(new SpeedVectorDefaultImpl(
				new Point((int)-creepSpeedVector.getDirection().getX(),
						(int)-creepSpeedVector.getDirection().getY()),
						creepSpeedVector.getSpeed()*10));
		for(int i = 0; i < 3; ++i)
			c.oneStepMove();
		c.setDriver(oldCreepDriver);
		c.setSpeedVector(creepSpeedVector);
		c.parry(strike());
	}

	public Rectangle getBoundingBox(Sprite sprite, Point p) {
		int x = (int) (sprite.getWidth() * sprite.getScale());
		int y = (int) (sprite.getHeight() * sprite.getScale());
		int width = (int) (sprite.getWidth() * sprite.getScale());
		int height = (int) (sprite.getHeight() * sprite.getScale());
		
		if (Utils.getOrientation(p) == "right") {
			width *= 1.5;
		}
		else if (Utils.getOrientation(p) == "left") {
			x = width/2;
			width *= 1.5;
		}
		else if (Utils.getOrientation(p) == "down") {
			height *= 1.5;
		}
		else if (Utils.getOrientation(p) == "up") {
			y = height/2;
			height *= 1.5;
		}
		return new Rectangle(x, y, width, height);
	}
}
