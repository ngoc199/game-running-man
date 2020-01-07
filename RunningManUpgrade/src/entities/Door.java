package entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Door extends Entity {
	
	private BufferedImage img; 

	public Door(float x, float y) {
		super(x, y);
		init();
	}
	
	private void init() {
		// Load Image
		try {
			img = ImageIO.read(getClass().getResourceAsStream("/tiles/door.png"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
		x -= moveSpeed;
	}

	public void draw(Graphics2D g) {
		g.drawImage(img, (int)x, (int)y, null);
	}
}
