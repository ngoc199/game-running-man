package entities.monster;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Bat extends Monster {

	private BufferedImage[] sprites;
	
	public Bat(float x, float y) {
		super(x, y);
		
		init();
	}
	
	private void init() {
		// Load Sprites
				try {
					BufferedImage spriteSheet = ImageIO.read(getClass().getResourceAsStream("/monsters/bat.png"));
					sprites = new BufferedImage[2];
					
					for (int i = 0; i < 2; i++) {
						sprites[i] = spriteSheet.getSubimage(WIDTH * i, 0, WIDTH, HEIGHT);
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
				
				animation.setFrames(sprites);
				animation.setDelay(60);
	}

	@Override
	public void update() {
		x -= moveSpeed;
		animation.update();
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
	}

}
