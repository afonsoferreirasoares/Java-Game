package objects;

import pt.iscte.poo.utils.Point2D;

public class Hammer extends GameObject implements Item, Explodable{
	
	public Hammer(Point2D position) {
		super(position);
	}
	
	@Override
   	public String getName() {
       return "Hammer";
	}
	
	public void interact(Manel manel) {
		manel.grabHammer();
   	}
	
	public String getPickupMessage(Manel manel) {
		return "Manel has picked up a Hammer!";
	}
}