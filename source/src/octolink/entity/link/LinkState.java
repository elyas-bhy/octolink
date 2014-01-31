package octolink.entity.link;

import java.awt.Rectangle;

import octolink.entity.Creep;

public interface LinkState {
	
	public String getValue();
	public AnimationType getAnimationType();
	public void setAnimationType(AnimationType type);
	public int strike();
	public int parry(int damage);
	public int parryFront(int damage);
	public void collide(Link l, Creep c);
	public void collideFront(Link l, Creep c);
	public Rectangle getBoundingBox(Link l);

}
