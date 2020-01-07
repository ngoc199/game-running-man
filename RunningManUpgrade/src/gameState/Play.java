package gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import audio.Audio;
import entities.DirtTile;
import entities.Door;
import entities.Player;
import entities.Spawner;
import score.ScoreManager;

public class Play extends GameState {
	
	private static String PATH;
	
	private Player player;
	private Spawner spawner;
	
	private final int size = 35;
	
	
	// Pause
	private boolean isPaused;
	
	// Instruction
	private String instruction = "Nhấn W, Space, Mũi tên lên để nhảy";
	private boolean pressed = false;
	
	// Audio
	private Audio audio;
	private final String audioPath = "/audio/Background.wav";
	
	// Door
	private Door door;
	
	// Dirt Tile
	private DirtTile dirtTile1;
	private DirtTile dirtTile2;
	
	// Timer
	private long startTime;
	private long timeBtwFrames;
	
	// Spawner
	private int numFramesBtwSpawn;
	private int spawnCounter;
	private final int numMonsterIncrease = 5;
	private int numFramesBtwSpawnDecrease;
	private final int minNumFramesBtwSpawn = 20; // Minimum Distance that Player can jump over 
	
	// Speed Up
	private long elapseBtwSpeedUp;
	private final long timeBtwSpeedUp = 10000;
	private int speedIncreaseBtwTime;
	
	// Monster Between Door
	private int numMonstersTillDoorAppear;
	private int doorAppearCounter;
	private boolean hasDoor;
	
	public Play(GameStateManager stateManager) {
		super(stateManager);
		init();
	}

	public static void setPath(String path) {
		PATH = path;
	}
	
	@Override
	public void init() {
		
		isPaused = false;
		
		ScoreManager.init();
		
		// Audio
		audio = new Audio();
		audio.setAudio(audioPath);
		audio.playLoop();
		
		// Entities
		player = new Player(size, size * 3, PATH);
		spawner = new Spawner(size * 11, size * 3, player);
		
		dirtTile1 = new DirtTile(0, size * 4);
		dirtTile2 = new DirtTile(size * 12, size * 4);
		
		// Timer
		startTime = System.nanoTime();
		
		timeBtwFrames = 70;
		
		numFramesBtwSpawn = 100;
		spawnCounter = numFramesBtwSpawn;
		numFramesBtwSpawnDecrease = 20;
		
		elapseBtwSpeedUp = timeBtwSpeedUp;
		speedIncreaseBtwTime = 5;
		
		numMonstersTillDoorAppear = 5;
		doorAppearCounter = numMonstersTillDoorAppear;
		hasDoor = false;
	}

	@Override
	public void update() {
		long elapsed = (System.nanoTime() - startTime) / 1000000;
		elapseBtwSpeedUp -= elapsed;
		if (elapsed > timeBtwFrames && !isPaused) {
			if (player.isDead() && player.getAnimation().hasPlayedOnce()) {
				audio.stopAudio();
				stateManager.setState(GameStateManager.GAME_OVER);
			}
			else if (player.isDead()) {
				player.update();
			}
			else {
				ScoreManager.update();
				
				dirtTile1.update();
				dirtTile2.update();
				
				player.update();
				
				if (!hasDoor) {
					spawner.update();
				}
				else {
					door.update();
					spawner.update();
				}
				
				if (hasDoor && door.intersects(player)) {
					ScoreManager.readHighScoreFromFile();
					ScoreManager.setHighScore();
					ScoreManager.printHighScoreToFile();
					audio.stopAudio();
					stateManager.setState(GameStateManager.WIN_GAME);
				}
				else if (hasDoor && door.getX() < 0) {
					door = null;
					hasDoor = false;
					if (numFramesBtwSpawn - numFramesBtwSpawnDecrease >= minNumFramesBtwSpawn) {
						numFramesBtwSpawn -= numFramesBtwSpawnDecrease;
					}
					spawnCounter = numFramesBtwSpawn;
					
					numMonstersTillDoorAppear += numMonsterIncrease;
					doorAppearCounter = numMonstersTillDoorAppear; 
				}
				
				// Spawn Monsters & Door
				spawnCounter--;
				if (spawnCounter <= 0) {
					if (doorAppearCounter <= 0 && !hasDoor) {
						door = new Door(size * 11, size * 3);
						hasDoor = true;
					}
					else if (!hasDoor) {
						spawner.addEntity();
						spawnCounter = numFramesBtwSpawn;
						doorAppearCounter--;
					}
				}
				
				if (elapseBtwSpeedUp <= 0 && timeBtwFrames - speedIncreaseBtwTime >= 5 && !hasDoor) {
					timeBtwFrames -= speedIncreaseBtwTime;
					elapseBtwSpeedUp = timeBtwSpeedUp;
				}
				
			}
			
			startTime = System.nanoTime();
		}

	}

	@Override
	public void draw(Graphics2D g) {
		ScoreManager.draw(g);
		
		// Pause Instruction
		g.setFont(new Font("Arial", Font.PLAIN, 10));
		if (isPaused) {
			g.setColor(Color.red);
			g.drawString("Nhấn P để tiếp tục chơi", size * 3, size * 7);
		}
		else {
			g.setColor(Color.black);
			g.drawString("Nhấn P để tạm dừng", size * 3, size * 7);
		}
		
		// Play Instruction
		if (!pressed) {
			g.setFont(new Font("Arial", Font.BOLD, 10));
			g.setColor(Color.black);
			g.drawString(instruction, size * 3, size);
		}
		
		// Draw Entities
		player.draw(g);
		if (!hasDoor) spawner.draw(g);
		else {
			spawner.draw(g);
			door.draw(g);
		}
		
		dirtTile1.draw(g);
		dirtTile2.draw(g);
		
		// Draw Health
		g.setColor(Color.black);
		g.setFont(new Font("Arial", Font.PLAIN, 20));
		g.drawString("HP: " + player.getHealth(), size, size * 6);
	}

	@Override
	public void keyPressed(int k) {
		if (k == KeyEvent.VK_UP || k == KeyEvent.VK_W || k == KeyEvent.VK_SPACE) {
			player.setJump(true);
			pressed = true;
		}
	}

	@Override
	public void keyReleased(int k) {
		if (k == KeyEvent.VK_UP || k == KeyEvent.VK_W || k == KeyEvent.VK_SPACE) {
			player.setJump(false);
			player.setFall(true);
		}
		if (k == KeyEvent.VK_P) {
			isPaused = !isPaused;
			if (!isPaused) {
				audio.playLoop();
			}
			else {
				audio.stopAudio();
			}
		}
	}

}
