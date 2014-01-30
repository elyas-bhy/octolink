package octolink.entity;

import gameframework.base.Overlappable;
import gameframework.game.GameEntity;
import gameframework.game.GameMovable;
import gameframework.game.GameMovableDriverDefaultImpl;
import gameframework.game.GameUniverse;
import gameframework.game.OverlapRulesApplierDefaultImpl;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import octolink.gameframework.game.ImmobileMoveStrategy;
import octolink.rule.OctolinkOverlapRules;

public class Spawn extends GameMovable implements GameEntity, Overlappable {
	
	public static final int SPRITE_SIZE = 24;
	protected GameUniverse universe;
	protected OctolinkOverlapRules overlapRules;
	protected Point position;
	protected int runCounter;
	protected ArrayList<Integer> frequency;
	protected ArrayList<Creep> creeps;

	public Spawn(GameUniverse univ, OctolinkOverlapRules rules, Point pos, ArrayList<Integer> freq, ArrayList<Creep> crps) {
		GameMovableDriverDefaultImpl driver = new GameMovableDriverDefaultImpl();
		ImmobileMoveStrategy strategy = new ImmobileMoveStrategy();
		driver.setStrategy(strategy);
		this.setDriver(driver);
		overlapRules = rules;
		universe = univ;
		position = pos;
		frequency = freq;
		creeps = crps;
		runCounter = 1;
	}

	public Point getPosition() {
		return position;
	}

	public Rectangle getBoundingBox() {
		return (new Rectangle((int) position.getX(), (int) position.getY(),
				SPRITE_SIZE, SPRITE_SIZE));
	}

	@Override
	public void oneStepMoveAddedBehavior() {
		if(creeps.size() > 0) {
			if(runCounter % frequency.get(0) == 0) {
				runCounter = 1;
				// make frequency list circular in order to apply patterns
				frequency.add(frequency.remove(0));
				Creep creep = creeps.remove(0);
				creep.setPosition(this.getPosition());
				universe.addGameEntity(creep);
				overlapRules.addCreep(creep);
			}
			++runCounter;
		}
		
	}
}
