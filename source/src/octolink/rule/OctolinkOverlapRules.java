package octolink.rule;

import gameframework.base.ObservableValue;
import gameframework.base.Overlap;
import gameframework.game.GameUniverse;
import gameframework.game.OverlapRulesApplierDefaultImpl;

import java.awt.Point;
import java.util.Vector;

import octolink.entity.Creep;
import octolink.entity.Wall;
import octolink.entity.WarriorCreep;
import octolink.entity.Water;
import octolink.entity.Zelda;
import octolink.entity.link.Link;

public class OctolinkOverlapRules extends OverlapRulesApplierDefaultImpl {
	
	protected GameUniverse universe;
	protected Vector<Creep> creeps = new Vector<Creep>();

	protected Point linkStartPos;
	protected Point creepStartPos;
	protected boolean manageLinkDeath;
	private final ObservableValue<Integer> score;
	private final ObservableValue<Integer> life;
	private final ObservableValue<Integer> lifeZelda;
	private final ObservableValue<Boolean> endOfGame;

	public OctolinkOverlapRules(Point linkPos, Point creepPos,
			ObservableValue<Integer> life, ObservableValue<Integer> lifeZelda,
			ObservableValue<Integer> score,	ObservableValue<Boolean> endOfGame) {
		linkStartPos = (Point) linkPos.clone();
		creepStartPos = (Point) creepPos.clone();
		this.life = life;
		this.lifeZelda = lifeZelda;
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
		l.collide(c);
		if(c.getHealth() <= 0) {
			universe.removeGameEntity(c);
			creeps.remove(c);
		} else if(l.getHealth() <= 0){
			l.setHealth(3);
			l.setPosition(linkStartPos);
		}
	}

	public void overlapRule(Zelda z, WarriorCreep c) {
		lifeZelda.setValue(lifeZelda.getValue()-1);
	}
	
	public void overlapRule(Link l, Wall w) {
		l.setPosition(linkStartPos);
	}
	
	public void overlapRule(Link l, Water w) {
		l.setPosition(linkStartPos);
	}
}
