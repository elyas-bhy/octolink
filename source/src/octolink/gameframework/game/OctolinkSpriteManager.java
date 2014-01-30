package octolink.gameframework.game;

import gameframework.base.DrawableImage;
import gameframework.game.SpriteManager;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import octolink.entity.link.Link;
import octolink.util.Sprite;

/**
 * This implementation of {@link SpriteManager} assumes that sprite types are in
 * rows whereas increments of a type are in columns
 * 
 */
public class OctolinkSpriteManager implements SpriteManager {

	private Sprite sprite;
	private final DrawableImage image;
	private Map<String, Integer> types;
	private int spriteNumber = 0;
	private int currentRow;

	public OctolinkSpriteManager(Sprite sprite, Canvas canvas) {
		this.sprite = sprite;
		image = new DrawableImage(sprite.getImage(), canvas);
	}

	@Override
	public void setTypes(String... types) {
		int i = 0;
		this.types = new HashMap<String, Integer>(types.length);
		for (String type : types) {
			this.types.put(type, i);
			i++;
		}
	}

	@Override
	public void draw(Graphics g, Point position) {
		// Destination image coordinates
		int dx1 = (int) position.getX();
		int dy1 = (int) position.getY();
		int dx2 = dx1 + (int) (sprite.getScale() * sprite.getWidth()) * 3;
		int dy2 = dy1 + (int) (sprite.getScale() * sprite.getHeight()) * 3;

		// Source image coordinates
		int sx1 = spriteNumber * sprite.getWidth() * 3;
		int sy1 = currentRow * sprite.getHeight() * 3;
		int sx2 = sx1 + sprite.getWidth() * 3;
		int sy2 = sy1 + sprite.getHeight() * 3;

		g.drawImage(image.getImage(), dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2,
				null);
	}

	@Override
	public void setType(String type) {
		if (!types.containsKey(type)) {
			throw new IllegalArgumentException(type
					+ " is not a valid type for this sprite manager.");
		}
		this.currentRow = types.get(type);
	}

	@Override
	public void increment() {
		spriteNumber = (spriteNumber + 1) % sprite.getRows()[currentRow];
	}

	@Override
	public void reset() {
		spriteNumber = 0;
	}

	@Override
	public void setIncrement(int increment) {
		this.spriteNumber = increment;
	}

	public int getCurrentRow() {
		return currentRow;
	}

	public void handleAnimation(Link l, Graphics g, String spriteType) {
		switch(l.getState().getTransitionType()) {
		case 1:
			setType(spriteType);
			reset();
			draw(g, l.getPosition());
			break;
		case 2:
			setType("animation-" + spriteType);
			draw(g, l.getPosition());
			for (int i = 1; i < sprite.getRows()[getCurrentRow()]; ++i ) {
				increment();
				draw(g, l.getPosition());
			}
			setType(spriteType);
			reset();
			break;
		}
	}

}
