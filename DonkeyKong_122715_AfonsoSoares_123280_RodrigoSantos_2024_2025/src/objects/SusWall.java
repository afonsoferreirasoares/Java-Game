package objects;

import pt.iscte.poo.utils.Point2D;

public class SusWall extends GameObject implements Explodable {
	
	public SusWall(Point2D position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Wall";
    }
    
    @Override
	public int getLayer() {
		return 2;
	}
}
