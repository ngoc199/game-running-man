package entities.bonuses;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import audio.Audio;
import entities.Entity;
import entities.Player;

public class BonusSpawner {
	private ArrayList<Bonus> bonusList;
	
	private float x;
	
	private Random rand = new Random();
	
	private int i;
	private Audio audio;
	private final String audioPath = "/audio/Pickup_Coin.wav";
	
	private Player player;
	
	public BonusSpawner(float x, Player player) {
		this.x = x;
		this.player = player;
		
		bonusList = new ArrayList<Bonus>();
		audio = new Audio();
	}
	
	private void removeBonus(Bonus bonus) {
		bonusList.remove(bonus);
		i--;
	}
	
	public void addBonus(int posY) {
		int bonusType = rand.nextInt(20);
		
		Bonus bonus;
		
		if (bonusType < 18) {
			bonus = new Coin(x, Entity.HEIGHT * posY);
		}
		else {
			bonus = new Heart(x, Entity.HEIGHT * posY);
		}
		
		bonusList.add(bonus);
	}
	
	public void update() {
		for (i = 0; i < bonusList.size(); i++) {
			Bonus bonus = bonusList.get(i);
			bonus.update();
			if (bonus.checkMeetPlayer(player)) {
				removeBonus(bonus);
				audio.setAudio(audioPath);
				audio.playAudio();
			}
			else if (bonus.getX() < -bonus.getWidth()) removeBonus(bonus);
		}
	}
	
	public void draw(Graphics2D g) {
		for (Bonus bonus : bonusList) {
			bonus.draw(g);
		}
	}
}
