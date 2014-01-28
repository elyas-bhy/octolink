package octolink.entity.link;

import gameframework.base.Drawable;
import gameframework.base.Overlappable;
import gameframework.game.GameEntity;
import gameframework.game.GameMovable;

import java.awt.Rectangle;

public abstract class AbstractLink extends GameMovable implements Drawable, GameEntity, Overlappable {

	public static final int SPRITE_WIDTH = 24;
	public static final int SPRITE_HEIGHT = 24;
	public static final float RENDERING_SCALE = 0.7f;
	public static final int[] SPRITE_ROWS = {11, 11, 11, 11, 5, 5, 5, 5, 8, 8, 8, 8, 3, 5, 4, 4,
		8, 9, 8, 8};
	public String link_state = "";


	@Override
	public Rectangle getBoundingBox() {
		return (new Rectangle(
				(int) (SPRITE_WIDTH * RENDERING_SCALE),    // boundingBox x1 sprite offset
				(int) (SPRITE_HEIGHT * RENDERING_SCALE),   // boundingBox y1 sprite offset
				(int) (SPRITE_WIDTH * RENDERING_SCALE),    // boundingBox width
				(int) (SPRITE_HEIGHT * RENDERING_SCALE))); // boundingBox height
	}
}
