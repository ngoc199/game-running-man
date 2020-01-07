package gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class Menu extends GameState {
	
	private String[] options = {"Chơi ngay",
			"Hướng dẫn chơi",
			"Điểm cao",
			"Thoát"};
	
	private int currentChoice = 0;
	
	private Color titleColor;
	private Font titleFont;
	private Font optionsFont;

	public Menu(GameStateManager stateManager) {
		super(stateManager);
		init();
	}
	
	@Override
	public void init() {
		titleColor = Color.red;
		titleFont = new Font("Century Gothic", Font.BOLD, 28);
		optionsFont = new Font("Arial", Font.BOLD, 15);
	}

	@Override
	public void update() {
		
	}

	@Override
	public void draw(Graphics2D g) {
		// Draw Title
		drawCenteredString(g, "Running Man", titleColor, titleFont, 100);
		
		// Draw Options
		Color optionsColor;
		for (int i = 0; i < options.length; i++) {
			if (i != currentChoice) {
				optionsColor = Color.black;
			}
			else {
				optionsColor = Color.blue;
			}
			drawCenteredString(g, options[i], optionsColor, optionsFont, 150 + 50 * i);
		}
	}
	
	private void select() {
		switch(currentChoice) {
			case 0: // Select Character, then start
				stateManager.setState(GameStateManager.SELECT_CHARACTERS);
				break;
			case 1: // Instruction
				stateManager.setState(GameStateManager.INSTRUCTION);
				break;
			case 2: // High Score
				stateManager.setState(GameStateManager.HIGH_SCORE);
				break;
			case 3: // Quit
				System.exit(0);
		}
	}

	@Override
	public void keyPressed(int k) {
		if (k == KeyEvent.VK_ENTER) {
			select();
		}
		else if (k == KeyEvent.VK_UP) {
			currentChoice--;
			if (currentChoice == -1) currentChoice = options.length - 1;
		}
		else if (k == KeyEvent.VK_DOWN) {
			currentChoice++;
			if (currentChoice == options.length) currentChoice = 0;
		}
	}

	@Override
	public void keyReleased(int k) {
		
	}

}
