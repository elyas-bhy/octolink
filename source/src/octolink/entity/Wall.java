package octolink.entity;

import java.awt.Canvas;

public class Wall extends AbstractTerrain {

	public Wall(Canvas defaultCanvas, String gif, int xx, int yy) {
		super(defaultCanvas, "images/wall.gif", xx, yy);
	}
}
