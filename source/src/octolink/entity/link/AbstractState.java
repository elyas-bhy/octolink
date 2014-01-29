package octolink.entity.link;

import java.awt.Point;
import java.awt.Rectangle;

public abstract class AbstractState implements LinkState {

	protected int transitionType;

	@Override
	public int getTransitionType() {
		return transitionType;
	}

	@Override
	public void setTransitionType(int type) {
		transitionType = type;
	}

	@Override
	public Rectangle getBoundingBox(int spriteWidth, int spriteHeight, float renderingScale, Point p) {
		return new Rectangle(
				(int) (spriteWidth * renderingScale),   // boundingBox x1 sprite offset
				(int) (spriteHeight * renderingScale),  // boundingBox y1 sprite offset
				(int) (spriteWidth * renderingScale),   // boundingBox width
				(int) (spriteHeight * renderingScale)); // boundingBox height
	}
}
