package entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import audio.Audio;

public abstract class Entity {
	
	protected static float moveSpeed = 6;
	
	// Position
	protected float x;
	protected float y;
	
	// Size
	public static int WIDTH = 35;
	public static int HEIGHT = 35;
	
	// Collision
	protected int cwidth = 25;
	protected int cheight = 25;
	
	// Animation
	protected Animation animation;
	protected int currentAction;
	protected int lastAction;
	
	// Audio
	protected Audio audio;
	
	public Entity(float x, float y) {
		this.x = x;
		this.y = y;
		animation = new Animation();
		audio = new Audio();
	}
	
	public boolean intersects(Entity e) {
		Rectangle r1 = getRectangle();
		Rectangle r2 = e.getRectangle();
		return r1.intersects(r2);
	}
	
	public static void increaseMoveSpeed(float inc) {
		moveSpeed += inc;
	}
	
	public static float getMoveSpeed() {
		return moveSpeed;
	}
	
	public Rectangle getRectangle() {
		return new Rectangle((int)x, (int)y, cwidth, cheight);
	}
	
	public float getX() {
		return x;
	}
	
	public int getWidth() {
		return WIDTH;
	}
	
	public Animation getAnimation() {
		return animation;
	}
	
	public abstract void update();
	public void draw(Graphics2D g) {
		g.drawImage(animation.getImage(), (int)x, (int)y, null);
	}
	
}
