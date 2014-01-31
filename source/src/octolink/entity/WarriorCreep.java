package octolink.entity;

import java.awt.Canvas;

public class WarriorCreep extends Creep {

	public WarriorCreep(Canvas defaultCanvas) {
		super(defaultCanvas, "images/creep_red.png");
		setHealth(DEFAULT_HEALTH);
	}

}
