package octolink.entity.link;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;

import octolink.gameframework.game.OctolinkSpriteManagerImpl;

public class Link extends AbstractLink {

	private boolean moving;
	protected final OctolinkSpriteManagerImpl spriteManager;

	public Link(Canvas defaultCanvas) {
		spriteManager = new OctolinkSpriteManagerImpl("images/link_sprites.png", defaultCanvas,
				RENDERING_SCALE, SPRITE_WIDTH, SPRITE_HEIGHT, SPRITE_ROWS, getBoundingBox());
		spriteManager.setTypes("down", "up", "right", "left",
				"sword-down", "sword-up", "sword-right", "sword-left",
				"animation-sword-down", "animation-sword-up", "animation-sword-right", "animation-sword-left",
				"animation-shield-down", "animation-shield-up", "animation-shield-right", "animation-shield-left",
				"shield-down", "shield-up", "shield-right", "shield-left"
				);
	}

	@Override
	public void draw(Graphics g) {
		String spriteType = "";
		Point tmp = getSpeedVector().getDirection();

		if (tmp.getX() == 1) {
			spriteType = state.getValue() + "right";
		} else if (tmp.getX() == -1) {
			spriteType = state.getValue() + "left";
		} else if (tmp.getY() == 1) {
			spriteType = state.getValue() + "down";
		} else if (tmp.getY() == -1) {
			spriteType = state.getValue() + "up";
		}
		
		if (state.getTransitionType() == 1) {
			spriteManager.setType(spriteType);
			spriteManager.reset();
			spriteManager.draw(g, getPosition());
			state.setTransitionType(0);
			return ;
		}
		else if (state.getTransitionType() == 2) {
			spriteManager.setType("animation-" + spriteType);
			spriteManager.draw(g, getPosition());
			for (int i = 1; i < SPRITE_ROWS[spriteManager.getCurrentRow()]; ++i ) {
				spriteManager.increment();
				spriteManager.draw(g, getPosition());
			}
			spriteManager.setType(spriteType);
			spriteManager.reset();
			state.setTransitionType(0);
			return;
		}
		
		if (getSpeedVector().getSpeed() == 0) {
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
