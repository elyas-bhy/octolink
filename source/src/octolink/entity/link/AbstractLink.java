package octolink.entity.link;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import gameframework.base.Drawable;
import gameframework.base.Overlappable;
import gameframework.game.GameEntity;
import gameframework.game.GameMovable;

public abstract class AbstractLink extends GameMovable implements Drawable, GameEntity, Overlappable, KeyListener {

	public static final int SPRITE_WIDTH = 24;
	public static final int SPRITE_HEIGHT = 24;
	public static final float RENDERING_SCALE = 0.7f;
	public static final int[] SPRITE_ROWS = {11, 11, 11, 11, 5, 5, 5, 5, 8, 8, 8, 8, 3, 5, 4, 4,
		8, 9, 8, 8};
	public int stateTransition = 0;
	public String linkState = "";
	public String linkPosition = "down";


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
			stateTransition = 1;
			linkState = "";
			break;
		case KeyEvent.VK_V:
			stateTransition = 1;
			linkState = "sword-";
			break;
		case KeyEvent.VK_B:
			stateTransition = 2;
			linkState = "shield-";
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {

	}

	@Override
	public void keyTyped(KeyEvent event) {

	}
}
