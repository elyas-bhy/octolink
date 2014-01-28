package octolink.entity.link;

public class DefenderLink extends LinkDecorator {

	public DefenderLink(AbstractLink link) {
		super(link);
		linkState = "shield-";
	}
}
