package octolink;

import gameframework.game.GameLevel;

import java.util.ArrayList;

import octolink.gameframework.game.OctolinkGame;

public class Main {

	public static void main(String[] args) {
		System.out.println("Octolink!");
		OctolinkGame g = new OctolinkGame();
		ArrayList<GameLevel> levels = new ArrayList<GameLevel>();

		levels.add(new WaveOne(g)); // only one level is available at this time
		
		g.setLevels(levels);
		g.start();
	}

}
