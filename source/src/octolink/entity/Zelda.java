package octolink.entity;

import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.base.Overlappable;
import gameframework.game.GameEntity;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Zelda implements Drawable, GameEntity, Overlappable {

	public static final int RENDERING_SIZE = 24;
	protected static DrawableImage image = null;
	protected Point position;

	public Zelda(Canvas defaultCanvas, Point pos) {
		image = new DrawableImage("images/zelda.gif", defaultCanvas);
		position = pos;
	}

	public Point getPosition() {
		return position;
	}

	public void draw(Graphics g) {
		g.drawImage(image.getImage(), (int) getPosition().getX(),
				(int) getPosition().getY(), RENDERING_SIZE, RENDERING_SIZE,
				null);

	}

	public Rectangle getBoundingBox() {
		return (new Rectangle((int) position.getX(), (int) position.getY(),
				RENDERING_SIZE, RENDERING_SIZE));
	}
}
