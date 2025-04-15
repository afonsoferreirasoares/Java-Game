package objects;

import pt.iscte.poo.utils.Point2D;

public class Banana extends GameObject implements Explodable {

    public Banana(Point2D position) {
        super(position);
    }
    
    @Override
    public int getLayer() {
        return 2;
    }

    @Override
    public String getName() {
        return "Banana";
    }
}
