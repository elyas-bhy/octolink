package octolink.entity;

import java.awt.Canvas;
import java.awt.Point;

public class Water extends AbstractBlockerTerrain {

	public Water(Canvas defaultCanvas, Point pos) {
		super(defaultCanvas, pos, "images/water.gif");
	}

}
