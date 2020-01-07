package gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextLayout;
import java.text.AttributedString;

import Main.GamePanel;

public class Instruction extends GameState {
	
	private Color titleColor;
	private Font titleFont;
	
	private String instruction = "Bấm phím Cách để nhảy qua chướng ngại vật. Nhặt trái tim và tiền để tăng điểm. Khi gặp cánh cửa có thể vào để lưu điểm. Hoặc nhảy qua để đạt điểm cao hơn.";
	private String warning = "Lưu ý: Khi nhân vật chết sẽ không lưu điểm. Cánh cửa là cách duy nhất để lưu điểm";
	private String goBack = "<- Quay lại";
	
	public Instruction(GameStateManager stateManager) {
		super(stateManager);
		init();
	}

	@Override
	public void init() {
		titleColor = Color.black;
		titleFont = new Font("Arial", Font.BOLD, 28);
	}

	@Override
	public void update() {
		
	}

	@Override
	public void draw(Graphics2D g) {
		// Draw Title
		drawCenteredString(g, "Hướng dẫn", titleColor, titleFont, 100);
		
		// Draw Instructions
		g.setColor(Color.black);
		g.setFont(new Font("Arial", Font.PLAIN, 15));
		drawParagraph(g, instruction, GamePanel.WIDTH, 150);
		drawParagraph(g, warning, GamePanel.WIDTH, 200);
		
		g.setColor(Color.blue);
		g.setFont(new Font("Arial", Font.BOLD, 15));
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
	
	void drawParagraph(Graphics2D g, String paragraph, float width, int y) {
	    LineBreakMeasurer linebreaker = new LineBreakMeasurer(new AttributedString(paragraph)
	        .getIterator(), g.getFontRenderContext());

	    while (linebreaker.getPosition() < paragraph.length()) {
	      TextLayout textLayout = linebreaker.nextLayout(width);

	      y += textLayout.getAscent();
	      textLayout.draw(g, 0, y);
	      y += textLayout.getDescent() + textLayout.getLeading();
	    }
	}

}
