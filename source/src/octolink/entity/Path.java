package octolink.entity;

import java.awt.Canvas;
import java.awt.Point;

public class Path extends AbstractTerrain {

	public Path(Canvas defaultCanvas, Point pos) {
		super(defaultCanvas, pos, "images/wall.gif");  // TODO replace gif with appropriate type
	}

}
