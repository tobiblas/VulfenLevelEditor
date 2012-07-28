package com.editor;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ClickableObject {

	private Image image;

	private int x;

	private int y;

	private int width;

	private int height;
	
	public static final int COW = 0;
	public static final int PIG = 1;
	public static final int SAVE = 2;
	public static final int OPEN = 3;
	public static final int BRICK_SOFT = 4;
	public static final int BRICK_MEDIUM = 5;
	public static final int BRICK_HARD = 6;

	public ClickableObject(int type, int x, int y, JPanel panel) {

		this.x = x;
		this.y = y;
		String file = null;
		
		switch (type) {
		case COW:
			file = "cow.png";
			break;
		case PIG:
			file = "pig.png";
			break;
		case SAVE:
			file = "save.png";
			break;
		case OPEN:
			file = "open.png";
			break;
		case BRICK_SOFT:
			file = "brick_soft.png";
			break;
		case BRICK_MEDIUM:
			file = "brick_medium.png";
			break;
		case BRICK_HARD:
			file = "brick_hard.png";
			break;
		}
		
		try {
			InputStream is = this.getClass().getResourceAsStream(file);
			image = ImageIO.read(is);
			width = image.getWidth(panel);
			height = image.getHeight(panel);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @return the image
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

}
