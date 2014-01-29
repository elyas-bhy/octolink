package octolink.entity.link;

import gameframework.base.Drawable;
import gameframework.base.Overlappable;
import gameframework.base.SpeedVector;
import gameframework.game.GameEntity;
import gameframework.game.GameMovable;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import octolink.entity.Creep;

public abstract class AbstractLink extends GameMovable implements Drawable, GameEntity, Overlappable, KeyListener {

	public static final int SPRITE_WIDTH = 24;
	public static final int SPRITE_HEIGHT = 24;
	public static final float RENDERING_SCALE = 0.7f;
	public static final int[] SPRITE_ROWS = {11, 11, 11, 11, 5, 5, 5, 5, 8, 8, 8, 8, 3, 5, 4, 4,
		8, 9, 8, 8};
	public int health;
	protected LinkState state;

	public LinkState getState() {
		return state;
	}

	public void setState(LinkState state) {
		this.state = state;
	}

	@Override
	public Rectangle getBoundingBox() {
		return (new Rectangle(
				(int) (SPRITE_WIDTH * RENDERING_SCALE),    // boundingBox x1 sprite offset
				(int) (SPRITE_HEIGHT * RENDERING_SCALE),   // boundingBox y1 sprite offset
				(int) (SPRITE_WIDTH * RENDERING_SCALE),    // boundingBox width
				(int) (SPRITE_HEIGHT * RENDERING_SCALE))); // boundingBox height
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

	public void collide(Creep c) {
		Point linkSVDir, creepSVDir;
		SpeedVector linkSV, creepSV;
		linkSV = this.getSpeedVector();
		creepSV = c.getSpeedVector();
		linkSVDir = linkSV.getDirection();
		creepSVDir = creepSV.getDirection();
		if((linkSVDir.getX() == -creepSVDir.getX() && linkSVDir.getX() != 0) ||
				linkSVDir.getY() == -creepSVDir.getY() && linkSVDir.getY() != 0) {
			this.state.collideFront(this, c);
		} else {
			this.state.collide(this, c);
			this.health += this.state.parry(c.damage());
		}
	}
}
