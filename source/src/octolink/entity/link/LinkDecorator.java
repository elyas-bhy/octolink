package octolink.entity.link;

import java.awt.Graphics;

public abstract class LinkDecorator extends AbstractLink {

	protected AbstractLink _link;

	public LinkDecorator(AbstractLink link) {
		this._link = link;
	}

	@Override
	public void draw(Graphics g) {
		_link.draw(g);
	}

	@Override
	public void oneStepMoveAddedBehavior() {
		_link.oneStepMoveAddedBehavior();
	}
}
