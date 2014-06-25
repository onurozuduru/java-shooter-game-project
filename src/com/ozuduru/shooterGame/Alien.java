/*
*Developed by:
*name:	Onur Ozuduru
*github page:	https://github.com/onurozuduru
*twitter:	https://twitter.com/OnurOzuduru
*e-mail: onur.ozuduru { at } gmail.com
*
*This work is licensed under the Creative Commons Attribution 4.0 International License.
*To view a copy of this license, visit http://creativecommons.org/licenses/by/4.0/.
*/
package com.ozuduru.shooterGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.Timer;

public abstract class Alien extends JPanel implements Creature, MouseListener {
	private final int scorePoint = 100;
	
	protected int deadLine;// game will over when Alien get that position.
	protected boolean manIsDown;// if true animation will changed and will disappear.
	protected int x, y, width, height;
	protected int moveSpeed;
	
	protected BufferedImage[] frames;
	protected int frameLivingLimit, frameDeadLimit, frameCurrent, frameStart;
	
	protected Timer animationTimer;
	
	public Alien(BufferedImage[] frames,int frameLivingLimit, int x, int y) {
		// Just to be safe, call setters and pass arguments to them.
		setFrames(frames, frameLivingLimit);
		setX(x);
		setY(y);
		
		// Width and Height values are the same with frames width and height values.
		width = frames[0].getWidth();
		height = frames[0].getHeight();
		
		manIsDown = false;

		deadLine = 100;
		
		// Set size and position of the panel.
		setPreferredSize(new Dimension(width, height));
		setBounds(x, y, width, height);
		
		// Set transparent background.
		setOpaque(true);
		setBackground(new Color(0, 0, 0, 0));
		
		// Add listener.
		addMouseListener(this);
		
		// It is the loop for animation.
		animationTimer = new Timer(Game.REFRESH_TIME, new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!GameBoard.isGameOver)
					update();
			}});
		animationTimer.start();
	}
	
	public Alien(String filePath, int row, int col, int frameLivingLimit, int x, int y) 
			throws IOException {
		this(SpriteSheetLoader.createSprites(filePath, row, col), frameLivingLimit, x, y);
	}

	// Sets image frames for animation and frameStart, frameCurrent, frameDeadLimit.
	public void setFrames(BufferedImage[] frames, int frameLivingLimit) {
		this.frames = frames;
		if(frameLivingLimit > 0)
			this.frameLivingLimit = frameLivingLimit;
		else
			throw new IllegalArgumentException("frameLivingLimit CANNOT BE zero or a negative number!");
		this.frameDeadLimit = frames.length;
		this.frameStart = 0;
		this.frameCurrent = frameStart;
	}
	
	public void setY(int y) {
		if(y >= 0)
			this.y = y;
		else
			throw new IllegalArgumentException("y CANNOT BE negative!");		
	}

	public void setX(int x) {
		if(x >= 0)
			this.x = x;
		else
			throw new IllegalArgumentException("x CANNOT BE negative!");
	}
	
	public void setMoveSpeed(int moveSpeed) {
		if(moveSpeed >= 0)
			this.moveSpeed = moveSpeed;
		else
			throw new IllegalArgumentException("moveSpeed CANNOT BE negative!");
	}

	public int getMoveSpeed() {
		return moveSpeed;
	}
	
	// It will decide which frame should be current frame while shooting
	//		also it will change manIsDown to true according to alien's strength.
	public abstract void shooting();
	
	@Override
	public Timer getAnimationTimer() {
		return animationTimer;
	}
	
	@Override
	public void move() {
		// if not dead, change position.
		if(!manIsDown){
			if(x > deadLine)
				x -= moveSpeed;
			else {
				x = deadLine;
				GameBoard.isGameOver = true; // game will over when an Alien reaches to deadLine.
				animationTimer.stop();
			}
			// setLocation of the panel.
			setLocation(x, y); 
		}
	}

	@Override
	public boolean isAlive() {
		return (frameCurrent < frameDeadLimit);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g); // call super class' paint function to clear.
		Graphics2D g2d = (Graphics2D) g;
		
		// if alive draw current image, otherwise draw transparent color.
		if(isAlive())
			g2d.drawImage(frames[frameCurrent], 0, 0, null);
		else
			setForeground(new Color(0,0,0,0));
	}
	
	@Override
	public void update() {
		// call move() to set new location of the panel.
		move();
		
		// Decide which frame will be current.
		if((++frameCurrent == frameLivingLimit) && !manIsDown)
			frameCurrent = frameStart;
		else if(manIsDown) 
			frameCurrent = (frameCurrent < frameLivingLimit) ? frameLivingLimit : (frameCurrent + 1);
		
		// call repaint() to paint current image.
		repaint();
	}

	@Override
	public int getScorePoint() {
		return this.scorePoint;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(GameBoard.isGameOver)
			return;
		Cannon.setFire(true);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		setCursor(Game.CURSOR_LOCKED);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		setCursor(Game.CURSOR_UNLOCKED);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(GameBoard.isGameOver)
			return;
		Cannon.setFire(true);
		shooting();
		update();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(GameBoard.isGameOver)
			return;
		Cannon.setFire(false);
	}

}
