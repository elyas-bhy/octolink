package octolink.rule;

import gameframework.base.ObservableValue;
import gameframework.base.Overlap;
import gameframework.game.GameUniverse;
import gameframework.game.OverlapRulesApplierDefaultImpl;

import java.awt.Point;
import java.util.Vector;

import octolink.entity.Creep;
import octolink.entity.Link;

public class OctolinkOverlapRules extends OverlapRulesApplierDefaultImpl {
	
	protected GameUniverse universe;
	protected Vector<Creep> creeps = new Vector<Creep>();

	protected Point linkStartPos;
	protected Point creepStartPos;
	protected boolean manageLinkDeath;
	private final ObservableValue<Integer> score;
	private final ObservableValue<Integer> life;
	private final ObservableValue<Boolean> endOfGame;

	public OctolinkOverlapRules(Point pacPos, Point gPos,
			ObservableValue<Integer> life, ObservableValue<Integer> score,
			ObservableValue<Boolean> endOfGame) {
		linkStartPos = (Point) pacPos.clone();
		creepStartPos = (Point) gPos.clone();
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
	}
}
