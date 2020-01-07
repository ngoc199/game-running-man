package entities.monster;

import entities.Entity;
import entities.Player;

public abstract class Monster extends Entity {

	public Monster(float x, float y) {
		super(x, y);
	}
	
	public boolean checkDealDamage(Player player) {
		if (this.intersects(player)) {
			player.loseHealth();
			return true;
		}
		return false;
	}
	
}
