package objects;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Manel extends GameObject implements Character {
	
	private int strength;
	private int health;
	private int hearts;
	private int hasBomb = 0;
	private boolean hasHammer = false;
	
	public Manel(Point2D position, int strength, int health, int hearts){
		super(position);
		this.strength = strength;
		this.health = health;
		this.hearts = hearts;
	}
	
	@Override
	public String getName() {
		return "Manel Melhor";
	}

	@Override
	public int getLayer() {
		return 2;
	}

	public void move(Direction direction) {
	    position = position.plus(direction.asVector());
	}
	
	public int getStrength() {
		return strength;
	}
	
	public int getHealth() {
		if(health > 0) {
        	return health;
        } else {
        	return 0;
        }
    }
	
	public int getHearts() {
		return this.hearts;
	}
	
	public void grabSword() {
		this.strength += 25;
	}
	
	public void takeDamage(int damage) {
		this.health -= damage;
	}
	
	public void eatBeef() {
		this.health += 25;
	}
	
	public boolean isDead() {
    	return this.health <= 0;
	}
	
	public void setHealthAndStrength(int newStrength, int newHealth, int newHearts) {
    	this.health = newHealth;
    	this.strength = newStrength;
    	this.hearts = newHearts;
    }
	
	public void loseHeart() {
	    if (hearts > 0) {
	        hearts--;
	    }
	}

	public boolean hasLives() {
	    return hearts > 0;
	}
	
	public int hasBomb() {
		return hasBomb;	
	}
	
	public void grabBomb() {
        this.hasBomb ++;
	}
	
	public void grabBombs(int b) {
        this.hasBomb += b;
	}
	
	public void dropBomb() {
		this.hasBomb --;
	}
	
	public boolean hasHammer() {
		return hasHammer;
	}
	
	public void grabHammer() {
		this.hasHammer = true;
	}
}
