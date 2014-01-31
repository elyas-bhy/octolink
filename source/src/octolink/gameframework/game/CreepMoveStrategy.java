package octolink.gameframework.game;

import gameframework.base.MoveStrategy;
import gameframework.base.SpeedVector;
import gameframework.base.SpeedVectorDefaultImpl;

import java.awt.Point;

import octolink.entity.Creep;

public class CreepMoveStrategy implements MoveStrategy {
	
	// Represents a path tile on the map
	private final int PATH = 0;
	private final int SPRITE_SIZE = 24;

	private SpeedVector currentMove = new SpeedVectorDefaultImpl(new Point(0, 0));
	private Creep creep;
	private int[][] map;

	public CreepMoveStrategy(Creep creep, int[][] map) {
		this.creep = creep;
		this.map = map;
		currentMove.setSpeed(creep.getSpeed());
	}

	public synchronized SpeedVector getSpeedVector() {
		if (creep.getPosition().distance(1 * SPRITE_SIZE, 1 * SPRITE_SIZE) == 0) {  // Spawn coordinates
			currentMove.setDirection(new Point(0, 1));
			return currentMove;
		}
		
		int row = (int) creep.getPosition().getX() / SPRITE_SIZE;
		int col = (int) creep.getPosition().getY() / SPRITE_SIZE;

		if (map[col][row+1] == PATH 
				&& (creep.getSpeedVector().getDirection().getX() != -1)) {  // right
			currentMove.setDirection(new Point(1, 0));
			
		} else if (map[col+1][row] == PATH 
				&& (creep.getSpeedVector().getDirection().getY() != -1)) {  // down
			currentMove.setDirection(new Point(0, 1));
			
		} else if (map[col][row-1] == PATH 
				&& (creep.getSpeedVector().getDirection().getX() != 1)) {  // left
			currentMove.setDirection(new Point(-1, 0));
			
		} else if (map[col-1][row] == PATH 
				&& (creep.getSpeedVector().getDirection().getY() != 1)) {  // up
			currentMove.setDirection(new Point(0, -1));
		}
		return currentMove;
	}
}
