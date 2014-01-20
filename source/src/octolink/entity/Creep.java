package octolink.entity;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.base.Overlappable;
import gameframework.game.GameEntity;
import gameframework.game.GameMovable;
import gameframework.game.SpriteManagerDefaultImpl;

public abstract class Creep extends GameMovable implements Drawable, GameEntity, Overlappable {

	public static final int RENDERING_SIZE = 16;
	
	private final SpriteManagerDefaultImpl spriteManager;
	protected static DrawableImage image = null;
	protected boolean active = true;

	public Creep(Canvas defaultCanvas, String gif) {
		spriteManager = new SpriteManagerDefaultImpl(gif, defaultCanvas, RENDERING_SIZE, 6);
		spriteManager.setTypes("left", "right", "up", "down", "unused");
	}
	
	@Override
	public Rectangle getBoundingBox() {
		return (new Rectangle(0, 0, RENDERING_SIZE, RENDERING_SIZE));
	}
	
	@Override
	public void draw(Graphics g) {
		String spriteType = "";
		Point tmp = getSpeedVector().getDirection();

		if (tmp.getX() == -1) {
			spriteType += "left";
		} else if (tmp.getY() == 1) {
			spriteType += "down";
		} else if (tmp.getY() == -1) {
			spriteType += "up";
		} else {
			spriteType += "right";
		}

		spriteManager.setType(spriteType);
		spriteManager.draw(g, getPosition());
	}

	@Override
	public void oneStepMoveAddedBehavior() {
		spriteManager.increment();
	}
	
}
