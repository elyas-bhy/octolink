package octolink;

import gameframework.base.MoveStrategyKeyboard;
import gameframework.base.ObservableValue;
import gameframework.game.CanvasDefaultImpl;
import gameframework.game.GameLevelDefaultImpl;
import gameframework.game.GameMovableDriverDefaultImpl;
import gameframework.game.GameUniverseDefaultImpl;
import gameframework.game.GameUniverseViewPortDefaultImpl;
import gameframework.game.MoveBlockerChecker;
import gameframework.game.OverlapProcessor;

import java.awt.Canvas;
import java.awt.Point;
import java.util.ArrayList;

import octolink.entity.Creep;
import octolink.entity.Grass;
import octolink.entity.Path;
import octolink.entity.Spawn;
import octolink.entity.Wall;
import octolink.entity.WarriorCreep;
import octolink.entity.Water;
import octolink.entity.Zelda;
import octolink.entity.link.Link;
import octolink.entity.link.NeutralState;
import octolink.gameframework.game.CreepMoveStrategy;
import octolink.gameframework.game.OctolinkGame;
import octolink.gameframework.game.OctolinkGameLevel;
import octolink.gameframework.game.OctolinkMoveBlockerChecker;
import octolink.gameframework.game.OctolinkMoveStrategyKeyboard;
import octolink.gameframework.game.OctolinkOverlapProcessor;
import octolink.rule.OctolinkMoveBlockers;
import octolink.rule.OctolinkOverlapRules;

public class WaveOne extends GameLevelDefaultImpl implements OctolinkGameLevel {
	Canvas canvas;

	// 0 : Path; 1 : Spawn; 2 : Walls; 3 : Grass; 4 : Water; 5 : Holes; 6 : Zelda
	static int[][] map = { 
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
	
	
	public static final int SPRITE_SIZE = 24;
	public static final int NUMBER_OF_CREEPS = 20;
	protected ObservableValue<Integer> lifeZelda[];
	
	public WaveOne(OctolinkGame g) {
		super(g);
		canvas = g.getCanvas();
		this.lifeZelda = g.lifeZelda();
	}

	@Override
	protected void init() {
		OverlapProcessor overlapProcessor = new OctolinkOverlapProcessor();

		MoveBlockerChecker moveBlockerChecker = new OctolinkMoveBlockerChecker();
		moveBlockerChecker.setMoveBlockerRules(new OctolinkMoveBlockers());
		
		OctolinkOverlapRules overlapRules = new OctolinkOverlapRules(new Point(8 * SPRITE_SIZE, 10 * SPRITE_SIZE),
				new Point(1 * SPRITE_SIZE, 1 * SPRITE_SIZE), life[0], lifeZelda[0], score[0], endOfGame);
		overlapProcessor.setOverlapRules(overlapRules);

		universe = new GameUniverseDefaultImpl(moveBlockerChecker, overlapProcessor);
		overlapRules.setUniverse(universe);

		gameBoard = new GameUniverseViewPortDefaultImpl(canvas, universe);
		((CanvasDefaultImpl) canvas).setDrawingGameBoard(gameBoard);
		
		// Creeps definition
		ArrayList<Creep> creepList = new ArrayList<Creep>();
		ArrayList<Integer> frequencyList = new ArrayList<Integer>();
		Creep creep;
		for (int t = 0; t < NUMBER_OF_CREEPS; ++t) {
			GameMovableDriverDefaultImpl creepDriver = new GameMovableDriverDefaultImpl();
			creepDriver.setmoveBlockerChecker(moveBlockerChecker);
			creep = new WarriorCreep(canvas);
			CreepMoveStrategy strategy = new CreepMoveStrategy(creep, map);
			creepDriver.setStrategy(strategy);
			creep.setDriver(creepDriver);
			creep.setPosition(new Point(1 * SPRITE_SIZE, 1 * SPRITE_SIZE));
			creepList.add(creep);
		}
		
		for(int d = 10; d > 0; d = d-2) {
			frequencyList.add(new Integer(d));
		}
		
		// Filling up the universe with basic non movable entities and inclusion in the universe
		for (int i = 0; i < 31; ++i) {
			for (int j = 0; j < 28; ++j) {
				if (map[i][j] == 0) {
					universe.addGameEntity(new Path(canvas, new Point(j * SPRITE_SIZE, i * SPRITE_SIZE)));
				}
				if (map[i][j] == 1) {
					universe.addGameEntity(new Spawn(universe, overlapRules, new Point(j * SPRITE_SIZE, i * SPRITE_SIZE), frequencyList, creepList));
				}
				if (map[i][j] == 2) {
					universe.addGameEntity(new Wall(canvas, new Point(j * SPRITE_SIZE, i * SPRITE_SIZE)));
				}
				if (map[i][j] == 3) {
					universe.addGameEntity(new Grass(canvas, new Point(j * SPRITE_SIZE, i * SPRITE_SIZE)));
				}
				if (map[i][j] == 4) {
					universe.addGameEntity(new Water(canvas, new Point(j * SPRITE_SIZE, i * SPRITE_SIZE)));
				}
				if (map[i][j] == 6) {
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
		link.setDriver(linkDriver);
		link.setPosition(new Point(8 * SPRITE_SIZE, 10 * SPRITE_SIZE));
		link.setState(new NeutralState());
		canvas.addKeyListener(keyStr);
		canvas.addKeyListener(link);
		universe.addGameEntity(link);
	}
	
	public int[][] getMap() {
		return map;
	}

}
