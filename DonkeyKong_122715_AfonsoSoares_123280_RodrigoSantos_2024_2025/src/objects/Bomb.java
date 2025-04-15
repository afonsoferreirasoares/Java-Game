package objects;

import pt.iscte.poo.utils.Point2D;


public class Bomb extends GameObject implements Explodable{
	
    private int ticks;
    private boolean isActivated;
    
    
    public Bomb(Point2D position) {
        super(position);
        this.ticks = 0;
        this.isActivated = false;
    }
    
    @Override
    public String getName() {
        return "bomboca";
    }

    @Override
    public int getLayer() {
        return 2;
    }

    public void IncrementTicks(){
        ticks++;
    }

    public int getTicks(){
        return ticks;
    }

    public void activate() {
        this.isActivated = true;
    }
    
    public boolean isActivated(){
        return isActivated;
    }
    
    public void interact(Manel manel) {
        if (isActivated) {
            return;
        }
        manel.grabBomb();
    }
    
//    public void interact(Manel manel) {
//    	if(manel.getPosition().equals(this.getPosition())) {
//    		if (isActivated) {
//    			return;
//          }
//          manel.grabBomb();
//          ImageGUI.getInstance().setStatusMessage("Manel has picked up a bomb!");
//          ImageGUI.getInstance().removeImage(this);
//          GameEngine.getInstance().getRoom().getListObjects().remove(this);
//    	}
//    }
    
	public String getPickupMessage(Manel manel) {
		return "Manel has picked up a bomb!";
	}
}