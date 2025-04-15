package objects;

import pt.iscte.poo.utils.Point2D;

public class Stair extends GameObject {
	

	public Stair(Point2D position) {
		super(position);
	}

	@Override
	public String getName() {
		return "Stairs";
	}
	
}

