package octolink.gameframework.game;

import gameframework.base.DrawableImage;
import gameframework.game.SpriteManager;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

/**
 * This implementation of {@link SpriteManager} assumes that sprite types are in
 * rows whereas increments of a type are in columns
 * 
 */
public class SpriteManagerOctolinkImpl implements SpriteManager {

	private final DrawableImage image;
	private Map<String, Integer> types;
	private int spriteNumber = 0;
	private final int maxSpriteNumber;
	private int currentRow;
	private final int renderingScale;
	private final int spriteWidth;
	private final int spriteHeight;

	public SpriteManagerOctolinkImpl(String filename, Canvas canvas, int renderingScale,
			int spriteWidth, int spriteHeight, int maxSpriteNumber) {
		this.renderingScale = renderingScale;
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
		image = new DrawableImage(filename, canvas);
		this.maxSpriteNumber = maxSpriteNumber;
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
		int dx2 = dx1 + (renderingScale * spriteWidth);
		int dy2 = dy1 + (renderingScale * spriteHeight);

		// Source image coordinates
		int sx1 = spriteNumber * spriteWidth;
		int sy1 = currentRow * spriteHeight;
		int sx2 = sx1 + spriteWidth;
		int sy2 = sy1 + spriteHeight;
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
		spriteNumber = (spriteNumber + 1) % maxSpriteNumber;
	}

	@Override
	public void reset() {
		spriteNumber = 0;
	}

	@Override
	public void setIncrement(int increment) {
		this.spriteNumber = increment;
	}
}
