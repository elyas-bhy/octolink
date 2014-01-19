package octolink.entity;

import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.base.Overlappable;
import gameframework.game.GameEntity;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class AbstractTerrain implements Terrain, Drawable, Overlappable, GameEntity {

	public static final int RENDERING_SIZE = 16;
	protected static DrawableImage image;
	protected Point position;

	public AbstractTerrain(Canvas defaultCanvas, Point pos, String gif) {
		image = new DrawableImage(gif, defaultCanvas);
		position = pos;
	}

	@Override
	public Point getPosition() {
		return position;
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(image.getImage(), (int) getPosition().getX(), 
				(int) getPosition().getY(), RENDERING_SIZE, RENDERING_SIZE,
				null);
	}
	
	@Override
	public Rectangle getBoundingBox() {
		return (new Rectangle((int) position.getX(), (int) position.getY(),
				RENDERING_SIZE, RENDERING_SIZE));
	}
	
}