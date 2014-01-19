package octolink.entity;

import gameframework.base.Overlappable;
import gameframework.game.GameEntity;

import java.awt.Point;
import java.awt.Rectangle;

public class Spawn implements GameEntity, Overlappable {
	
	public static final int SPRITE_SIZE = 16;
	protected Point position;

	public Spawn(Point pos) {
		position = pos;
	}

	public Point getPosition() {
		return position;
	}

	public Rectangle getBoundingBox() {
		return (new Rectangle((int) position.getX(), (int) position.getY(),
				SPRITE_SIZE, SPRITE_SIZE));
	}
}
