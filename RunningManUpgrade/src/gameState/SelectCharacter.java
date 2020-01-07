package gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entities.Animation;

public class SelectCharacter extends GameState {
	
	private ArrayList<BufferedImage[]> sprites;
	
	private Animation animation;
	
	private int currentChoice;
	private int lastChoice;
	private String[] options = {"Pink", "Owlet", "Dude"};
	
	private final int width = 35;
	private final int height = 35;
	
	private static final int NUM_CHAR = 3;
	private static final int PINK = 0;
	private static final int OWLET = 1;
	private static final int DUDE = 2;
	
	public SelectCharacter(GameStateManager stateManager) {
		super(stateManager);
		init();
	}

	@Override
	public void init() {
		// Load Sprites
		try {
			BufferedImage spriteSheet = ImageIO.read(getClass().getResourceAsStream("/players/CharacterSelect.png"));
			
			sprites = new ArrayList<BufferedImage[]>();
			
			for (int i = 0; i < NUM_CHAR; i++) {
				BufferedImage[] temp = new BufferedImage[4];
				
				for (int j = 0; j < 4; j++) {
					temp[j] = spriteSheet.getSubimage(width * j, height * i, width, height);
				}
				
				sprites.add(temp);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		animation = new Animation();
		animation.setFrames(sprites.get(PINK));
		animation.setDelay(400);
		
		currentChoice = PINK;
		lastChoice = currentChoice;
	}
	
	private void select() {
		String path = "/players/";
		
		switch(currentChoice) {
			case PINK:
				path += "pink.png";
				break;
			case OWLET:
				path += "owlet.png";
				break;
			case DUDE:
				path += "dude.png";
				break;
		}
		
		Play.setPath(path);
		stateManager.setState(GameStateManager.PLAY);
	}

	@Override
	public void update() {
		if (lastChoice != currentChoice) {
			animation.setFrames(sprites.get(currentChoice));
			animation.setDelay(400);
			lastChoice = currentChoice;
		}
		animation.update();
	}

	@Override
	public void draw(Graphics2D g) {
		// Draw Title
		drawCenteredString(g, "Chọn nhân vật", Color.black, new Font("Arial", Font.BOLD, 28), 100);
		
		for (int i = 0; i < options.length; i++) {
			
			// Draw Character Image
			BufferedImage character = sprites.get(i)[0];
			
			if (i == currentChoice) {
				character = animation.getImage();
			}
			
			character = resize(character, 70, 70);
			
			g.drawImage(character, 100 * i + 100, 160, null);
			
			// Draw Character Name
			g.setColor(Color.black);
			if (i == PINK && currentChoice == PINK) {
				g.setColor(Color.pink);
				
			}
			else if (i == OWLET && currentChoice == OWLET) {
				g.setColor(Color.white);
			}
			else if (i == DUDE && currentChoice == DUDE) {
				g.setColor(Color.blue);
			}
			
			g.setFont(new Font("Arial", Font.BOLD, 20));
			g.drawString(options[i], 100 * i + 100, 250);
			
		}
	}

	@Override
	public void keyPressed(int k) {
		if (k == KeyEvent.VK_UP || k == KeyEvent.VK_RIGHT) {
			currentChoice++;
			if (currentChoice == NUM_CHAR) {
				currentChoice = 0;
			}
		}
		if (k == KeyEvent.VK_DOWN || k == KeyEvent.VK_LEFT) {
			currentChoice--;
			if (currentChoice == -1) {
				currentChoice = NUM_CHAR - 1;
			}
		}
		if (k == KeyEvent.VK_ENTER) {
			select();
		}
	}

	@Override
	public void keyReleased(int k) {
		
	}

	 private BufferedImage resize(BufferedImage img, int height, int width) {
	        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	        Graphics2D g2d = resized.createGraphics();
	        g2d.drawImage(tmp, 0, 0, null);
	        g2d.dispose();
	        return resized;
	    }
	
}
