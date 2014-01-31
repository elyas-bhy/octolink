package octolink;

import static org.junit.Assert.assertTrue;

import java.awt.Canvas;

import octolink.entity.Creep;
import octolink.entity.WarriorCreep;
import octolink.entity.link.DefenderState;
import octolink.entity.link.FighterState;
import octolink.entity.link.Link;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OctolinkTest {
	Creep creep;
	Link link;
	Canvas defaultCanvas;
	
	@Before
	public void setUp() {
		defaultCanvas = new Canvas();
		link = new Link(defaultCanvas);
		creep = new WarriorCreep(defaultCanvas);
	}
	
	@After
	public void tearDown() {
	}
	
	@Test
	public void parryTest() {
		link.setHealth(11);
		link.parry(10);
		assertTrue(link.getHealth() == 1);
	}
	
	@Test
	public void fighterTest() {
		int creepHealth = creep.getHealth();
		link.setState(new FighterState());
		creep.parry(link.strike());
		assertTrue(creep.getHealth() == creepHealth - link.strike());
	}
	
	@Test
	public void defenderTest() {
		link.setState(new DefenderState());
		int linkHealth = link.getHealth();
		link.parryFront(100);
		assertTrue(linkHealth == link.getHealth());
	}

}
