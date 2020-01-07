package gameState;

import java.awt.Graphics2D;

public class GameStateManager {
	
	private GameState[] gameStates;
	private int currentState;
	private int lastState;
	
	// State Index
	private static final int NUM_STATE = 7;
	public static final int MENU = 0;
	public static final int SELECT_CHARACTERS = 1;
	public static final int PLAY = 2;
	public static final int INSTRUCTION = 3;
	public static final int HIGH_SCORE = 4;
	public static final int GAME_OVER = 5;
	public static final int WIN_GAME = 6;
	
	// Constructor
	public GameStateManager() {
		// Initialize Array with size = NUM_STATE
		gameStates = new GameState[NUM_STATE];
		
		currentState = MENU;
		loadState(currentState);
	}
	
	private void loadState(int state) {
		switch(state) {
			case MENU:
				gameStates[state] = new Menu(this);
				break;
			case SELECT_CHARACTERS:
				gameStates[state] = new SelectCharacter(this);
				break;
			case PLAY:
				gameStates[state] = new Play(this);
				break;
			case INSTRUCTION:
				gameStates[state] = new Instruction(this);
				break;
			case HIGH_SCORE:
				gameStates[state] = new HighScore(this);
				break;
			case GAME_OVER:
				gameStates[state] = new GameOver(this);
				break;
			case WIN_GAME:
				gameStates[state] = new WinGame(this);
				break;
		}
		
	}
	
	private void unloadState(int state) {
		gameStates[state] = null;
	}
	
	public void setState(int state) {
		setLastState(currentState);
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}
	
	private void setLastState(int state) {
		lastState = state;
	}
	
	public int getLastState() {
		return lastState;
	}
	
	// Gaming
	public void update() {
		// Check if gameState at currentState is null
		try {
			gameStates[currentState].update();
		} catch(Exception e) {}
	}
	
	public void draw(Graphics2D g) {
		try {
			gameStates[currentState].draw(g);
		} catch (Exception e) {}
	}
	
	public void keyPressed(int k) {
		gameStates[currentState].keyPressed(k);
	}
	
	public void keyReleased(int k) {
		gameStates[currentState].keyReleased(k);
	}
	
}
