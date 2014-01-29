package octolink.entity.link;

import java.awt.Point;
import java.awt.Rectangle;

import octolink.Utils;

public class FighterState extends AbstractState {
	
	@Override
	public String getValue() {
		return "sword-";
	}

	@Override
	public Rectangle getBoundingBox(int spriteWidth, int spriteHeight, float renderingScale, Point p) {
		int x = (int) (spriteWidth * renderingScale);
		int y = (int) (spriteHeight * renderingScale);
		int width = (int) (spriteWidth * renderingScale);
		int height = (int) (spriteHeight * renderingScale);
		
		if (Utils.getOrientation(p) == "right") {
			width *= 2;
		}
		else if (Utils.getOrientation(p) == "left") {
			x = 0;
			width *= 2;
		}
		else if (Utils.getOrientation(p) == "down") {
			height *= 2;
		}
		else if (Utils.getOrientation(p) == "up") {
			y = 0;
			height *= 2;
		}
		return new Rectangle(x, y, width, height);
	}
}
