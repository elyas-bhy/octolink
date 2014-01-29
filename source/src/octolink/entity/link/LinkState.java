package octolink.entity.link;

import java.awt.Point;
import java.awt.Rectangle;

public interface LinkState {
	
	public String getValue();
	public int getTransitionType();
	public void setTransitionType(int type);
	public Rectangle getBoundingBox(int spriteWidth, int spriteHeight, float renderingScale, Point p);

}
