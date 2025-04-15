package objects;

import pt.iscte.poo.utils.Point2D;

public class Beef extends GameObject implements Item, Explodable {
	
	private int turnsToRot;
    private boolean isRotten;
    private String imageName = "GoodMeat";
	
	public Beef(Point2D position, int turnsToRot) {
        super(position);
        this.turnsToRot = turnsToRot;
        this.isRotten = false;
    }
	
    @Override
    public String getName() {
        return imageName;
    }
    
    @Override
    public void interact(Manel manel) {
    	if (isRotten) {
            manel.takeDamage(50);
        } else {
            manel.setHealthAndStrength(manel.getStrength(), 100, manel.getHearts());
        }
    }
    
    public String getPickupMessage(Manel manel) {
    	if (isRotten) {
            return "Rotten beef! You lost health! Hearts: " + manel.getHealth();
        } else {
            return "Beef eaten! Health: " + manel.getHealth();
        }
    }
    
    public void decay() {
        if (turnsToRot > 0) {
            turnsToRot--;
        } else if (!isRotten) {
            isRotten = true;
            imageName = "BadMeat";
        }
    }

    public boolean isRotten() {
        return isRotten;
    }
}
