package octolink;

import gameframework.base.MoveStrategyKeyboard;
import gameframework.base.MoveStrategyRandom;
import gameframework.game.CanvasDefaultImpl;
import gameframework.game.Game;
import gameframework.game.GameLevelDefaultImpl;
import gameframework.game.GameMovableDriverDefaultImpl;
import gameframework.game.GameUniverseDefaultImpl;
import gameframework.game.GameUniverseViewPortDefaultImpl;
import gameframework.game.MoveBlockerChecker;
import gameframework.game.MoveBlockerCheckerDefaultImpl;
import gameframework.game.OverlapProcessor;
import gameframework.game.OverlapProcessorDefaultImpl;

import java.awt.Canvas;
import java.awt.Point;

import octolink.entity.Link;
import octolink.entity.Path;
import octolink.entity.Wall;
import octolink.entity.WarriorCreep;
import octolink.rule.OctolinkMoveBlockers;
import octolink.rule.OctolinkOverlapRules;
import pacman.rule.GhostMovableDriver;

public class WaveOne extends GameLevelDefaultImpl {
	Canvas canvas;

	// 0 : Terrain; 1 : Spawn point; 2 : Walls; 3 : Ponds; 4 : Holes; 5 : Zelda
	static int[][] tab = { 
	    	{ 1, 2, 2, 2, 2, 2, 2, 2 },
	    	{ 0, 0, 0, 0, 0, 0, 0, 2 },
			{ 2, 2, 2, 2, 2, 2, 0, 2 },
			{ 2, 2, 2, 0, 0, 0, 0, 2 },
			{ 5, 0, 0, 0, 2, 2, 2, 2 } };

	public static final int SPRITE_SIZE = 16;
	public static final int NUMBER_OF_CREEPS = 5;

	@Override
	protected void init() {
		OverlapProcessor overlapProcessor = new OverlapProcessorDefaultImpl();

		MoveBlockerChecker moveBlockerChecker = new MoveBlockerCheckerDefaultImpl();
		moveBlockerChecker.setMoveBlockerRules(new OctolinkMoveBlockers());
		
		OctolinkOverlapRules overlapRules = new OctolinkOverlapRules(new Point(3 * SPRITE_SIZE, 3 * SPRITE_SIZE),
				new Point(2 * SPRITE_SIZE, 2 * SPRITE_SIZE), life[0], score[0], endOfGame);
		overlapProcessor.setOverlapRules(overlapRules);

		universe = new GameUniverseDefaultImpl(moveBlockerChecker, overlapProcessor);
		overlapRules.setUniverse(universe);

		gameBoard = new GameUniverseViewPortDefaultImpl(canvas, universe);
		((CanvasDefaultImpl) canvas).setDrawingGameBoard(gameBoard);
		
		// Filling up the universe with basic non movable entities and inclusion in the universe
		for (int i = 0; i < 5; ++i) {
			for (int j = 0; j < 8; ++j) {
				if (tab[i][j] == 0) {
					universe.addGameEntity(new Path(canvas, new Point(j * SPRITE_SIZE, i * SPRITE_SIZE)));
				}
				if (tab[i][j] == 2) {
					universe.addGameEntity(new Wall(canvas, new Point(j * SPRITE_SIZE, i * SPRITE_SIZE)));
				}
			}
		}
		
		// Link definition and inclusion in the universe
		Link link = new Link(canvas);
		GameMovableDriverDefaultImpl linkDriver = new GameMovableDriverDefaultImpl();
		MoveStrategyKeyboard keyStr = new MoveStrategyKeyboard();
		linkDriver.setStrategy(keyStr);
		linkDriver.setmoveBlockerChecker(moveBlockerChecker);
		canvas.addKeyListener(keyStr);
		link.setDriver(linkDriver);
		link.setPosition(new Point(4 * SPRITE_SIZE, 4 * SPRITE_SIZE));
		universe.addGameEntity(link);

		// Creeps definition and inclusion in the universe
		WarriorCreep creep;
		for (int t = 0; t < NUMBER_OF_CREEPS; ++t) {
			GameMovableDriverDefaultImpl creepDriver = new GhostMovableDriver(); // TODO implement CreepMovableDriver
			MoveStrategyRandom ranStr = new MoveStrategyRandom();
			creepDriver.setStrategy(ranStr);
			creepDriver.setmoveBlockerChecker(moveBlockerChecker);
			creep = new WarriorCreep(canvas);
			creep.setDriver(creepDriver);
			creep.setPosition(new Point(2 * SPRITE_SIZE, 2 * SPRITE_SIZE));
			universe.addGameEntity(creep);
			overlapRules.addCreep(creep);
		}
	}

	public WaveOne(Game g) {
		super(g);
		canvas = g.getCanvas();
	}

}
