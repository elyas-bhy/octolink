package octolink.entity.link;

import gameframework.base.Drawable;
import gameframework.base.Overlappable;
import gameframework.game.GameEntity;
import gameframework.game.GameMovable;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import octolink.entity.Creep;
import octolink.entity.WarriorCreep;
import octolink.gameframework.game.OctolinkSpriteManager;
import octolink.util.Sprite;
import octolink.util.Utils;


public class Link extends GameMovable implements Drawable, GameEntity,
		Overlappable, KeyListener {

	private static final int DEFAULT_INVULNERABILITY_TICKS = 30;
	private int invulnerableTicks;
	// 3 hearts with 4 quarters each
	private int health = 12;
	private boolean moving;
	private boolean stricking;
	private boolean gKeyPressed;

	private Sprite sprite;
	private LinkState state;
	private final OctolinkSpriteManager spriteManager;


	public Link(Canvas defaultCanvas) {
		int[] rows = { 11, 11, 11, 11, 5, 5, 5, 5, 8, 8, 8, 8, 3, 5, 4, 4, 8, 9, 8, 8 };
		sprite = new Sprite("images/link_sprites.png", 24, 24, 1.0f, rows);
		state = new NeutralState();
		state.setAnimationType(AnimationType.NONE);
		spriteManager = new OctolinkSpriteManager(sprite, defaultCanvas);
		spriteManager.setTypes("down", "up", "right", "left", "sword-down",
				"sword-up", "sword-right", "sword-left",
				"animation-sword-down", "animation-sword-up",
				"animation-sword-right", "animation-sword-left",
				"animation-shield-down", "animation-shield-up",
				"animation-shield-right", "animation-shield-left",
				"shield-down", "shield-up", "shield-right", "shield-left");
	}

	@Override
	public void draw(Graphics g) {
		Point p = getSpeedVector().getDirection();
		String spriteType = state.getValue() + Utils.getOrientation(p);

		if (state.getAnimationType() != AnimationType.NONE) {
			spriteManager.handleAnimation(this, g, spriteType);
			return;
		}

		if (getSpeedVector().getSpeed() == 0) {
			moving = false;
			spriteManager.reset();
		} else {
			moving = true;
			spriteManager.setType(spriteType);
		}

		if (invulnerableTicks > 0) {
			g.setXORMode(new Color(140, 60, 80));
		}

		spriteManager.draw(g, getPosition());
		g.setPaintMode();
	}

	@Override
	public void oneStepMoveAddedBehavior() {
		if (invulnerableTicks > 0) {
			--invulnerableTicks;
		}
		if (moving) {
			spriteManager.increment();
		}
	}

	@Override
	public Rectangle getBoundingBox() {
		return state.getBoundingBox(this);
	}
	
	public void collide(Creep c) {
		Point linkPos = this.getPosition();
		Point creepPos = c.getPosition();
		Point linkDir = this.getSpeedVector().getDirection();
		Point creepDir = c.getSpeedVector().getDirection();
		/*if ((creepPos.getX() > linkPos.getX() && linkDir.getX() > 0) ||
				(creepPos.getY() > linkPos.getY() && linkDir.getY() > 0) ||
				(creepPos.getX() < linkPos.getX() && linkDir.getX() < 0) ||
				(creepPos.getY() < linkPos.getY() && linkDir.getY() < 0)){*/
		if((creepDir.getX() == -linkDir.getX()) && linkDir.getX() != 0 ||
				(creepDir.getY() == -linkDir.getY()) && linkDir.getX() != 0) {
			state.collideFront(this, c);
		} else {
			state.collide(this, c);
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
	
	@Override
	public void keyPressed(KeyEvent event) {
		int keycode = event.getKeyCode();
		switch (keycode) {
		case KeyEvent.VK_C:
			state = new NeutralState();
			state.setAnimationType(AnimationType.SINGLE);
			break;
		case KeyEvent.VK_V:
			state = new FighterState();
			state.setAnimationType(AnimationType.SINGLE);
			break;
		case KeyEvent.VK_B:
			state = new DefenderState();
			state.setAnimationType(AnimationType.MULTIPLE);
			break;
		case KeyEvent.VK_G:
			if (!gKeyPressed) {
				state = new FighterState();
				state.setAnimationType(AnimationType.MULTIPLE);
				stricking = true;
				gKeyPressed = true;
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {
		int keycode = event.getKeyCode();
		switch (keycode) {
		case KeyEvent.VK_G:
			gKeyPressed = false;
			break;
		default:
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent event) {}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
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

	public int getInvulnerableTicks() {
		return invulnerableTicks;
	}

	public void setInvulnerableTicks(int ticks) {
		invulnerableTicks = ticks;
	}
	
	public void resetInvulnerableTicks() {
		invulnerableTicks = DEFAULT_INVULNERABILITY_TICKS;
	}

	public boolean isStricking() {
		return stricking;
	}
	
	public void setStricking(boolean b) {
		stricking = b;
	}

}
