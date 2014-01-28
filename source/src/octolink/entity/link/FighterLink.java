package octolink.entity.link;

public class FighterLink extends LinkDecorator {

	public FighterLink(AbstractLink link) {
		super(link);
		linkState = "sword-";
	}
}
