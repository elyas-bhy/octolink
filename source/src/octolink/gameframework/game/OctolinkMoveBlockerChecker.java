package octolink.gameframework.game;

import gameframework.base.Movable;
import gameframework.base.SpeedVector;
import gameframework.game.MoveBlocker;
import gameframework.game.MoveBlockerChecker;
import gameframework.game.MoveBlockerRulesApplier;
import gameframework.game.MoveBlockerRulesApplierDefaultImpl;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

import octolink.gameframework.base.OctolinkIntersectTools;

public class OctolinkMoveBlockerChecker implements MoveBlockerChecker {
	private ConcurrentLinkedQueue<MoveBlocker> moveBlockers;
	private MoveBlockerRulesApplier moveBlockerRuleApplier;

	public OctolinkMoveBlockerChecker() {
		moveBlockers = new ConcurrentLinkedQueue<MoveBlocker>();
		this.moveBlockerRuleApplier = new MoveBlockerRulesApplierDefaultImpl();
	}

	public void addMoveBlocker(MoveBlocker p) {
		moveBlockers.add(p);
	}

	public void removeMoveBlocker(MoveBlocker p) {
		moveBlockers.remove(p);
	}

	public void setMoveBlockerRules(MoveBlockerRulesApplier moveBlockerRules) {
		this.moveBlockerRuleApplier = moveBlockerRules;
	}

	public boolean moveValidation(Movable m, SpeedVector mov) {
		Shape intersectShape = OctolinkIntersectTools.getIntersectShape(m, mov);
		Vector<MoveBlocker> moveBlockersInIntersection = new Vector<MoveBlocker>();
		Area intersectArea = new Area(intersectShape);
		Rectangle tmpIntersec = (intersectShape.getBounds());

		for (MoveBlocker moveBlocker : moveBlockers) {
			Rectangle tmpB = moveBlocker.getBoundingBox();
			if (tmpIntersec.intersects(tmpB)) {
				Area tmpArea = new Area(tmpB);
				tmpArea.intersect(intersectArea);
				if (!tmpArea.isEmpty()) {
					moveBlockersInIntersection.add(moveBlocker);
				}
			}
		}

		if (!moveBlockersInIntersection.isEmpty()) {
			return moveBlockerRuleApplier.moveValidationProcessing(
					moveBlockersInIntersection, m);
		}

		return true;
	}
}
