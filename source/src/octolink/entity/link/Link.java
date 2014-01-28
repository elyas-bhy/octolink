package octolink.entity.link;

import gameframework.game.SpriteManager;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;

import octolink.gameframework.game.SpriteManagerOctolinkImpl;

public class Link extends AbstractLink {

	private boolean moving;
	protected final SpriteManager spriteManager;

	public Link(Canvas defaultCanvas) {
		spriteManager = new SpriteManagerOctolinkImpl("images/link_sprites.png", defaultCanvas,
				RENDERING_SCALE, SPRITE_WIDTH, SPRITE_HEIGHT, SPRITE_ROWS, getBoundingBox());
		spriteManager.setTypes("down", "up", "right", "left",
				"sword-down", "sword-up", "sword-right", "sword-left",
				"sword-strike-down", "sword-strike-up", "sword-strike-right", "sword-strike-left",
				"shield-down", "shield-up", "shield-right", "shield-left",
				"shield-moving-down", "shield-moving-up", "shield-moving-right", "shield-moving-left"
				);
	}

	@Override
	public void draw(Graphics g) {
		String spriteType = "";
		Point tmp = getSpeedVector().getDirection();

		if (tmp.getX() == 1) {
			spriteType += link_state + "right";
		} else if (tmp.getX() == -1) {
			spriteType += link_state + "left";
		} else if (tmp.getY() == 1) {
			spriteType += link_state + "down";
		} else if (tmp.getY() == -1) {
			spriteType += link_state + "up";
		} else {
			moving = false;
			spriteManager.reset();
			spriteManager.draw(g, getPosition());
			return;
		}
		moving = true;
		spriteManager.setType(spriteType);
		spriteManager.draw(g, getPosition());
		g.drawRect(getPosition().x, getPosition().y,
				getBoundingBox().width, getBoundingBox().height);
	}

	@Override
	public void oneStepMoveAddedBehavior() {
		if (moving) {
			spriteManager.increment();
		}
	}

}
