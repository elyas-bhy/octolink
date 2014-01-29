package octolink.entity.link;

import gameframework.base.Drawable;
import gameframework.base.Overlappable;
import gameframework.game.GameEntity;
import gameframework.game.GameMovable;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import octolink.Utils;
import octolink.entity.WarriorCreep;
import octolink.gameframework.game.OctolinkSpriteManager;

public class Link extends GameMovable implements Drawable, GameEntity, Overlappable, KeyListener {

	public static final int SPRITE_WIDTH = 24;
	public static final int SPRITE_HEIGHT = 24;
	public static final float RENDERING_SCALE = 0.7f;
	public static final int[] SPRITE_ROWS = {11, 11, 11, 11, 5, 5, 5, 5, 8, 8, 8, 8, 3, 5, 4, 4,
		8, 9, 8, 8};
	protected LinkState state;
	private boolean moving;
	protected final OctolinkSpriteManager spriteManager;


	public Link(Canvas defaultCanvas) {
		state = new NeutralState();
		spriteManager = new OctolinkSpriteManager("images/link_sprites.png", defaultCanvas,
				RENDERING_SCALE, SPRITE_WIDTH, SPRITE_HEIGHT, SPRITE_ROWS);
		spriteManager.setTypes("down", "up", "right", "left",
				"sword-down", "sword-up", "sword-right", "sword-left",
				"animation-sword-down", "animation-sword-up", "animation-sword-right", "animation-sword-left",
				"animation-shield-down", "animation-shield-up", "animation-shield-right", "animation-shield-left",
				"shield-down", "shield-up", "shield-right", "shield-left"
				);
	}

	@Override
	public void draw(Graphics g) {
		// display boundingBox
		g.drawRect(getPosition().x + (int)getBoundingBox().getX(),
				getPosition().y + (int)getBoundingBox().getY(),
				getBoundingBox().width, getBoundingBox().height);

		Point tmp = getSpeedVector().getDirection();
		String spriteType = state.getValue() + Utils.getOrientation(tmp);

		if (state.getTransitionType() == 1) {
			spriteManager.setType(spriteType);
			spriteManager.reset();
			spriteManager.draw(g, getPosition());
			state.setTransitionType(0);
			return;
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
	}

	@Override
	public void oneStepMoveAddedBehavior() {
		if (moving) {
			spriteManager.increment();
		}
	}
	public LinkState getState() {
		return state;
	}

	public void setState(LinkState state) {
		this.state = state;
	}

	@Override
	public Rectangle getBoundingBox() {
		Point p = getSpeedVector().getDirection();
		return state.getBoundingBox(SPRITE_WIDTH, SPRITE_HEIGHT, RENDERING_SCALE, p);			
	}

	@Override
	public void keyPressed(KeyEvent event) {
		int keycode = event.getKeyCode();
		switch (keycode) {
		case KeyEvent.VK_C:
			state = new NeutralState();
			state.setTransitionType(1);
			break;
		case KeyEvent.VK_V:
			state = new FighterState();
			state.setTransitionType(1);
			break;
		case KeyEvent.VK_B:
			state = new DefenderState();
			state.setTransitionType(2);
			break;
		case KeyEvent.VK_G:
			state = new FighterState();
			state.setTransitionType(2);
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {

	}

	@Override
	public void keyTyped(KeyEvent event) {

	}

	public void collide(WarriorCreep c) {
		Point linkDir = this.getSpeedVector().getDirection();
		Point creepDir = c.getSpeedVector().getDirection();
		if ((linkDir.getX() == -creepDir.getX() && linkDir.getX() != 0) || 
				linkDir.getY() == -creepDir.getY() && linkDir.getY() != 0) {
			state.collideFront(this, c);
		} else {
			state.collide(this, c);
			//this.health += this.state.parry(c.damage());
		}
	}

}
