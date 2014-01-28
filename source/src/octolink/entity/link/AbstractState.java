package octolink.entity.link;

public abstract class AbstractState implements LinkState {
	
	protected int transitionType;

	@Override
	public int getTransitionType() {
		return transitionType;
	}
	
	@Override
	public void setTransitionType(int type) {
		transitionType = type;
	}
}
