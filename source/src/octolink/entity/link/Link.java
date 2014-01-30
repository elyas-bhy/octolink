package octolink.entity.link;

import gameframework.base.Drawable;
import gameframework.base.Overlappable;
import gameframework.game.GameEntity;
import gameframework.game.GameMovable;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import octolink.entity.WarriorCreep;
import octolink.gameframework.game.OctolinkSpriteManager;
import octolink.util.Sprite;
import octolink.util.Utils;

public class Link extends GameMovable implements Drawable, GameEntity, Overlappable, KeyListener {

	private int health = 3;
	private boolean moving;
	private Sprite sprite;
	protected LinkState state;
	protected final OctolinkSpriteManager spriteManager;
	
	public Link(Canvas defaultCanvas) {
		int[] rows = {11, 11, 11, 11, 5, 5, 5, 5, 8, 8, 8, 8, 3, 5, 4, 4, 8, 9, 8, 8};
		sprite = new Sprite("images/link_sprites.png", 24, 24, 1.0f, rows);
		state = new NeutralState();
		spriteManager = new OctolinkSpriteManager(sprite, defaultCanvas);
		spriteManager.setTypes("down", "up", "right", "left",
				"sword-down", "sword-up", "sword-right", "sword-left",
				"animation-sword-down", "animation-sword-up", "animation-sword-right", "animation-sword-left",
				"animation-shield-down", "animation-shield-up", "animation-shield-right", "animation-shield-left",
				"shield-down", "shield-up", "shield-right", "shield-left"
				);
	}

	@Override
	public void draw(Graphics g) {
		// Display boundingBox
		g.drawRect(getPosition().x + (int)getBoundingBox().getX(),
				getPosition().y + (int)getBoundingBox().getY(),
				getBoundingBox().width, getBoundingBox().height);

		Point p = getSpeedVector().getDirection();
		String spriteType = state.getValue() + Utils.getOrientation(p);
		
		if (state.getTransitionType() != 0) {
			spriteManager.handleAnimation(this, g, spriteType);
			state.setTransitionType(0);
			return;
		}

		if (getSpeedVector().getSpeed() == 0) {
			moving = false;
			spriteManager.reset();
		} else {
			moving = true;
			spriteManager.setType(spriteType);
		}

		spriteManager.draw(g, getPosition());
	}

	@Override
	public void oneStepMoveAddedBehavior() {
		if (moving) {
			spriteManager.increment();
		}
	}
	
	public LinkState getState() {
		return state;
	}

	public void setState(LinkState state) {
		this.state = state;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int h) {
		health = h;
	}

	@Override
	public Rectangle getBoundingBox() {
		Point p = getSpeedVector().getDirection();
		return state.getBoundingBox(sprite, p);			
	}

	@Override
	public void keyPressed(KeyEvent event) {
		int keycode = event.getKeyCode();
		switch (keycode) {
		case KeyEvent.VK_C:
			state = new NeutralState();
			state.setTransitionType(1);
			break;
		case KeyEvent.VK_V:
			state = new FighterState();
			state.setTransitionType(1);
			break;
		case KeyEvent.VK_B:
			state = new DefenderState();
			state.setTransitionType(2);
			break;
		case KeyEvent.VK_G:
			state = new FighterState();
			state.setTransitionType(2);
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {

	}

	@Override
	public void keyTyped(KeyEvent event) {

	}

	public void collide(WarriorCreep c) {
		Point linkDir = this.getSpeedVector().getDirection();
		Point creepDir = c.getSpeedVector().getDirection();
		if ((linkDir.getX() == -creepDir.getX() && linkDir.getX() != 0) || 
				linkDir.getY() == -creepDir.getY() && linkDir.getY() != 0) {
			state.collideFront(this, c);
		} else {
			this.state.collide(this, c);
		}
	}
	
	public int strike() {
		return state.strike();
	}
	
	public void parry(int damage) {
		this.health -= state.parry(damage);
	}

	public void parryFront(int damage) {
		this.health -= state.parryFront(damage);
	}

}
