package objects;

import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Direction;

public class Donkey extends GameObject implements Character {
	
	private int strength = 30;
	private int health = 100;

	public Donkey(Point2D position) {
        super(position);
    }

    @Override
    public String getName() {
        return "DonkeyKong";
    }
    
    public void move(Direction direction) {
        position = position.plus(direction.asVector());
    }
    
    public int getHealth() {
        if(health > 0) {
        	return health;
        } else {
        	return 0;
        }
    }
    
    public int getStrength() {
		return strength;
	}
    
    public void takeDamage(int damage) {
		this.health -= damage;
	}
    
    public boolean isDead() {
    	return this.health <= 0;
    }
}
