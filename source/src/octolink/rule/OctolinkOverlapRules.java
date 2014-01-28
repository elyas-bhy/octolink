package octolink.rule;

import gameframework.base.MoveStrategyStraightLine;
import gameframework.base.ObservableValue;
import gameframework.base.Overlap;
import gameframework.base.SpeedVector;
import gameframework.game.GameMovableDriver;
import gameframework.game.GameMovableDriverDefaultImpl;
import gameframework.game.GameUniverse;
import gameframework.game.OverlapRulesApplierDefaultImpl;

import java.awt.Point;
import java.util.Vector;

import octolink.entity.Creep;
import octolink.entity.Grass;
import octolink.entity.Wall;
import octolink.entity.WarriorCreep;
import octolink.entity.link.Link;

public class OctolinkOverlapRules extends OverlapRulesApplierDefaultImpl {
	
	protected GameUniverse universe;
	protected Vector<Creep> creeps = new Vector<Creep>();

	protected Point linkStartPos;
	protected Point creepStartPos;
	protected boolean manageLinkDeath;
	private final ObservableValue<Integer> score;
	private final ObservableValue<Integer> life;
	private final ObservableValue<Boolean> endOfGame;

	public OctolinkOverlapRules(Point linkPos, Point creepPos,
			ObservableValue<Integer> life, ObservableValue<Integer> score,
			ObservableValue<Boolean> endOfGame) {
		linkStartPos = (Point) linkPos.clone();
		creepStartPos = (Point) creepPos.clone();
		this.life = life;
		this.score = score;
		this.endOfGame = endOfGame;
	}

	public void setUniverse(GameUniverse universe) {
		this.universe = universe;
	}
	
	public void addCreep(Creep c) {
		creeps.addElement(c);
	}

	@Override
	public void applyOverlapRules(Vector<Overlap> overlappables) {
		manageLinkDeath = true;
		super.applyOverlapRules(overlappables);
	}

	public void overlapRule(Link l, WarriorCreep c) {
		Point a = l.getPosition();
		SpeedVector creepSpeedVector = c.getSpeedVector();
		GameMovableDriver oldLinkDriver = l.getDriver();
		
		GameMovableDriverDefaultImpl linkDriver = new GameMovableDriverDefaultImpl();
		Point destination = new Point(
					(int) (a.getX() + creepSpeedVector.getDirection().getX() * 100),
					(int) (a.getY() + creepSpeedVector.getDirection().getY() * 100));

		linkDriver.setStrategy(new MoveStrategyStraightLine(a, destination));
		l.setDriver(linkDriver);
		for(int i = 0; i < 3; ++i)
			l.oneStepMove();
		l.setDriver(oldLinkDriver);
	}
	
	public void overlapRule(Link l, Wall w) {
		l.setPosition(linkStartPos);
	}
}
