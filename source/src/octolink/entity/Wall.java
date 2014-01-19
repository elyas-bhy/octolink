package octolink.entity;

import java.awt.Canvas;
import java.awt.Point;

public class Wall extends AbstractTerrain {

	public Wall(Canvas defaultCanvas, Point pos) {
		super(defaultCanvas, pos, "images/wall.gif");
	}
	
}
