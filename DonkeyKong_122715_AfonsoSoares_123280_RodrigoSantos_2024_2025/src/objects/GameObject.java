	package objects;
	
	import pt.iscte.poo.gui.ImageTile;
	import pt.iscte.poo.utils.Point2D;
	
	public abstract class GameObject implements ImageTile {
		
		protected Point2D position;
		
		public GameObject(Point2D position) {
			this.position = position;
		}
		
		@Override
	    public Point2D getPosition() {
	        return position;
	    }
		
		@Override
	    public abstract String getName();
		
		@Override
	    public int getLayer() {
	        return 1;
	    }
		
		public void setPosition(Point2D position) {
	        this.position = position;
	    }
		
	}
