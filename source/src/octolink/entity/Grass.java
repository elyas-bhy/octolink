package octolink.entity;

import java.awt.Canvas;
import java.awt.Point;

public class Grass extends AbstractBlockerTerrain {

	public Grass(Canvas defaultCanvas, Point pos) {
		super(defaultCanvas, pos, "images/grass.gif");
	}

}
