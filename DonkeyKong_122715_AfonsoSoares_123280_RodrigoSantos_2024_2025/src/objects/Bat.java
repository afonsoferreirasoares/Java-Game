package objects;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Bat extends GameObject  implements Explodable {
	
	public Bat(Point2D position) {
        super(position);
    }
	
	@Override
	public int getLayer() {
        return 3;
    }

    @Override
    public String getName() {
        return "Bat";
    }
	
    public void move(Direction direction) {
        position = position.plus(direction.asVector());
    }
    
	

}
