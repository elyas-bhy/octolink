package octolink.entity;

import gameframework.base.SpeedVectorDefaultImpl;

import java.awt.Canvas;
import java.awt.Point;

public class SpeedyCreep extends Creep {
	
	protected static int DEFAULT_SPEED = 12;
	protected static int DEFAULT_HEALTH = 1;

	public SpeedyCreep(Canvas defaultCanvas) {
		super(defaultCanvas, "images/creep_green.png");
		setSpeedVector(new SpeedVectorDefaultImpl(new Point(), DEFAULT_SPEED));
		setHealth(DEFAULT_HEALTH);
	}
	
	public int getDefaultSpeed() {
		return DEFAULT_SPEED;
	}

}
