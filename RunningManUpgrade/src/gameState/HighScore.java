package gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import Main.GamePanel;
import score.ScoreManager;

public class HighScore extends GameState {
	
	private Color titleColor;
	private Font titleFont;
	
	private String goBack = "<- Quay lại";

	public HighScore(GameStateManager stateManager) {
		super(stateManager);
		init();
	}
	
	@Override
	public void init() {
		titleColor = Color.black;
		titleFont = new Font("Arial", Font.BOLD, 28);
		
		ScoreManager.readHighScoreFromFile();
	}

	@Override
	public void update() {
		
	}

	@Override
	public void draw(Graphics2D g) {
		// Draw Title
		drawCenteredString(g, "Điểm cao", titleColor, titleFont, 100);
		
		// Draw Score
		ScoreManager.drawHighScore(g);
		
		// Draw Back
		g.setColor(Color.blue);
		g.drawString(goBack, 20, GamePanel.HEIGHT - 100);
	}

	@Override
	public void keyPressed(int k) {
		if (k == KeyEvent.VK_ENTER) {
			stateManager.setState(stateManager.getLastState());
		}
	}

	@Override
	public void keyReleased(int k) {
		
	}

}
