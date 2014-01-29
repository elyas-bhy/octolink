package octolink.entity.link;

import octolink.entity.Creep;

public interface LinkState {
	
	public String getValue();
	public int getTransitionType();
	public void setTransitionType(int type);
	public void collide(AbstractLink abstractLink, Creep c);
	public void collideFront(AbstractLink abstractLink, Creep c);
	public int parry(int damage);
	public int strike();

}
