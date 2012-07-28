package com.editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class LevelEditor {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	private static void createAndShowGUI() {
		System.out.println("Created GUI on EDT? "
				+ SwingUtilities.isEventDispatchThread());
		JFrame f = new JFrame("Vulfen Level Editor 0.1");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(new GamePanel());
		f.setSize(480, 800);
		f.setVisible(true);
	}

}

