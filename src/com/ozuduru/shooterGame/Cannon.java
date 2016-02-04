/*********************************************************************************
*File: Cannon.java
*Author: Onur Ozuduru
*   e-mail: onur.ozuduru { at } gmail.com
*   github: github.com/onurozuduru
*   twitter: twitter.com/OnurOzuduru
*
*License: The MIT License (MIT)
*
*   Copyright (c) 2014 Onur Ozuduru
*   Permission is hereby granted, free of charge, to any person obtaining a copy
*   of this software and associated documentation files (the "Software"), to deal
*   in the Software without restriction, including without limitation the rights
*   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
*   copies of the Software, and to permit persons to whom the Software is
*   furnished to do so, subject to the following conditions:
*  
*   The above copyright notice and this permission notice shall be included in all
*   copies or substantial portions of the Software.
*  
*   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
*   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
*   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
*   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
*   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
*   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
*   SOFTWARE.
*********************************************************************************/

package com.ozuduru.shooterGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;

public class Cannon extends JPanel {
	// File paths for cannon and background images.
	// File paths are top of the class to be changed easily.
	private final String fileCannonImage = "images/cannons/cannon0.png",
			fileCannonFireImage = "images/cannons/cannon1.png",
			fileBackGround = "images/backgrounds/cannonbg.png";
	
	private final int width = 100, height = 150;// Width and Height values of the panel.
	
	private Image cannonImage, cannonFireImage;
	
	// positionY: y position of the panel on the window.
	// imagePosY: y position of the cannon image on this panel.
	private int positionY, imagePosY;
	private double angle;// angle of the image.
	private static boolean fire;
	
	public Cannon() {
		// Calculate panel's y position on the window.
		positionY = (Game.HEIGHT / 2) - (height / 2) + 50;
		
		// Set position as (0, positionY) and size.
		setBounds(0, positionY, width, height);

		setBackground(Color.WHITE);
		 
		setFire(false);
		setImages();
	}

	private void setImages() {
		// Initialize normal and fired cannon images.
		cannonImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource(fileCannonImage));
		cannonFireImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource(fileCannonFireImage));
		
		// Wait until the Image's getHeight function returns the height value.
		// The getHeight function returns -1 if the image's height is not yet known.
		while(cannonImage.getHeight(null) == -1)
			System.out.println("class Canon: Waiting for image height...");
		
		// Calculate cannon image's y position on the panel.
		imagePosY = (height / 2) - (cannonImage.getHeight(null) / 2) - 15;
		angle = 0;
	}

	public static void setFire(boolean f) {
		fire = f;
	}
	
	public void rotate(int x, int y, boolean fire) {
		setFire(fire);
		// Calculate angle of the image according to given x y arguments.
		// angle = arctan( [y - (panel's y position) + (image's y position)] / x)
		angle = Math.atan2(y - (positionY + imagePosY), x);
		
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;
		
		// rotation angle is angle, rotation origin x is 15, rotation origin y is imagePosY + 60.
		// rotation origin positions private final int scorePoint = 200;was found by experiments.
		g2d.rotate(angle, 15, imagePosY + 60);
		
		// If it is firing paint cannon fire image.
		if(!fire) 	
			g2d.drawImage(cannonImage, 0, imagePosY, null);
		else
			g2d.drawImage(cannonFireImage, 0, imagePosY, null);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Draw background of the panel.
		g.drawImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(fileBackGround)), 0, 0, null);
	}
}
