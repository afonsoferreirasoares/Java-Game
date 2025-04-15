package pt.iscte.poo.game;
	
import objects.*;
import objects.Character;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

import pt.iscte.poo.gui.ImageTile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import pt.iscte.poo.utils.Direction;
	
public class Room {
		
	private Manel manel;
	private List<ImageTile> imageTiles = new ArrayList<>();
	private List<Donkey> donkeys = new ArrayList<>();
	ImageGUI gui = ImageGUI.getInstance();
		
	public Room() {
		addFloorToAllPositions();
		loadObjectsFromFile("rooms/room0.txt");
		gui.addImages(imageTiles);
		gui.update();
	}
	
	private Point2D initialPosition;
	
	public void loadObjectsFromFile(String fileName) {
	    try {
	        File file = new File(fileName);
	        Scanner scanner = new Scanner(file);

	        if (scanner.hasNextLine()) {
	            scanner.nextLine();
	        }

	        int row = 0;
	        final int expectedWidth = 10;

	        while (scanner.hasNextLine()) {
	            String line = scanner.nextLine();

	            if (line.length() < expectedWidth) {
	                System.out.printf("Line " + row + " is nicomplete (size "+ line.length() +", expected " + expectedWidth + "). It will be filled with floor.\n");
	                line = String.format("%-" + expectedWidth + "s", line);
	            }

	            for (int col = 0; col < expectedWidth; col++) {
	                char symbol = line.charAt(col);
	                Point2D position = new Point2D(col, row);

	                if (symbol == 'W') {
	                    addWall(position);
	                } else if (symbol == 'H') {
	                    addManel(position);
	                    initialPosition = position;
	                } else if (symbol == 'S') {
	                    addStair(position);
	                } else if (symbol == 't') {
	                    addTrap(position);
	                } else if (symbol == 's') {
	                    addSword(position);
	                } else if (symbol == 'G') {
	                    addDonkey(position);
	                } else if (symbol == '0') {
	                    addDoor(position);
	                } else if (symbol == 'm') {
	                    addBeef(position);
	                } else if (symbol == 'P') {
	                    addPrincess(position);
	                } else if (symbol == 'b') {
	                    addBat(position);
	                } else if (symbol == 'B') {
	                    addBomb(position);
	                } else if (symbol == 'x') {
	                    addSusWall(position);
	                    addTrap(position);
	                } else if (symbol == 'h') {
	                    addHammer(position);
	                } else if (symbol == ' ') {
	                    addFloor(position);
	                } else {
	                    System.out.printf("Unknown character " + symbol + " in line " + row + ", collumn " + col + ". Replaced with floor.\n");
	                    addFloor(position);
	                }
	            }
	            row++;
	        }
	        scanner.close();
	    } catch (FileNotFoundException e) {
	        System.out.println("File not found: " + fileName);
	        Scanner input = new Scanner(System.in);
	        System.out.print("Enter the name of the file to upload: ");
	        String newFileName = input.nextLine();
	        loadObjectsFromFile(newFileName);
	    } catch (Exception e) {
	        System.out.println("Error processing file: " + e.getMessage());
	        gui.dispose();
	    }
	}

    public void addFloorToAllPositions() {
        for (int y = 0; y < 10; y++) {
            for(int x = 0; x < 10; x++) {
                addFloor(new Point2D(x, y));
            }
        }
        
    }
    
    public void addFloor(Point2D position) {
        Floor newFloor = new Floor(position);
        imageTiles.add(newFloor);
    }
    
    public void addWall(Point2D position) {
        Wall newWall = new Wall(position);
        imageTiles.add(newWall);
    }
    
    public void addManel(Point2D position) {
        manel = new Manel(position, 10, 100, 3);
        imageTiles.add(manel);
    }
    
    public void addStair(Point2D position) {
        Stair newStair = new Stair(position);
        imageTiles.add(newStair);
    }
    
    public void addTrap(Point2D position) {
        Trap newTrap = new Trap(position);
        imageTiles.add(newTrap);
    }
    
    public void addSword(Point2D position) {
        Sword newSword = new Sword(position);
        imageTiles.add(newSword);
    }
    
    public void addDonkey(Point2D position) {
        Donkey newDonkey = new Donkey(position);
        donkeys.add(newDonkey);
        imageTiles.add(newDonkey);
    }
    
    public void addDoor(Point2D position) {
        Door newDoor = new Door(position);
        imageTiles.add(newDoor);
    }
    
    public void addBeef(Point2D position) {
        Beef newBeef = new Beef(position, 12);
        imageTiles.add(newBeef);
    }
    
    public void addPrincess(Point2D position) {
        Princess newPrincess = new Princess(position);
        imageTiles.add(newPrincess);
    }
    
    public void addBat(Point2D position) {
        Bat newBat = new Bat(position);
        imageTiles.add(newBat);
    }
    
    public void addBomb(Point2D position) {
        Bomb newBomb = new Bomb(position);
        imageTiles.add(newBomb);
    }
    
    public void addSusWall(Point2D position) {
        SusWall newSus = new SusWall(position);
        imageTiles.add(newSus);
    }
    
    public void addHammer(Point2D position) {
        Hammer newHammer = new Hammer(position);
        imageTiles.add(newHammer);
    }
    
    public void moveManel(int keyCode) {
        Direction direction = Direction.directionFor(keyCode);
        Point2D newPosition = manel.getPosition().plus(direction.asVector());
        
        if (canMoveToPosition(newPosition, direction)) {
            manel.move(direction);
            checkForPickup();
            checkForNextLevel();
            checkBananas();
            checkForBomb();
        }
        
        if (isDonkeyAtPosition(newPosition)) {
            attackManel();
        }
    }
    
    private boolean canMoveToPosition(Point2D position, Direction direction) {
        if (!isWithinBounds(position)) {
            return false;
        }
        if (direction == Direction.UP || direction == Direction.DOWN) {
            return !isWallAtPosition(position) && isStairAtPosition(position) && !isDonkeyAtPosition(position);
            
        }
        return !isWallAtPosition(position) && !isDonkeyAtPosition(position) && !isManuelAtPosition(position);
    }
    
    private boolean canDonkeyMoveToPosition(Point2D position, Direction direction) {
        if (!isWithinBounds(position)) {
            return false;
        }
        if (direction == Direction.UP || direction == Direction.DOWN) {
            return !isWallAtPosition(position) && isStairAtPosition(position);
            
        }
        return !isWallAtPosition(position) && !isDonkeyAtPosition(position) && !isManuelAtPosition(position) && !isPrincessAtPosition(position);
    }
    
    private boolean isWithinBounds(Point2D position) {
        return position.getX() >= 0 && position.getX() < 10 && position.getY() >= 0 && position.getY() < 10;
    }
    
    private boolean isWallAtPosition(Point2D position) {
        for(ImageTile tile : imageTiles) {
             if (tile instanceof Wall && tile.getPosition().equals(position)) {
                    return true;
             }
        }
        return false;
    }
    
    private boolean isDonkeyAtPosition(Point2D position) {
        for(ImageTile tile : imageTiles) {
             if (tile instanceof Donkey && tile.getPosition().equals(position)) {
                    return true;
             }
        }
        return false;
    }
    
    private boolean isManuelAtPosition(Point2D position) {
        for(ImageTile tile : imageTiles) {
             if (tile instanceof Manel && tile.getPosition().equals(position)) {
                    return true;
             }
        }
        return false;
    }
    
    private boolean isPrincessAtPosition(Point2D position) {
        for(ImageTile tile : imageTiles) {
             if (tile instanceof Princess && tile.getPosition().equals(position)) {
                    return true;
             }
        }
        return false;
    }
    
    private boolean isBatAtPosition(Point2D position) {
        for(ImageTile tile : imageTiles) {
             if (tile instanceof Bat && tile.getPosition().equals(position)) {
                    return true;
             }
        }
        return false;
    }
    
    private boolean isStairAtPosition(Point2D position) {
        for (ImageTile tile : imageTiles) {
            if (tile instanceof Stair && (tile.getPosition().equals(position) || tile.getPosition().equals(position.plus(Direction.DOWN.asVector())))) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isStairBelow(Point2D position) {
        for (ImageTile tile : imageTiles) {
            if (tile instanceof Stair && tile.getPosition().equals(position.plus(Direction.DOWN.asVector()))) {
                return true;
            }
        }
        return false;
    }
    
    public void donkeyMovement() {
        for (Donkey donkey : donkeys) {
        	Point2D manelPosition = manel.getPosition();
            Point2D donkeyPosition = donkey.getPosition();          
            double chaseRate = 0.3 + (level * 0.1);
            Direction randomDirection;
            
            if (Math.random() < chaseRate) {
                randomDirection = calculateDirectionTowards(manelPosition, donkeyPosition);
            } else {
                randomDirection = Direction.random();
            }
            
            if(randomDirection == Direction.LEFT || randomDirection == Direction.RIGHT) {
                Point2D newPosition = donkey.getPosition().plus(randomDirection.asVector());

                if (canDonkeyMoveToPosition(newPosition, randomDirection)) {
                    donkey.move(randomDirection);
                }
            }
            double rate = 0.10 + (level * 0.10);
            if (Math.random() < rate) {
                throwBanana(donkey.getPosition());
            }
        }
    }
    
    private Direction calculateDirectionTowards(Point2D target, Point2D current) {
        int dx = target.getX() - current.getX();
        int dy = target.getY() - current.getY();

        if (Math.abs(dx) > Math.abs(dy)) {
            return dx > 0 ? Direction.RIGHT : Direction.LEFT;
        } else {
            return dy > 0 ? Direction.DOWN : Direction.UP;
        }
    }
    
    public void batMovement() {
		List<Bat> batsToRemove = new ArrayList<>();

	    for (ImageTile tile : new ArrayList<>(imageTiles)) {
	        if (tile instanceof Bat) {
	            Bat bat = (Bat) tile;
	            Point2D position = bat.getPosition();
	            if (isManuelAtPosition(position)) {
                	manel.takeDamage(20);
                	gui.setStatusMessage("Manel was attacked by a Bat! Life: " + manel.getHealth());
                	batsToRemove.add(bat);
            	}
	            if(isStairBelow(position)) {
            		bat.move(Direction.DOWN);
            	} else {
            		Direction randomDirection = Direction.random();
            		if(randomDirection == Direction.LEFT || randomDirection == Direction.RIGHT) {
            			Point2D newPosition = bat.getPosition().plus(randomDirection.asVector());
            			if (isWithinBounds(newPosition) && !isWallAtPosition(newPosition) && !isPrincessAtPosition(newPosition) && !isBatAtPosition(newPosition)) {
            				bat.move(randomDirection);
            			}
            		}
            	}
	        }
	    }
	    
	    for (Bat bat : batsToRemove) {
	        imageTiles.remove(bat);
	        gui.removeImage(bat);
	    }
	}
    
    int level = 1;
		
    private void checkForPickup() {
	    Point2D manelPosition = manel.getPosition();
	    GameObject itemToRemove = null;

	    for (ImageTile tile : imageTiles) {
	        if (tile instanceof Item && tile.getPosition().equals(manelPosition)) {
	            itemToRemove = (GameObject) tile;
	            Item item = (Item) tile;
	            item.interact(manel);
	            gui.setStatusMessage(item.getPickupMessage(manel));
	        }
	    }

	    if (itemToRemove != null) {
	        imageTiles.remove(itemToRemove);
	        gui.removeImage(itemToRemove);
	    }
	}
    
    private void checkForBomb() {
        Point2D manelPosition = manel.getPosition();
        GameObject itemToRemove = null;

        for (ImageTile tile : imageTiles) {
            if (tile instanceof Bomb && tile.getPosition().equals(manelPosition)) {
                Bomb bomb = (Bomb) tile;
                if (bomb.isActivated()) {
                    return;
                }
                manel.grabBomb();
                itemToRemove = bomb;
                gui.setStatusMessage(bomb.getPickupMessage(manel));
            }
        }

        if (itemToRemove != null) {
            imageTiles.remove(itemToRemove);
            gui.removeImage(itemToRemove);
        }
    }
	
	private void checkForNextLevel() {
		Point2D manelPosition = manel.getPosition();
		
		for (ImageTile tile : imageTiles) {
			if (tile instanceof Door && tile.getPosition().equals(manelPosition)) {
				nextLevel(level);
				level++;
				return;
			}
			if (tile instanceof Princess && tile.getPosition().equals(manelPosition)) {
				gui.showMessage("You Won", "You rescued the Princess! Congratulations!");
				gui.dispose();
				int score = 1000 - gui.getTicks();
				System.out.println("Score: " + score);
				saveScore(score);
				displayTopScores();
				System.exit(0);
			}
		}
	}
	
	private void saveScore(int score) {
		try {
			List<Integer> scores = new ArrayList<>();
			File file = new File("rooms/highscores.txt");
			if (file.exists()) {
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line;
				while ((line = br.readLine()) != null) {
					scores.add(Integer.parseInt(line.trim()));
				}
				br.close();
			}
			scores.add(score);
			
			Collections.sort(scores, Collections.reverseOrder());
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			for (int s : scores) {
				bw.write(s + "\n");
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void displayTopScores() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("rooms/highscores.txt"));
			String line;
			int count = 0;
			System.out.println("Top Scores:");
			while ((line = br.readLine()) != null && count < 5) {
				System.out.println(line);
				count++;
			}
			br.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void checkTrap() {
		Point2D manelPosition = manel.getPosition();
		SusWall itemToRemove = null;
		
		for (ImageTile tile : imageTiles) {
			if(tile instanceof SusWall && (tile.getPosition().equals(manelPosition) || tile.getPosition().equals(manelPosition.plus(Direction.DOWN.asVector())))) {
				itemToRemove = (SusWall) tile;
	            gui.setStatusMessage("Hidden trap!");
			}
			if(tile instanceof Trap && (tile.getPosition().equals(manelPosition) || tile.getPosition().equals(manelPosition.plus(Direction.DOWN.asVector())))) {
				manel.takeDamage(25);
				gui.setStatusMessage("Manel took damage! Health: " + manel.getHealth());
			}
		}
		if (itemToRemove != null) {
	        imageTiles.remove(itemToRemove);
	        gui.removeImage(itemToRemove);
	    }
	}
	
	public void checkBananas() {
		Point2D manelPosition = manel.getPosition();
		Banana bananaToRemove = null;
		
		for (ImageTile tile : imageTiles) {
			if (tile instanceof Banana && tile.getPosition().equals(manelPosition)) {
	            bananaToRemove = (Banana) tile;
	        }
		}
		
		if (bananaToRemove != null) {
	        imageTiles.remove(bananaToRemove);
	        gui.removeImage(bananaToRemove);
	        manel.takeDamage(10);
            gui.setStatusMessage("Manel got hit by a banana! Health: " + manel.getHealth());
	    }
	}
	
	public void checkHammers() {
	    Donkey donkeyToDamage = null;
	    Hammer hammerToRemove = null;

	    for (Donkey donkey : donkeys) {
	        Point2D donkeyPosition = donkey.getPosition();
	        
	        for (ImageTile tile : imageTiles) {
	            if (tile instanceof Hammer && tile.getPosition().equals(donkeyPosition)) {
	                hammerToRemove = (Hammer) tile;
	                donkeyToDamage = donkey;
	            }
	        }
	        
	        if (hammerToRemove != null) {
	            imageTiles.remove(hammerToRemove);
	            gui.removeImage(hammerToRemove);
	            gui.setStatusMessage("Donkey got hit by a hammer!");
	        }
	    }

	    if (donkeyToDamage != null) {
	        imageTiles.remove(donkeyToDamage);
	        donkeys.remove(donkeyToDamage);
	        gui.removeImage(donkeyToDamage);
	    }
	}

	
	public void gravity() {
	    if (!hasSupportBelow(manel.getPosition())) {
	        manel.move(Direction.DOWN);
	    }

	    List<Banana> bananasToRemove = new ArrayList<>();
	    for (ImageTile tile : imageTiles) {
	        if (tile instanceof Banana) {
	            Banana banana = (Banana) tile;
	            Point2D belowPosition = banana.getPosition().plus(Direction.DOWN.asVector());
	            if (isWithinBounds(belowPosition)) {
	                banana.setPosition(belowPosition);
	            } else {
	                bananasToRemove.add(banana);
	            }
	        }
	    }
	    imageTiles.removeAll(bananasToRemove);
	    gui.removeImages(bananasToRemove);

	    if (manel.hasHammer()) {
	        List<Hammer> hammerToRemove = new ArrayList<>();
	        for (ImageTile tile : imageTiles) {
	            if (tile instanceof Hammer) {
	                Hammer hammer = (Hammer) tile;
	                Point2D upPosition = hammer.getPosition().plus(Direction.UP.asVector());
	                if (isWithinBounds(upPosition)) {
	                    hammer.setPosition(upPosition);
	                } else {
	                    hammerToRemove.add(hammer);
	                }
	            }
	        }
	        imageTiles.removeAll(hammerToRemove);
	        gui.removeImages(hammerToRemove);
	    }
	}

	
	private boolean hasSupportBelow(Point2D position) {
	    Point2D belowPosition = position.plus(Direction.DOWN.asVector());
	    
	    for (ImageTile tile : imageTiles) {
	        if ((tile instanceof Wall || tile instanceof Stair || tile instanceof Trap) && tile.getPosition().equals(belowPosition)) {
	            return true;
	        }
	    }
	    
	    return false;
	}
	
	public void attackManel() {
		Point2D manelPosition = manel.getPosition();
	    List<Point2D> adjacentPositions = manelPosition.getNeighbourhoodPoints();
	    Donkey donkeyToDamage = null;

	    for (Donkey donkey : donkeys) {
	        if (adjacentPositions.contains(donkey.getPosition())) {
	            donkeyToDamage = donkey;
	            break;
	        }
	    }
	    
	    if (donkeyToDamage != null) {
	        donkeyToDamage.takeDamage(manel.getStrength());
	        gui.setStatusMessage("DonkeyKong was attacked! Life: " + donkeyToDamage.getHealth());
	        
	        if (donkeyToDamage.isDead()) {
	            imageTiles.remove(donkeyToDamage);
	            donkeys.remove(donkeyToDamage);
	            gui.removeImage(donkeyToDamage);
	            gui.setStatusMessage("DonkeyKong Killed!");
	        }
	    }
	}
	
	public void attackDonkey() {
		Point2D manelPosition = manel.getPosition();

	    for (Donkey donkey : donkeys) {
	        List<Point2D> adjacentPositions = donkey.getPosition().getNeighbourhoodPoints();

	        if (adjacentPositions.contains(manelPosition)) {
	            manel.takeDamage(donkey.getStrength());
	            gui.setStatusMessage("Manel was attacked! Life: " + manel.getHealth());
	        }
	    }		
	}
	
	public void restartGame() {
		level = 1;
	    imageTiles.clear();
	    donkeys.clear();
	    gui.clearImages();

	    addFloorToAllPositions();
	    loadObjectsFromFile("rooms/room0.txt");

	    gui.addImages(imageTiles);
	    gui.setStatusMessage("Manel died! Game restarted!");
	}
	
	public void restartLevel() {
		manel.setHealthAndStrength(manel.getStrength(), 100, manel.getHearts());
        manel.setPosition(initialPosition);
		gui.setStatusMessage("Manel lost a heart! Hearts remaining: " + manel.getHearts());
	}
	
	public void checkIfManelIsDead() {
		if (manel.isDead()) {
	        manel.loseHeart();
	        if (!manel.hasLives()) {
	            restartGame();
	        } else {
	            restartLevel();
	        }
	    }
	}
	
	public void nextLevel(int level) {
		int keepHealth = manel.getHealth();
		int keepStrength = manel.getStrength();
		int keepHearts = manel.getHearts();
		int hasBomb = manel.hasBomb();
		boolean hasHammer = manel.hasHammer();
		imageTiles.clear();
	    donkeys.clear();
	    gui.clearImages();
	    
	    addFloorToAllPositions();
	    loadObjectsFromFile("rooms/room" + level + ".txt");
	    
	    gui.addImages(imageTiles);
	    manel.setHealthAndStrength(keepStrength, keepHealth, keepHearts);
	    if (hasBomb >= 1) {
            manel.grabBombs(hasBomb); 
        } 
	    if (hasHammer) {
            manel.grabHammer(); 
        }
	    gui.setStatusMessage("Level " + (level+1) + "! Health: " + manel.getHealth() + ", Strength: " + manel.getStrength());
	}
	
	public void throwBanana(Point2D position) {
	    Banana banana = new Banana(position);
	    imageTiles.add(banana);
	    gui.addImage(banana);
	}
	
	public void updateBeefDecay() {
	    for (ImageTile tile : imageTiles) {
	        if (tile instanceof Beef) {
	            ((Beef) tile).decay();
	            if(((Beef) tile).isRotten()) {
	            	gui.removeImage((Beef) tile);
	                gui.addImage((Beef) tile);
	            }
	        }
	    }
	}
	
	public void PlantBomb(){
        if(manel.hasBomb() >= 1){
            Point2D manelPosition = manel.getPosition();
            Bomb newBomb = new Bomb(manelPosition);
            imageTiles.add(newBomb);
            gui.addImage(newBomb);
            gui.setStatusMessage("Bomb planted!");
            newBomb.activate();
            manel.dropBomb();
        } else if(manel.hasBomb() == 0){
            gui.setStatusMessage("Manel has no bombs!");
        }
    }
	
	public void updateBombs() {
		imageTiles = new CopyOnWriteArrayList<>(imageTiles);

	    for (ImageTile tile : imageTiles) {
	        if (tile instanceof Bomb) {
	            Bomb bomb = (Bomb) tile;

	            if (bomb.isActivated()) {
	                bomb.IncrementTicks();

	                boolean characterOnBomb = imageTiles.stream().filter(t -> t instanceof Character).anyMatch(character -> bomb.getTicks() >= 2 && character.getPosition().equals(bomb.getPosition()));

	                if (characterOnBomb || bomb.getTicks() >= 5) {
	                    explodeBomb(bomb);
	                    imageTiles.remove(bomb);
	                    gui.removeImage(bomb);
	                    gui.setStatusMessage("Bomb exploded!");
	                }
	            }
	        }
	    }
    }
	
	private void explodeBomb(Bomb bomb) {
        Point2D bombPosition = bomb.getPosition();
        List<Point2D> explosionRadius = bombPosition.getNeighbourhoodPoints();
        explosionRadius.add(bombPosition);
        List<GameObject> objectsToRemove = new ArrayList<>();
        List<Donkey> donkeysToRemove = new ArrayList<>();
    
        for (Point2D position : explosionRadius) {
            if (manel.getPosition().equals(position)) {
                manel.takeDamage(100);
                return;
            }

            for (Donkey donkey : donkeys) {
            	if (donkey.getPosition().equals(position)) {
            		donkeysToRemove.add(donkey);
            	}
            }

    	    for (ImageTile tile : imageTiles) {
    	    	if (tile instanceof Explodable && tile.getPosition().equals(position)) {
    	    		if((GameObject) tile != bomb) {
    	    			objectsToRemove.add((GameObject) tile);
    	    		}
    	        }
    	    }
        }
        for (Donkey donkey : donkeysToRemove) {
            donkeys.remove(donkey);
            imageTiles.remove(donkey);
            gui.removeImage(donkey);
        }

        for (GameObject object : objectsToRemove) {
            imageTiles.remove(object);
            gui.removeImage(object);
        }
    }
	
	public void throwHammer() {
	    if (manel.hasHammer()) {
	        Point2D manelPosition = manel.getPosition();
	        Hammer newHammer = new Hammer(manelPosition);
	        imageTiles.add(newHammer);
	        gui.addImage(newHammer);
	        gui.setStatusMessage("Manel threw the Hammer!");
	    } else if (!manel.hasHammer()) {
	        gui.setStatusMessage("Manel has no Hammer!");
	    }
	}
	
	public void alwaysCheck() {
		gravity();
		checkTrap();
		checkBananas();
		checkHammers();
		updateBeefDecay();
		donkeyMovement();
		batMovement();
		attackDonkey();
		updateBombs();
		checkIfManelIsDead();
	}
}
