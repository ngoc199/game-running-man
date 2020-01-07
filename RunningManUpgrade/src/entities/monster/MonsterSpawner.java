package entities.monster;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import entities.Entity;
import entities.Player;

public class MonsterSpawner {
	private ArrayList<Monster> monsterList;
	
	private float x;
	private float y;
	
	private int i;
	
	private Player player;
	
	private Random rand = new Random();
	
	public MonsterSpawner(float x, float y, Player player) {
		this.x = x;
		this.y = y;
		this.player = player;
		
		monsterList = new ArrayList<Monster>();
	}
	
	public void addMonster(int posY) {
		int monsterType = rand.nextInt(20);
		
		Monster monster;
		if (monsterType < 12) {
			monster = new Ball(x, y);
		}
		else {
			monster = new Bat(x, Entity.HEIGHT * posY);
		}
		monsterList.add(monster);
	}
	
	public boolean isBall() {
		return monsterList.get(monsterList.size() - 1).getClass().equals(new Ball(x, y).getClass());
	}
	
	private void removeMonster(Monster monster) {
		monsterList.remove(monster);
		i--;
	}
	
	public void update() {
		for (i = 0; i < monsterList.size(); i++) {
			Monster monster = monsterList.get(i);
			monster.update();
			if (monster.checkDealDamage(player)) removeMonster(monster);
			else if (monster.getX() < -monster.getWidth()) removeMonster(monster);
		}
	}
	
	public void draw(Graphics2D g) {
		for (Monster monster : monsterList) {
			monster.draw(g);
		}
	}
}
