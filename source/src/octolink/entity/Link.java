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

	public static final int SPRITE_WIDTH = 24*3;
	public static final int SPRITE_HEIGHT = 32*3;
	public static final int RENDERING_SCALE = 1;
	public static final int[] SPRITE_ROWS = {11, 11, 11, 11, 5, 5, 5, 5, 8, 8, 8, 8, 3, 5, 4, 4,
		8, 9, 8, 8};

	protected final SpriteManager spriteManager;
	private boolean moving;

	public Link(Canvas defaultCanvas) {
		spriteManager = new SpriteManagerOctolinkImpl("images/link_sprites.png", defaultCanvas,
				RENDERING_SCALE, SPRITE_WIDTH, SPRITE_HEIGHT, SPRITE_ROWS);
		spriteManager.setTypes("down", "up", "right", "left",
				"sword-down", "sword-up", "sword-right", "sword-left",
				"sword-strike-down", "sword-strike-up", "sword-strike-right", "sword-strike-left",
				"shield-down", "shield-up", "shield-right", "shield-left",
				"shield-moving-down", "shield-moving-up", "shield-moving-right", "shield-moving-left"
				);
	}

	@Override
	public Rectangle getBoundingBox() {
		return (new Rectangle((int) getPosition().getX(), (int) getPosition().getY(),
				24, 32));
	}

	@Override
	public void draw(Graphics g) {
		String spriteType = "";
		Point tmp = getSpeedVector().getDirection();

		if (tmp.getX() == 1) {
			spriteType += "shield-moving-right";
		} else if (tmp.getX() == -1) {
			spriteType += "shield-moving-left";
		} else if (tmp.getY() == 1) {
			spriteType += "shield-moving-down";
		} else if (tmp.getY() == -1) {
			spriteType += "shield-moving-up";
		} else {
			moving = false;
			spriteManager.reset();
			spriteManager.draw(g, getPosition());
			return;
		}
		moving = true;
		spriteManager.setType(spriteType);
		spriteManager.draw(g, getPosition());
		g.drawRect(getBoundingBox().x, getBoundingBox().y, getBoundingBox().width, getBoundingBox().height);
	}

	@Override
	public void oneStepMoveAddedBehavior() {
		if (moving) {
			spriteManager.increment();
		}
	}

}
