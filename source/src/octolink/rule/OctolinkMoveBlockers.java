package octolink.rule;

import gameframework.game.IllegalMoveException;
import gameframework.game.MoveBlockerRulesApplierDefaultImpl;
import octolink.entity.AbstractBlockerTerrain;
import octolink.entity.Creep;
import octolink.entity.Grass;
import octolink.entity.Wall;
import octolink.entity.Zelda;

public class OctolinkMoveBlockers extends MoveBlockerRulesApplierDefaultImpl {
	
	public void moveBlockerRule(Creep c, AbstractBlockerTerrain t) throws IllegalMoveException {
		// Creeps can only walk through paths
		throw new IllegalMoveException();
	}

	public void moveBlockerRule(Zelda z, Wall w) throws IllegalMoveException {
		throw new IllegalMoveException();
	}
	
	/*public void moveBlockerRule(Zelda z, Grass g) throws IllegalMoveException {
		
	}*/
	
}
