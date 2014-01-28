package octolink.gameframework.game;

import gameframework.base.MoveStrategyKeyboard;
import gameframework.base.SpeedVector;
import gameframework.base.SpeedVectorDefaultImpl;

import java.awt.Point;
import java.awt.event.KeyEvent;

public class OctolinkMoveStrategyKeyboard extends MoveStrategyKeyboard {
	
	private final int DEFAULT_SPEED = 8;
	
	protected SpeedVector speedVector = new SpeedVectorDefaultImpl(new Point(0,1), 0);

	public SpeedVector getSpeedVector() {
		return speedVector;
	}

	@Override
	public void keyPressed(KeyEvent event) {
		int keycode = event.getKeyCode();
		switch (keycode) {
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D:
			speedVector.setDirection(new Point(1, 0));
			speedVector.setSpeed(DEFAULT_SPEED);
			break;
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_A:
			speedVector.setDirection(new Point(-1, 0));
			speedVector.setSpeed(DEFAULT_SPEED);
			break;
		case KeyEvent.VK_UP:
		case KeyEvent.VK_W:
			speedVector.setDirection(new Point(0, -1));
			speedVector.setSpeed(DEFAULT_SPEED);
			break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_S:
			speedVector.setDirection(new Point(0, 1));
			speedVector.setSpeed(DEFAULT_SPEED);
			break;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent event) {
		int keycode = event.getKeyCode();
		switch (keycode) {
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D:
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_A:
		case KeyEvent.VK_UP:
		case KeyEvent.VK_W:
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_S:
			speedVector.setSpeed(0);
			break;
		}
	}
	
}
