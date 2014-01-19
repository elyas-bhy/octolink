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

import octolink.entity.Grass;
import octolink.entity.Link;
import octolink.entity.Path;
import octolink.entity.Spawn;
import octolink.entity.Wall;
import octolink.entity.WarriorCreep;
import octolink.entity.Water;
import octolink.entity.Zelda;
import octolink.gameframework.game.OctolinkMoveStrategyKeyboard;
import octolink.rule.OctolinkMoveBlockers;
import octolink.rule.OctolinkOverlapRules;
import pacman.rule.GhostMovableDriver;

public class WaveOne extends GameLevelDefaultImpl {
	Canvas canvas;

	// 0 : Path; 1 : Spawn; 2 : Walls; 3 : Grass; 4 : Water; 5 : Holes; 6 : Zelda
	static int[][] tab = { 
		{ 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 },
		{ 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 },
		{ 2, 0, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 },
		{ 2, 0, 2, 2, 2, 2, 2, 2, 2, 2, 0, 3, 3, 3, 3, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 },
		{ 2, 0, 0, 0, 0, 0, 2, 2, 2, 2, 0, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2 },
		{ 2, 2, 3, 3, 3, 0, 2, 2, 2, 2, 0, 3, 3, 3, 3, 2, 3, 3, 3, 3, 3, 4, 4, 4, 0, 2, 2, 2 },
		{ 2, 2, 3, 3, 3, 0, 2, 2, 2, 2, 0, 3, 3, 3, 3, 2, 3, 3, 3, 3, 3, 4, 4, 4, 0, 2, 2, 2 },
		{ 2, 2, 3, 3, 3, 0, 2, 2, 2, 2, 0, 3, 3, 3, 3, 2, 3, 3, 3, 3, 0, 0, 0, 0, 0, 2, 2, 2 },
		{ 2, 2, 3, 3, 3, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 2, 3, 3, 3, 3, 0, 2, 2, 2, 2, 2, 2, 2 },
		{ 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 3, 3, 3, 3, 0, 2, 2, 2, 2, 2, 2, 2 },
		{ 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 3, 3, 3, 3, 0, 3, 3, 3, 3, 3, 3, 2 },
		{ 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 3, 3, 0, 0, 0, 3, 3, 3, 3, 3, 3, 2 },
		{ 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 3, 3, 0, 2, 2, 2, 2, 2, 3, 3, 3, 2 },
		{ 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 0, 2, 2, 2, 2, 2, 3, 3, 3, 2 },
		{ 2, 2, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 2, 2, 2, 2, 2, 2, 3, 3, 3, 2 },
		{ 2, 2, 0, 4, 4, 4, 4, 0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 2, 2, 2, 2, 2, 2, 3, 3, 3, 2 },
		{ 2, 2, 0, 4, 4, 4, 4, 0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 2, 2, 2, 2, 2, 2, 3, 3, 3, 2 },
		{ 2, 2, 0, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 3, 3, 3, 2 },
		{ 2, 2, 0, 4, 4, 4, 4, 3, 3, 2, 2, 3, 2, 2, 3, 2, 2, 3, 2, 2, 2, 2, 2, 2, 3, 3, 3, 2 },
		{ 2, 2, 0, 4, 4, 4, 4, 3, 3, 2, 2, 3, 2, 2, 3, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2 },
		{ 2, 2, 0, 4, 4, 4, 4, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2 },
		{ 2, 2, 0, 4, 4, 4, 4, 3, 3, 2, 2, 3, 2, 2, 3, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2 },
		{ 2, 2, 0, 4, 4, 4, 4, 3, 3, 2, 2, 3, 2, 2, 3, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2 },
		{ 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2 },
		{ 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 2 },
		{ 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 2 },
		{ 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 2 },
		{ 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 2 },
		{ 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 2 },
		{ 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 6, 2 },
		{ 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 } };
	
	
	public static final int SPRITE_SIZE = 16;
	public static final int NUMBER_OF_CREEPS = 5;

	@Override
	protected void init() {
		OverlapProcessor overlapProcessor = new OverlapProcessorDefaultImpl();

		MoveBlockerChecker moveBlockerChecker = new MoveBlockerCheckerDefaultImpl();
		moveBlockerChecker.setMoveBlockerRules(new OctolinkMoveBlockers());
		
		OctolinkOverlapRules overlapRules = new OctolinkOverlapRules(new Point(8 * SPRITE_SIZE, 8 * SPRITE_SIZE),
				new Point(1 * SPRITE_SIZE, 1 * SPRITE_SIZE), life[0], score[0], endOfGame);
		overlapProcessor.setOverlapRules(overlapRules);

		universe = new GameUniverseDefaultImpl(moveBlockerChecker, overlapProcessor);
		overlapRules.setUniverse(universe);

		gameBoard = new GameUniverseViewPortDefaultImpl(canvas, universe);
		((CanvasDefaultImpl) canvas).setDrawingGameBoard(gameBoard);
		
		// Filling up the universe with basic non movable entities and inclusion in the universe
		for (int i = 0; i < 31; ++i) {
			for (int j = 0; j < 28; ++j) {
				if (tab[i][j] == 0) {
					universe.addGameEntity(new Path(canvas, new Point(j * SPRITE_SIZE, i * SPRITE_SIZE)));
				}
				if (tab[i][j] == 1) {
					universe.addGameEntity(new Spawn(new Point(j * SPRITE_SIZE, i * SPRITE_SIZE)));
				}
				if (tab[i][j] == 2) {
					universe.addGameEntity(new Wall(canvas, new Point(j * SPRITE_SIZE, i * SPRITE_SIZE)));
				}
				if (tab[i][j] == 3) {
					universe.addGameEntity(new Grass(canvas, new Point(j * SPRITE_SIZE, i * SPRITE_SIZE)));
				}
				if (tab[i][j] == 4) {
					universe.addGameEntity(new Water(canvas, new Point(j * SPRITE_SIZE, i * SPRITE_SIZE)));
				}
				if (tab[i][j] == 6) {
					universe.addGameEntity(new Zelda(canvas, new Point(j * SPRITE_SIZE, i * SPRITE_SIZE)));
				}
			}
		}
		
		// Link definition and inclusion in the universe
		Link link = new Link(canvas);
		GameMovableDriverDefaultImpl linkDriver = new GameMovableDriverDefaultImpl();
		MoveStrategyKeyboard keyStr = new OctolinkMoveStrategyKeyboard();
		linkDriver.setStrategy(keyStr);
		linkDriver.setmoveBlockerChecker(moveBlockerChecker);
		canvas.addKeyListener(keyStr);
		link.setDriver(linkDriver);
		link.setPosition(new Point(8 * SPRITE_SIZE, 8 * SPRITE_SIZE));
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
			creep.setPosition(new Point(1 * SPRITE_SIZE, 1 * SPRITE_SIZE));
			universe.addGameEntity(creep);
			overlapRules.addCreep(creep);
		}
	}

	public WaveOne(Game g) {
		super(g);
		canvas = g.getCanvas();
	}

}
