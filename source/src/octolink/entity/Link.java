package octolink.entity;

import gameframework.base.Drawable;
import gameframework.base.Overlappable;
import gameframework.game.GameEntity;
import gameframework.game.GameMovable;
import gameframework.game.SpriteManager;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import octolink.gameframework.game.SpriteManagerOctolinkImpl;

public class Link extends GameMovable implements Drawable, GameEntity, Overlappable {
	
	public static final int SPRITE_WIDTH = 24;
	public static final int SPRITE_HEIGHT = 32;
	
	protected final SpriteManager spriteManager;
	private boolean moving;

	public Link(Canvas defaultCanvas) {
		spriteManager = new SpriteManagerOctolinkImpl("images/link_walk.png", defaultCanvas, 2,
				SPRITE_WIDTH, SPRITE_HEIGHT, 11);
		spriteManager.setTypes("down", "up", "right", "left");
	}

	@Override
	public Rectangle getBoundingBox() {
		return new Rectangle(0, 0, SPRITE_WIDTH, SPRITE_HEIGHT);
	}

	@Override
	public void draw(Graphics g) {
		String spriteType = "";
		Point tmp = getSpeedVector().getDirection();

		if (tmp.getX() == 1) {
			spriteType += "right";
		} else if (tmp.getX() == -1) {
			spriteType += "left";
		} else if (tmp.getY() == 1) {
			spriteType += "down";
		} else if (tmp.getY() == -1) {
			spriteType += "up";
		} else {
			moving = false;
			spriteManager.reset();
			spriteManager.draw(g, getPosition());
			return;
		}
		moving = true;
		spriteManager.setType(spriteType);
		spriteManager.draw(g, getPosition());
	}

	@Override
	public void oneStepMoveAddedBehavior() {
		if (moving) {
			spriteManager.increment();
		}
	}
	
}
