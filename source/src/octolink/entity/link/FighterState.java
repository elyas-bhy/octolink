package octolink.entity.link;

import gameframework.base.SpeedVector;
import gameframework.base.SpeedVectorDefaultImpl;

import java.awt.Point;
import java.awt.Rectangle;

import octolink.entity.Creep;
import octolink.util.Sprite;
import octolink.util.Utils;

public class FighterState extends AbstractState {
	
	protected static int DEFAULT_DAMAGE = 3;

	@Override
	public String getValue() {
		return "sword-";
	}
	
	@Override
	public int strike() {
		return DEFAULT_DAMAGE;
	}

	@Override
	public void collideFront(Link l, Creep c) {
		SpeedVector creepSpeedVector = c.getSpeedVector();
		
		c.setSpeedVector(new SpeedVectorDefaultImpl(
				new Point((int)-creepSpeedVector.getDirection().getX(),
						(int)-creepSpeedVector.getDirection().getY()),
						creepSpeedVector.getSpeed()*20));
		
		for(int i = 0; i < 3; ++i) {
			c.oneStepMove();
		}
		Point invertDirection = new Point((int)-c.getSpeedVector().getDirection().getX(),
										(int)-c.getSpeedVector().getDirection().getY());
		c.setSpeedVector(new SpeedVectorDefaultImpl(invertDirection, creepSpeedVector.getSpeed()));
		c.parry(strike());
	}

	public Rectangle getBoundingBox(Link l) {
		Sprite sprite = l.getSprite();
		int x = (int) (sprite.getWidth() * sprite.getScale());
		int y = (int) (sprite.getHeight() * sprite.getScale());
		int width = (int) (sprite.getWidth() * sprite.getScale());
		int height = (int) (sprite.getHeight() * sprite.getScale());
		
		// Increase bounding box size when Link is striking
		if (l.isStricking()) {
			Point p = l.getSpeedVector().getDirection();
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
		}
		return new Rectangle(x, y, width, height);
	}
}
