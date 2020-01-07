package gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import Main.GamePanel;
import score.ScoreManager;

public class WinGame extends GameState {

	private String[] options = {"Chơi lại", "Xem Top 4", "Về Menu"};
	private int currentChoice;
	
	public WinGame(GameStateManager stateManager) {
		super(stateManager);
		init();
	}

	@Override
	public void init() {
		currentChoice = 0;
	}

	@Override
	public void update() {
		
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.red);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		drawCenteredString(g, "Chiến thắng!", Color.yellow, new Font("Arial", Font.BOLD, 30), 100);
		if (ScoreManager.indexWhereHigherThanHighScore() == -1) {
			drawCenteredString(g, "Điểm của bạn chưa đủ cao để vào Top 4", Color.yellow, new Font("Arial", Font.ITALIC, 15), 150);
		}
		else drawCenteredString(g, "Điểm của bạn đã được lưu", Color.yellow, new Font("Arial", Font.ITALIC, 15), 150);
		for (int i = 0; i < options.length; i++) {
			Color color = Color.yellow;
			if (i == currentChoice) color = Color.blue;
			drawCenteredString(g, options[i], color, new Font("Arial", Font.BOLD, 15), 200 + 50 * i);
		}
	}
	
	private void select() {
		switch(currentChoice) {
			case 0:
				stateManager.setState(GameStateManager.SELECT_CHARACTERS);
				break;
			case 1:
				stateManager.setState(GameStateManager.HIGH_SCORE);
				break;
			case 2:
				stateManager.setState(GameStateManager.MENU);
				break;
		}
	}

	@Override
	public void keyPressed(int k) {
		if (k == KeyEvent.VK_DOWN) {
			currentChoice++;
			if (currentChoice == options.length) currentChoice = 0;
		}
		if (k == KeyEvent.VK_UP) {
			currentChoice--;
			if (currentChoice < 0) currentChoice = options.length - 1;
		}
		if (k == KeyEvent.VK_ENTER) {
			select();
		}
	}

	@Override
	public void keyReleased(int k) {
		
	}

}
