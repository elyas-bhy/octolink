package octolink.entity.link;

import java.awt.Rectangle;

import gameframework.base.Drawable;
import gameframework.base.Overlappable;
import gameframework.game.GameEntity;
import gameframework.game.GameMovable;

public abstract class AbstractLink extends GameMovable implements Drawable, GameEntity, Overlappable {

	public static final int SPRITE_WIDTH = 24;
	public static final int SPRITE_HEIGHT = 24;
	public static final float RENDERING_SCALE = 0.7f;
	public static final int[] SPRITE_ROWS = {11, 11, 11, 11, 5, 5, 5, 5, 8, 8, 8, 8, 3, 5, 4, 4,
		8, 9, 8, 8};
	public String link_state = "";
	

	@Override
	public Rectangle getBoundingBox() {
		return (new Rectangle((int) getPosition().getX(), (int) getPosition().getY(),
				(int) (SPRITE_WIDTH * RENDERING_SCALE), (int) (SPRITE_HEIGHT * RENDERING_SCALE)));
	}
}
