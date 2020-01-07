package entities.bonuses;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.Player;

public class Heart extends Bonus {
	
	private BufferedImage img;

	public Heart(float x, float y) {
		super(x, y);
		
		try {
			img = ImageIO.read(getClass().getResourceAsStream("/bonuses/Heart.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
		x -= moveSpeed;
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(img, (int)x, (int)y, null);
	}

	@Override
	public boolean checkMeetPlayer(Player player) {
		if (this.intersects(player)) {
			player.addHealth();
			return true;
		}
		return false;
	}

}
