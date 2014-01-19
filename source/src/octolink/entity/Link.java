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
	public static final int[] SPRITE_ROWS = {11, 11, 11, 11, 5, 5, 5, 5};
	
	protected final SpriteManager spriteManager;
	private boolean moving;

	public Link(Canvas defaultCanvas) {
		spriteManager = new SpriteManagerOctolinkImpl("images/link_sprites.png", defaultCanvas,
				RENDERING_SCALE, SPRITE_WIDTH, SPRITE_HEIGHT, SPRITE_ROWS);
		spriteManager.setTypes("down", "up", "right", "left",
				"downSword", "upSword", "rightSword", "leftSword");
	}

	@Override
	public Rectangle getBoundingBox() {
		return new Rectangle(SPRITE_WIDTH * RENDERING_SCALE, SPRITE_HEIGHT * RENDERING_SCALE,
				SPRITE_WIDTH * 2 * RENDERING_SCALE, SPRITE_HEIGHT * 2 * RENDERING_SCALE);
	}

	@Override
	public void draw(Graphics g) {
		String spriteType = "";
		Point tmp = getSpeedVector().getDirection();

		if (tmp.getX() == 1) {
			spriteType += "rightSword";
		} else if (tmp.getX() == -1) {
			spriteType += "leftSword";
		} else if (tmp.getY() == 1) {
			spriteType += "downSword";
		} else if (tmp.getY() == -1) {
			spriteType += "upSword";
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
