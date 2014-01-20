package octolink.rule;

import gameframework.base.ObservableValue;
import gameframework.base.Overlap;
import gameframework.game.GameUniverse;
import gameframework.game.OverlapRulesApplierDefaultImpl;

import java.awt.Point;
import java.util.Vector;

import octolink.entity.Creep;
import octolink.entity.Link;
import octolink.entity.Wall;
import octolink.entity.Water;
import octolink.entity.Zelda;

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

	public void overlapRule(Link l, Creep c) {
		System.out.println(l.getPosition().getX() + "," + l.getPosition().getY());
		System.out.println("creep");
	}

	public void overlapRule(Link l, Wall w) {
		System.out.println(l.getPosition().getX() + "," + l.getPosition().getY());
		System.out.println(w.getPosition().getX() + "," + w.getPosition().getY());
		System.out.println("wall");
	}

	public void overlapRule(Link l, Water w) {
		System.out.println(l.getPosition().getX() + "," + l.getPosition().getY());
		System.out.println("water");
	}

	public void overlapRule(Link l, Zelda z) {
		System.out.println(l.getPosition().getX() + "," + l.getPosition().getY());
		System.out.println("zelda");
	}
}
