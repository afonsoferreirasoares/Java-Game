package objects;

import pt.iscte.poo.utils.Point2D;

public class Trap extends GameObject implements Explodable{
	
	public int damage = 20;

	public Trap(Point2D position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Trap";
    }
}
