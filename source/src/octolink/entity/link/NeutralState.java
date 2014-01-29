package octolink.entity.link;


public class NeutralState extends AbstractState {

	@Override
	public String getValue() {
		return "";
	}
	
	@Override
	public int parry(int damage) {
		return 0 - damage;
	}

	@Override
	public int strike() {
		return 0;
	}
}
