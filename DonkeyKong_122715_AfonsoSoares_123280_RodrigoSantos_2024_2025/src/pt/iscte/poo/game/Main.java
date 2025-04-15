package pt.iscte.poo.game;

import java.io.File;

import pt.iscte.poo.gui.ImageGUI;

public class Main {

	private static final String IMAGE_DIR = "images";

	public static void main(String[] args) {
		File folder = new File(IMAGE_DIR);
        System.out.println("Pasta: " + folder.getAbsolutePath());
        System.out.println("Existe? " + folder.exists());
        System.out.println("É diretório? " + folder.isDirectory());

		ImageGUI gui = ImageGUI.getInstance();
		GameEngine engine = new GameEngine();
		gui.setStatusMessage("Good luck!");
		gui.registerObserver(engine);
		gui.go();
	}
	
}
