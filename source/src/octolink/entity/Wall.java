package octolink.entity;

import gameframework.base.Overlappable;

import java.awt.Canvas;
import java.awt.Point;

public class Wall extends AbstractBlockerTerrain implements Overlappable {

	public Wall(Canvas defaultCanvas, Point pos) {
		super(defaultCanvas, pos, "images/wall.gif");
	}
	
}
