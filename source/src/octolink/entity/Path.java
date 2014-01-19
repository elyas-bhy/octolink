package octolink.entity;

import java.awt.Canvas;

public class Path extends AbstractTerrain {

	public Path(Canvas defaultCanvas, String gif, int xx, int yy) {
		super(defaultCanvas, "images/wall.gif", xx, yy);  // TODO replace gif with appropriate type
	}

}
