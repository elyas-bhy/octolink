package octolink.gameframework.game;

import gameframework.base.ObservableValue;
import gameframework.game.Game;
import gameframework.game.GameLevel;
import gameframework.game.GameUniverse;
import gameframework.game.GameUniverseViewPort;

import java.util.Date;

/**
 * To be implemented with respect to a specific game. Expected to initialize the
 * universe and the gameBoard
 */

public abstract class OctolinkGameLevel extends Thread implements GameLevel {
	private static final int MINIMUM_DELAY_BETWEEN_GAME_CYCLES = 100;
	protected GameUniverse universe;
	protected GameUniverseViewPort gameBoard;
	protected ObservableValue<Integer> score[];
	protected ObservableValue<Integer> life[];
	protected ObservableValue<Boolean> endOfGame;

	boolean stopGameLoop;
	protected final Game g;

	protected abstract void init();

	public OctolinkGameLevel(Game g) {
		this.g = g;
		this.score = g.score();
		this.life = g.life();
	}

	@Override
	public void start() {
		endOfGame = g.endOfGame();
		init();
		super.start();
		try {
			super.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		stopGameLoop = false;
		// main game loop :
		long start;
		while (!this.isInterrupted()) {
			start = new Date().getTime();
			gameBoard.paint();
			universe.allOneStepMoves();
			universe.processAllOverlaps();
			try {
				long sleepTime = MINIMUM_DELAY_BETWEEN_GAME_CYCLES
						- (new Date().getTime() - start);
				if (sleepTime > 0) {
					Thread.sleep(sleepTime);
				}
			} catch (InterruptedException e) {
				synchronized (this) {
					try {
						this.wait();
					} catch (InterruptedException e1) {
					}
				}
			}
		}
	}

	public void end() {
		stopGameLoop = true;
	}

	protected void overlap_handler() {
	}

}
