package entities;

import java.awt.Graphics2D;
import java.util.Random;

import entities.bonuses.BonusSpawner;
import entities.monster.MonsterSpawner;

public class Spawner {

	private Random rand = new Random();
	
	private BonusSpawner bonusSpawner;
	private MonsterSpawner monsterSpawner;
	
	public Spawner(float x, float y, Player player) {
		bonusSpawner = new BonusSpawner(x, player);
		monsterSpawner = new MonsterSpawner(x, y, player);
	}
	
	public void addEntity() {
		int monsterPosY = rand.nextInt(3) + 1;
		monsterSpawner.addMonster(monsterPosY);
		
//		int bonusPosY;
//		if (monsterSpawner.isBall()) {
//			bonusPosY = rand.nextInt(2) + 1;
//		}
//		else {
//			do {
//				bonusPosY = rand.nextInt(3) + 1;
//			} while (bonusPosY == monsterPosY);
//		}
//		
//		bonusSpawner.addBonus(bonusPosY);
	}
	
	public BonusSpawner getBonusSpawner() {
		return bonusSpawner;
	}
	
	public MonsterSpawner getMonsterSpawner() {
		return monsterSpawner;
	}
	
	public void update() {
		monsterSpawner.update();
		bonusSpawner.update();
	}
	
	public void draw(Graphics2D g) {
		monsterSpawner.draw(g);
		bonusSpawner.draw(g);
	}
	
}
