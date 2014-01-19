package octolink.rule;

import octolink.entity.Creep;
import octolink.entity.Path;
import octolink.entity.Terrain;
import gameframework.game.IllegalMoveException;
import gameframework.game.MoveBlockerRulesApplierDefaultImpl;

public class OctolinkMoveBlockers extends MoveBlockerRulesApplierDefaultImpl {

	public void moveBlockerRule(Creep c, Terrain t) throws IllegalMoveException {
		// Creeps can only walk through paths
		if (!(t instanceof Path)) {
			throw new IllegalMoveException();
		}
	}
}
