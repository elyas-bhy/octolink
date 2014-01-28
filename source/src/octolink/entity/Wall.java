package octolink.entity;

import gameframework.base.Overlappable;
import gameframework.game.MoveBlocker;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Wall extends AbstractBlockerTerrain implements Overlappable {

	public Wall(Canvas defaultCanvas, Point pos) {
		super(defaultCanvas, pos, "images/wall.gif");
	}
	
}
