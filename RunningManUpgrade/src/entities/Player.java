package entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Player extends Entity {
	
	private int health;
	private final int jumpSpeed = 5;
	private final int fallSpeed = 5;
	private final int maxJumpHeight = HEIGHT / 2;
	
	private boolean canJump;
	private boolean jumping;
	private boolean falling;
	private boolean hurt;
	
	// Animation
	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = {6, 4, 4, 4, 8};
	private final int[] delay = {50, 70, 70, 50, 70};
	
	private int currentAction;
	private int lastAction;
	
	// Animation Actions
	private static final int RUN = 0;
	private static final int JUMP = 1;
	private static final int FALL = 2;
	private static final int HURT = 3;
	private static final int DIE = 4;
	
	// Audio Paths
	private String[] audioPath = {
			"", "/audio/Jump.wav", "", "/audio/Hit_Hurt.wav", "/audio/Explosion.wav"
	};
//	private final String jumpAudio = "/audio/Jump.wav";
//	private final String hurtAudio = "/audio/Hit_Hurt.wav";
//	private final String pickAudio = "/audio/Pickup_Coin.wav";
//	private final String dieAudio = "/audio/Explosion.wav";

	public Player(float x, float y, String path) {
		super(x, y);
		
		health = 3;		
		
		// Load Sprites
		try {
			BufferedImage spriteSheet = ImageIO.read(getClass().getResourceAsStream(path));
			sprites = new ArrayList<BufferedImage[]>();
			
			for (int i = 0; i < 5; i++) {
				BufferedImage[] temp = new BufferedImage[numFrames[i]];
				
				for (int j = 0; j < numFrames[i]; j++) {
					temp[j] = spriteSheet.getSubimage(WIDTH * j, HEIGHT * (i + 1), WIDTH, HEIGHT); 
				}
				
				sprites.add(temp);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		currentAction = RUN;
		animation.setFrames(sprites.get(RUN));
		animation.setDelay(delay[RUN]);
		
		canJump = true;
	}
	
	private boolean contactTop() {
		return y - jumpSpeed < maxJumpHeight;
	}
	
	private boolean contactBottom() {
		return y + fallSpeed > HEIGHT * 3;
	}
	
//	public void increaseJumpSpeed(int inc) {
//		jumpSpeed += inc;
//	}
	
	public boolean isDead() {
		return currentAction == DIE;
	}
	
	public void setJump(boolean b) {
		jumping = b;
	}
	
	public void setFall(boolean b) {
		falling = b;
	}
	
	public void setHurt(boolean b) {
		hurt = b;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void loseHealth() {
		health--;
		hurt = true;
	}
	
	public void addHealth() {
		health++;
	}
	
	@Override
	public void update() {
		
		lastAction = currentAction;
		
		if (!hurt) currentAction = RUN;
		
		if (health <= 0) {
			currentAction = DIE;
		}
		
		// Jumping
		else if (jumping && !contactTop() && canJump) {
			y -= jumpSpeed;
			currentAction = JUMP;
		}
		else if (contactTop() || falling) {
			y += fallSpeed;
			if (contactBottom()) {
				falling = false;
				canJump = true;
				currentAction = RUN;
			}
			else {
				falling = true;
				canJump = false;
				currentAction = FALL;
			}
		}
		
		if (hurt && currentAction != DIE) {
			currentAction = HURT;
		}
		
		// Set Animation & Audio
		if (lastAction != currentAction) {
			animation.setFrames(sprites.get(currentAction));
			animation.setDelay(delay[currentAction]);
			if (currentAction != RUN && currentAction != FALL) {
				audio.setAudio(audioPath[currentAction]);
				audio.playAudio();
			}
			lastAction = currentAction;
		}
		
		// Update Animation
		if (currentAction == RUN) {
			animation.update();
		}
		else if (!animation.hasPlayedOnce()) {
			animation.update();
		}
		
		if (hurt && animation.hasPlayedOnce()) {
			hurt = false;
		}
		
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
	}
	

}
