package entities.bonuses;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.Player;
import score.ScoreManager;

public class Coin extends Bonus {
	
	private int value = 50;
	
	private BufferedImage img;

	public Coin(float x, float y) {
		super(x, y);
		
		try {
			img = ImageIO.read(getClass().getResourceAsStream("/bonuses/Coin.png"));
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
			ScoreManager.addScore(value);
			return true;
		}
		return false;
	}

}
