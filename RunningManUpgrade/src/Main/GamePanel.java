package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import gameState.GameStateManager;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements KeyListener, Runnable {
	
	public static final int WIDTH = 400;
	public static final int HEIGHT = 400;
	public static final int SCALE = 2;
	
	// Game Thread
	private Thread thread;
	private boolean running;
	private int fps = 60;
	private long targetTime = 1000 / fps;
	
	// Graphics
	private Graphics2D g;
	
	private BufferedImage img;
	
	// Game State Manager
	private GameStateManager stateManager;
	
	public GamePanel() {
		super();
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setFocusable(true);
		requestFocus();
	}
	
	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}
	
	private void init() {
		
		img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		g = (Graphics2D) img.getGraphics();
		
		running = true;
		
		stateManager = new GameStateManager();
	}

	@Override
	public void run() {
		
		init();
		
		long start;
		long elapsed;
		long wait;
		
		// Game Loop
		while (running) {
			start = System.nanoTime();
			
			update();
			draw();
			drawToScreen();
			
			elapsed = System.nanoTime() - start;
			
			wait = targetTime - elapsed / 1000000;
			
			if (wait < 0) wait = 5;
			
			try {
				Thread.sleep(wait);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		stateManager.keyPressed(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		stateManager.keyReleased(e.getKeyCode());
	}
	
	private void update() {
		stateManager.update();
	}
	
	private void draw() {
		g.clearRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
		g.setColor(new Color(9, 179, 156));
		g.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
		stateManager.draw(g);
	}
	
	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(img, 0, 0,
				WIDTH * SCALE, HEIGHT * SCALE,
				null);
		g2.dispose();
	}

}
