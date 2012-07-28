package com.editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

	public static final int WIDTH = 480;
	public static final int GAME_AREA_HEIGHT = 640;
	public static final int SCREEN_HEIGHT = 800;
	private static final int BRICK_W = 50;
	private static final int BRICK_H = 20;
	
	private Image backgroundImage;

	GameObject selectedGameObject = null;

	List<GameObject> gameObjects = new ArrayList<GameObject>();

	int selectedIndex = 0;

	String fishes = "G";
	int star1 = 1000;
	int star2 = 2000;
	int star3 = 3000;
	int lives = 2;
	
	
	private ClickableObject cow;
	private ClickableObject pig;
	private ClickableObject brickSoft;
	private ClickableObject brickMedium;
	private ClickableObject brickHard;
	private ClickableObject saveButton;
	private ClickableObject openButton;

	int lastX = 0;
	int lastY = 0;

	public GamePanel() {

//		setBorder(BorderFactory.createLineBorder(Color.black));

		setFocusable(true);

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (selectedIndex == 0 || e.getY() >= GAME_AREA_HEIGHT) {
					selectObject(e.getX(), e.getY());
					if (selectedIndex == 0) {
						deleteObject(e.getX(), e.getY());
					}
				} else {
					boolean deleted = deleteObject(e.getX(), e.getY());
					if (!deleted && e.getY() < GAME_AREA_HEIGHT) {
						addObject(e.getX(), e.getY());
					} else if (!deleted && e.getY() >= GAME_AREA_HEIGHT) {
						// deselect
						selectedIndex = 0;
					}
				}
				repaint();
			}

		});

		addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				arg0.consume();
				int keyCode = arg0.getKeyCode();

				int left = 37;
				int right = 39;
				int down = 40;
				int up = 38;

				if (keyCode == left) {
					if (selectedGameObject != null) {
						selectedGameObject.setX(selectedGameObject.getX() - 1);
						lastX--;
					}
				}
				if (keyCode == right) {
					if (selectedGameObject != null) {
						selectedGameObject.setX(selectedGameObject.getX() + 1);
						lastX++;
					}
				}
				if (keyCode == up) {
					if (selectedGameObject != null) {
						selectedGameObject.setY(selectedGameObject.getY() - 1);
						lastY--;
					}
				}
				if (keyCode == down) {
					if (selectedGameObject != null) {
						selectedGameObject.setY(selectedGameObject.getY() + 1);
						lastY++;
					}
				}

				repaint();

			}

			@Override
			public void keyPressed(KeyEvent arg0) {
			}
		});

		try {
			InputStream is = this.getClass().getResourceAsStream(
					"background4.jpg");
			backgroundImage = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}

		cow = new ClickableObject(ClickableObject.COW, 20, 680, this);
		pig = new ClickableObject(ClickableObject.PIG, 120, 680, this);
		brickSoft = new ClickableObject(ClickableObject.BRICK_SOFT, 200, 650, this);
		brickSoft.setHeight(BRICK_H);
		brickSoft.setWidth(BRICK_W);
		brickMedium = new ClickableObject(ClickableObject.BRICK_MEDIUM, 200, 690, this);
		brickMedium.setHeight(BRICK_H);
		brickMedium.setWidth(BRICK_W);
		brickHard = new ClickableObject(ClickableObject.BRICK_HARD, 200, 730, this);
		brickHard.setHeight(BRICK_H);
		brickHard.setWidth(BRICK_W);
		
		saveButton = new ClickableObject(ClickableObject.SAVE, 400, 660, this);
		saveButton.setHeight(50);
		saveButton.setWidth(50);
		openButton = new ClickableObject(ClickableObject.OPEN, 340, 660, this);
		openButton.setHeight(50);
		openButton.setWidth(50);

	}

	private void addObject(int x, int y) {

		int objectType = -1;
		GameObject gameObject = null;

		if (selectedIndex == 1) {
			objectType = ClickableObject.COW;
//			x = x - cow.getWidth() / 2;
//			y = y - cow.getHeight() / 2;
			gameObject = new GameObject(objectType, this, x, y);
		} else if (selectedIndex == 2) {
			objectType = ClickableObject.PIG;
//			x = x - pig.getWidth() / 2;
//			y = y - pig.getHeight() / 2;
			gameObject = new GameObject(objectType, this, x, y);
		} else if (selectedIndex == 3) {
			objectType = ClickableObject.BRICK_SOFT;
//			x = x - brickSoft.getWidth() / 2;
//			y = y - brickSoft.getHeight() / 2;
			gameObject = new GameObject(objectType, this, x, y, BRICK_W, BRICK_H);
		} else if (selectedIndex == 4) {
			objectType = ClickableObject.BRICK_MEDIUM;
//			x = x - brickMedium.getWidth() / 2;
//			y = y - brickMedium.getHeight() / 2;
			gameObject = new GameObject(objectType, this, x, y, BRICK_W, BRICK_H);
		} else if (selectedIndex == 5) {
			objectType = ClickableObject.BRICK_HARD;
//			x = x - brickMedium.getWidth() / 2;
//			y = y - brickMedium.getHeight() / 2;
			gameObject = new GameObject(objectType, this, x, y, BRICK_W, BRICK_H);
		}

		lastX = x;
		lastY = y;

		

		selectedGameObject = gameObject;

		gameObjects.add(gameObject);
	}

	private boolean deleteObject(int x, int y) {
		boolean deleted = false;
		GameObject objectToDelete = null;
		for (GameObject go : this.gameObjects) {
			if (x > go.getX() - go.getWidth() / 2
					&& x < go.getX() + go.getWidth() / 2
					&& y > go.getY() - go.getHeight() / 2
					&& y < go.getY() + go.getHeight() / 2) {
				objectToDelete = go;
			}
		}
		if (objectToDelete != null) {
			gameObjects.remove(objectToDelete);
			if (objectToDelete.getX() == lastX && objectToDelete.getY() == lastY) {
				lastY = 0;
				lastX = 0;
			}
			deleted = true;
		}
		return deleted;
	}

	private void selectObject(int x, int y) {

		if (x > cow.getX() && x < cow.getX() + cow.getWidth() && y > cow.getY()
				&& y < cow.getY() + cow.getHeight()) {
			selectedIndex = 1;
		} else if (x > pig.getX() && x < pig.getX() + pig.getWidth()
				&& y > pig.getY() && y < pig.getY() + pig.getHeight()) {
			selectedIndex = 2;
		} else if (x > brickSoft.getX() && x < brickSoft.getX() + brickSoft.getWidth()
				&& y > brickSoft.getY() && y < brickSoft.getY() + brickSoft.getHeight()) {
			selectedIndex = 3;
		} else if (x > brickMedium.getX() && x < brickMedium.getX() + brickMedium.getWidth()
				&& y > brickMedium.getY() && y < brickMedium.getY() + brickMedium.getHeight()) {
			selectedIndex = 4;
		} else if (x > brickHard.getX() && x < brickHard.getX() + brickHard.getWidth()
				&& y > brickHard.getY() && y < brickHard.getY() + brickHard.getHeight()) {
			selectedIndex = 5;
		} else if (x > saveButton.getX()
				&& x < saveButton.getX() + saveButton.getWidth()
				&& y > saveButton.getY()
				&& y < saveButton.getY() + saveButton.getHeight()) {
			// TODO: spara
			final JFileChooser fc = new JFileChooser();

			int returnVal = fc.showSaveDialog(this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				save(file);
			}
			selectedIndex = 0;
		} else if (x > openButton.getX()
				&& x < openButton.getX() + openButton.getWidth()
				&& y > openButton.getY()
				&& y < openButton.getY() + openButton.getHeight()) {

			final JFileChooser fc = new JFileChooser();

			int returnVal = fc.showOpenDialog(this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				open(file);
			}
			selectedIndex = 0;
		} else {
			selectedIndex = 0;
		}

	}

	private void open(File file) {

		selectedIndex = 0;
		lastX = 0;
		lastY = 0;
		gameObjects.clear();
		
		try {
			FileReader fstream = new FileReader(file);
			BufferedReader reader = new BufferedReader(fstream);
			
			String line = null;
			
			do {
				line = reader.readLine();
				
				int x = 0;
				int y = 0;
				int width = 0;
				int height = 0;
				
				if (line.contains("COW:")) {
					line = line.replace("COW:", "");
					String[] numbers = line.split(",");
					x = Integer.parseInt(numbers[0]);
					y = Integer.parseInt(numbers[1]);
					width = Integer.parseInt(numbers[2]);
					height = Integer.parseInt(numbers[3]);
					GameObject go = new GameObject(ClickableObject.COW, this, x, y, width, height);
					gameObjects.add(go);
				} else if (line.contains("PIG:")) {
					line = line.replace("PIG:", "");
					String[] numbers = line.split(",");
					x = Integer.parseInt(numbers[0]);
					y = Integer.parseInt(numbers[1]);
					width = Integer.parseInt(numbers[2]);
					height = Integer.parseInt(numbers[3]);
					GameObject go = new GameObject(ClickableObject.PIG, this, x, y, width, height);
					gameObjects.add(go);
				} else if (line.contains("BRICK1:")) {
					line = line.replace("BRICK1:", "");
					String[] numbers = line.split(",");
					x = Integer.parseInt(numbers[0]);
					y = Integer.parseInt(numbers[1]);
					width = Integer.parseInt(numbers[2]);
					height = Integer.parseInt(numbers[3]);
					GameObject go = new GameObject(ClickableObject.BRICK_SOFT, this, x, y, width, height);
					gameObjects.add(go);
				} else if (line.contains("BRICK2:")) {
					line = line.replace("BRICK2:", "");
					String[] numbers = line.split(",");
					x = Integer.parseInt(numbers[0]);
					y = Integer.parseInt(numbers[1]);
					width = Integer.parseInt(numbers[2]);
					height = Integer.parseInt(numbers[3]);
					GameObject go = new GameObject(ClickableObject.BRICK_MEDIUM, this, x, y, width, height);
					gameObjects.add(go);
				} else if (line.contains("BRICK3:")) {
					line = line.replace("BRICK3:", "");
					String[] numbers = line.split(",");
					x = Integer.parseInt(numbers[0]);
					y = Integer.parseInt(numbers[1]);
					width = Integer.parseInt(numbers[2]);
					height = Integer.parseInt(numbers[3]);
					GameObject go = new GameObject(ClickableObject.BRICK_HARD, this, x, y, width, height);
					gameObjects.add(go);
				} else if (line.contains("FISHES:")) {
					line = line.replace("FISHES:", "");
					fishes = line;
				} else if (line.contains("LIVES:")) {
					line = line.replace("LIVES:", "");
					lives = Integer.parseInt(line);
				}  else if (line.contains("STARS:")) {
					line = line.replace("STARS:", "");
					String[] numbers = line.split(",");
					star1 = Integer.parseInt(numbers[0]);
					star2 = Integer.parseInt(numbers[1]);
					star3 = Integer.parseInt(numbers[2]);
				} 
				
			} while (line != null);
			
			
			
			reader.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		
	}

	private void save(File file) {

		try {
			// Create file
			FileWriter fstream = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fstream);
			
			for (GameObject go : gameObjects) {
				String line = "";
				if (go.getType() == ClickableObject.COW) {
					line += "COW:";
				} else if (go.getType() == ClickableObject.PIG) {
					line += "PIG:";
				} else if (go.getType() == ClickableObject.BRICK_SOFT) {
					line += "BRICK1:";
				}  else if (go.getType() == ClickableObject.BRICK_MEDIUM) {
					line += "BRICK2:";
				}  else if (go.getType() == ClickableObject.BRICK_HARD) {
					line += "BRICK3:";
				}  
				line += go.getX() + ",";
				line += go.getY() + ",";
				line += go.getWidth() + ",";
				line += go.getHeight() + "\n";
			
				out.write(line);
			}
			
			out.write("FISHES:" + fishes + "\n");
			out.write("STARS:" + star1 + "," + star2 + "," + star3 + "\n");
			out.write("LIVES:" + lives + "\n");
			
			out.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

	}

	public Dimension getPreferredSize() {
		return new Dimension(250, 200);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.fillRect(0, 0, WIDTH, SCREEN_HEIGHT);
		g.drawString("This is my custom Panel!", 10, 20);

		g.drawImage(backgroundImage, 0, 0, 480, 640, this);

		g.drawImage(cow.getImage(), cow.getX(), cow.getY(), cow.getWidth(),
				cow.getHeight(), this);
		g.drawImage(pig.getImage(), pig.getX(), pig.getY(), pig.getWidth(),
				pig.getHeight(), this);
		g.drawImage(saveButton.getImage(), saveButton.getX(),
				saveButton.getY(), saveButton.getWidth(),
				saveButton.getHeight(), this);
		g.drawImage(openButton.getImage(), openButton.getX(),
				openButton.getY(), openButton.getWidth(),
				openButton.getHeight(), this);
		
		g.drawImage(brickSoft.getImage(), brickSoft.getX(),
				brickSoft.getY(), brickSoft.getWidth(),
				brickSoft.getHeight(), this);
		g.drawImage(brickMedium.getImage(), brickMedium.getX(),
				brickMedium.getY(), brickMedium.getWidth(),
				brickMedium.getHeight(), this);
		g.drawImage(brickHard.getImage(), brickHard.getX(),
				brickHard.getY(), brickHard.getWidth(),
				brickHard.getHeight(), this);

		if (selectedIndex > 0) {
			Color color = g.getColor();
			g.setColor(Color.RED);
			if (selectedIndex == 1) {
				g.drawRect(cow.getX(), cow.getY(), cow.getWidth(),
						cow.getHeight());
			} else if (selectedIndex == 2) {
				g.drawRect(pig.getX(), pig.getY(), pig.getWidth(),
						pig.getHeight());
			} else if (selectedIndex == 3) {
				g.drawRect(brickSoft.getX(), brickSoft.getY(), brickSoft.getWidth(),
						brickSoft.getHeight());
			} else if (selectedIndex == 4) {
				g.drawRect(brickMedium.getX(), brickMedium.getY(), brickMedium.getWidth(),
						brickMedium.getHeight());
			} else if (selectedIndex == 5) {
				g.drawRect(brickHard.getX(), brickHard.getY(), brickHard.getWidth(),
						brickHard.getHeight());
			}
			g.setColor(color);
		}

		drawGrid(g);

		for (GameObject gameObject : gameObjects) {
			g.drawImage(gameObject.getObjectImage(), gameObject.getX() - gameObject.getWidth() / 2,
					gameObject.getY() - gameObject.getHeight() / 2, gameObject.getWidth(),
					gameObject.getHeight(), this);
		}
	}

	private void drawGrid(Graphics g) {

		int colWidth = 20;
		int rowHeight = 20;
		int cols = WIDTH / colWidth;
		int rows = GAME_AREA_HEIGHT / rowHeight;

		Color color = g.getColor();
		g.setColor(new Color(100, 100, 100));

		for (int i = 0; i < cols; i++) {
			g.drawLine(i * colWidth, 0, i * colWidth, GAME_AREA_HEIGHT);
		}
		for (int j = 0; j < rows; j++) {
			g.drawLine(0, j * rowHeight, WIDTH, j * rowHeight);
		}

		g.setColor(Color.WHITE);
		g.drawString("[" + lastX + "," + lastY + "]", WIDTH - 70,
				SCREEN_HEIGHT - 50);
		g.setColor(color);
	}
}
