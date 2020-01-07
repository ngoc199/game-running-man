package entities.bonuses;

import java.awt.Graphics2D;

import entities.Entity;
import entities.Player;

public abstract class Bonus extends Entity {
	
	public Bonus(float x, float y) {
		super(x, y);
	}
	
	public abstract boolean checkMeetPlayer(Player player);

	@Override
	public void update() {
		
	}

	@Override
	public void draw(Graphics2D g) {
		
	}

}
