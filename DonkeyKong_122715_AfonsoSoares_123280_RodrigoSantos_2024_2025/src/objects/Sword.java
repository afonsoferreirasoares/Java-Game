package objects;

import pt.iscte.poo.utils.Point2D;

public class Sword extends GameObject implements Item, Explodable {
	

	public Sword(Point2D position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Sword";
    }
    
    @Override
    public void interact(Manel manel) {
    	manel.grabSword();
    }
    
    public String getPickupMessage(Manel manel) {
        return "Sword picked up! Strength: " + manel.getStrength();
    }
	
}
