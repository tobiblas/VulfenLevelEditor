package com.editor;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GameObject {

	private int xPos = 0;
	private int yPos = 0;
	private int width = 0;
	private int height = 0;
	
	private Image objectImage;
	
	private int type;
	
	public GameObject(int objectType, JPanel panel, int x, int y) {
		xPos = x;
		yPos = y;
		this.type = objectType;
		init(panel);
	}
	
	public GameObject(int objectType, JPanel panel, int x, int y, int w, int h) {
		xPos = x;
		yPos = y;
		this.type = objectType;
		init(panel);
		width = w;
		height = h;
	}
	
	private void init(JPanel panel) {
		
		String image = null;
		
		switch(type) {
		case ClickableObject.COW:
			image = "cow.png";
			break;
		case ClickableObject.PIG:
			image = "pig.png";
			break;
		case ClickableObject.BRICK_SOFT:
			image = "brick_soft.png";
			break;
		case ClickableObject.BRICK_MEDIUM:
			image = "brick_medium.png";
			break;
		case ClickableObject.BRICK_HARD:
			image = "brick_hard.png";
			break;
		}
		
		try {
			InputStream is = this.getClass().getResourceAsStream(image);
			objectImage = ImageIO.read(is);
			width = objectImage.getWidth(panel);
			height = objectImage.getHeight(panel);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setX(int xPos) {
		this.xPos = xPos;
	}

	public int getX() {
		return xPos;
	}

	public void setY(int yPos) {
		this.yPos = yPos;
	}

	public int getY() {
		return yPos;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	/**
	 * @return the objectImage
	 */
	public Image getObjectImage() {
		return objectImage;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

}