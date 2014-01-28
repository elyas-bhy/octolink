package octolink.entity;

import gameframework.base.Overlappable;

import java.awt.Canvas;
import java.awt.Point;

public class Water extends AbstractBlockerTerrain implements Overlappable {

	public Water(Canvas defaultCanvas, Point pos) {
		super(defaultCanvas, pos, "images/water.gif");
	}

}
