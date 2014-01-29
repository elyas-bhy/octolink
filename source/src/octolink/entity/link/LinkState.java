package octolink.entity.link;

import java.awt.Point;
import java.awt.Rectangle;

import octolink.entity.Creep;

public interface LinkState {
	
	public String getValue();
	public int getTransitionType();
	public void setTransitionType(int type);
	public void collide(Link l, Creep c);
	public void collideFront(Link l, Creep c);
	public int parry(int damage);
	public int strike();
	public Rectangle getBoundingBox(int spriteWidth, int spriteHeight, float renderingScale, Point p);

}
