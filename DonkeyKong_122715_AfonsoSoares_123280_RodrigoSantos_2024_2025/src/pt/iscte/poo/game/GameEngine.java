package pt.iscte.poo.game;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;

public class GameEngine implements Observer {
	
//	private static GameEngine instance;
	private Room currentRoom = new Room();
	private int lastTickProcessed = 0;
	
	public GameEngine() {
		ImageGUI.getInstance().update();
	}
	
//	public static GameEngine getInstance() {
//        if (instance == null) {
//            instance = new GameEngine();
//        }
//        return instance;
//    }
//
//    // Getter for the current room
//    public Room getRoom() {
//        return currentRoom;
//    }

	@Override
	public void update(Observed source) {
		
		if (ImageGUI.getInstance().wasKeyPressed()) {
			int k = ImageGUI.getInstance().keyPressed();
			System.out.println("Keypressed " + k);
			if (Direction.isDirection(k)) {
				System.out.println("Direction! ");
				currentRoom.moveManel(k);
			} else if (k == 66) {
				currentRoom.PlantBomb();
			} else if (k == 72) {
				currentRoom.throwHammer();
			}

		}
		int t = ImageGUI.getInstance().getTicks();
		while (lastTickProcessed < t) {
			processTick();
		}
		ImageGUI.getInstance().update();
	}


	private void processTick() {
		System.out.println("Tic Tac : " + lastTickProcessed);
		currentRoom.alwaysCheck();
		lastTickProcessed++;
	}
	
	public int getTick() {
		return lastTickProcessed;
	}

}
