package octolink;

import java.awt.Point;

public class Utils {

	public static String getOrientation(Point p) {
		if (p.getX() == 1) {
			return "right";
		} else if (p.getX() == -1) {
			return "left";
		} else if (p.getY() == 1) {
			return "down";
		} else if (p.getY() == -1) {
			return "up";
		}
		return null;
	}
}
