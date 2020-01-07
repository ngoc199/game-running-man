package score;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import Main.GamePanel;
import entities.Entity;

public class ScoreManager {
	private static int score;
	private static int highScore;
	private static BufferedReader fileIn;
	private static BufferedWriter fileOut;
	private static int[] scoreBoard = new int[4]; 
	
	public static void init() {
		score = 0;
		readHighScoreFromFile();
	}
	
	public static void update() {
		score += 1;
	}
	
	public static void addScore(int inc) {
		score += inc;
	}
	
	public static void draw(Graphics2D g) {
		FontMetrics metrics = g.getFontMetrics();
		String str = "SCORE: " + Integer.toString(score);
		g.setColor(Color.black);
		g.setFont(new Font("Arial", Font.PLAIN, 20));
		g.drawString(str, Entity.WIDTH * 10 - metrics.stringWidth(str), Entity.HEIGHT * 6);
		
		str = "HIGHEST SCORE: " + Integer.toString(highScore);
		g.drawString(str, (Entity.WIDTH * 10 - metrics.stringWidth(str)) / 2, Entity.HEIGHT * 8);
	}
	
	public static void setHighScore() {
		int index = indexWhereHigherThanHighScore();
		if (index != -1) {
			for (int i = scoreBoard.length - 1; i > index; i--) {
				scoreBoard[i] = scoreBoard[i - 1];
			}
			scoreBoard[index] = score;
		}
	}
	
	public static int getHighScore() {
		return highScore;
	}
	
	public static int indexWhereHigherThanHighScore() {
		for (int i = 0; i < scoreBoard.length; i++) {
			if (score > scoreBoard[i]) return i; 
		}
		return -1;
	}
	
	public static void printHighScoreToFile() {
		try {
			fileOut = new BufferedWriter(new FileWriter("./res/score/highScore.txt"));
			for (int i = 0; i < scoreBoard.length; i++) {
				fileOut.write(Integer.toString(scoreBoard[i]));
				fileOut.newLine();
			}
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void readHighScoreFromFile() {
		try {
			fileIn = new BufferedReader(new FileReader("./res/score/highScore.txt"));
			
			for (int i = 0; i < scoreBoard.length; i++) {
				scoreBoard[i] = Integer.parseInt(fileIn.readLine());
			}
			
			highScore = scoreBoard[0];
			
			fileIn.close(); 
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void drawHighScore(Graphics2D g) {
		for (int i = 0; i < scoreBoard.length; i++) {
			drawCenteredString(g, i + 1 + "   " + scoreBoard[i], Color.black, new Font("Arial", Font.ITALIC, 20), 150 + 50 * i);
		}
	}
	
	private static void drawCenteredString(Graphics2D g, String str, Color color, Font font, int y) {
		FontMetrics metrics = g.getFontMetrics(font);
		g.setColor(color);
		g.setFont(font);
		g.drawString(str, (GamePanel.WIDTH - metrics.stringWidth(str)) / 2, y);
	}
}
