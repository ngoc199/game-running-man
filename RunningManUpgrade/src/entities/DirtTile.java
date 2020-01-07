package entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class DirtTile extends Entity {
	
	private int moveSpeed = 3;
	
	private BufferedImage dirt;

	public DirtTile(float x, float y) {
		super(x, y);
		init();
	}
	
	private void init() {
		// Load Image
		try {
			dirt = ImageIO.read(getClass().getResourceAsStream("/tiles/dirtTile.png"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
		x -= moveSpeed;
		if (x <= -dirt.getWidth()) {
			x = dirt.getWidth();
		}
	}

	public void draw(Graphics2D g) {
		g.drawImage(dirt, (int)x, (int)y, null);
	}
}
