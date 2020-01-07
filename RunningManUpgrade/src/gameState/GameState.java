package gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import Main.GamePanel;

public abstract class GameState {

	protected GameStateManager stateManager;
	
	public GameState(GameStateManager stateManager) {
		this.stateManager = stateManager;
	}
	
	public abstract void init();
	public abstract void update();
	public abstract void draw(Graphics2D g);
	public abstract void keyPressed(int k);
	public abstract void keyReleased(int k);
	
	protected void drawCenteredString(Graphics2D g, String str, Color color, Font font, int y) {
		FontMetrics metrics = g.getFontMetrics(font);
		g.setColor(color);
		g.setFont(font);
		g.drawString(str, (GamePanel.WIDTH - metrics.stringWidth(str)) / 2, y);
	}
}
