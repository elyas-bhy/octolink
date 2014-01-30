package octolink.entity.link;

import gameframework.base.MoveStrategyStraightLine;
import gameframework.base.SpeedVector;
import gameframework.base.SpeedVectorDefaultImpl;
import gameframework.game.GameMovableDriver;
import gameframework.game.GameMovableDriverDefaultImpl;

import java.awt.Point;
import java.awt.Rectangle;

import octolink.Utils;
import octolink.entity.Creep;

public class FighterState extends AbstractState {

	@Override
	public String getValue() {
		return "sword-";
	}
	
	@Override
	public int parryFront(int damage) {
		return 0;
		
	}

	@Override
	public int strike() {
		return 3;
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

	public Rectangle getBoundingBox(int spriteWidth, int spriteHeight, float renderingScale, Point p) {
		int x = (int) (spriteWidth * renderingScale);
		int y = (int) (spriteHeight * renderingScale);
		int width = (int) (spriteWidth * renderingScale);
		int height = (int) (spriteHeight * renderingScale);
		
		if (Utils.getOrientation(p) == "right") {
			width *= 2;
		}
		else if (Utils.getOrientation(p) == "left") {
			x = 0;
			width *= 2;
		}
		else if (Utils.getOrientation(p) == "down") {
			height *= 2;
		}
		else if (Utils.getOrientation(p) == "up") {
			y = 0;
			height *= 2;
		}
		return new Rectangle(x, y, width, height);
	}
}
