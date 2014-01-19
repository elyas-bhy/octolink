package octolink.entity;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import gameframework.base.Drawable;
import gameframework.base.Overlappable;
import gameframework.game.GameEntity;
import gameframework.game.GameMovable;
import gameframework.game.SpriteManager;
import gameframework.game.SpriteManagerDefaultImpl;

public class Link extends GameMovable implements Drawable, GameEntity, Overlappable {
	
	public static final int RENDERING_SIZE = 36;
	
	protected final SpriteManager spriteManager;

	public Link(Canvas defaultCanvas) {
		spriteManager = new SpriteManagerDefaultImpl("images/link_walk.png", defaultCanvas, RENDERING_SIZE, 11);
		spriteManager.setTypes("down", "up", "right", "left", "static");
	}

	@Override
	public Rectangle getBoundingBox() {
		return new Rectangle(0, 0, RENDERING_SIZE, RENDERING_SIZE);
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
			spriteType = "static";
			spriteManager.reset();
		}
		spriteManager.setType(spriteType);
		spriteManager.draw(g, getPosition());
	}

	@Override
	public void oneStepMoveAddedBehavior() {
		spriteManager.increment();
	}
	
}
