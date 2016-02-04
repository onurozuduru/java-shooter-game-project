/*********************************************************************************
*File: Game.java
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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class Game extends JFrame implements ActionListener{
	// The static cursors will be used in different places in the game (i.e. in Class Alien).
	// File paths are top of the class to be changed easily.
	protected static Cursor CURSOR_LOCKED, CURSOR_UNLOCKED, CURSOR_DEFAULT;
	private final String fileCursorLocked = "images/cursors/locked.png",
			fileCursorUnlocked = "images/cursors/unlocked.png",
			fileCursorDefault = "images/cursors/default.png";
	
	// Set width and height of main window.
	// 		Other classes will use these values that is why they are static.
	protected final static int WIDTH = 900, HEIGHT = 500;
	
	protected final static int REFRESH_TIME = 150;
	
	protected static int highScore = 0;
	
	// We will not only read also will write on fileScore,
	// 		so since we cannot write on a file which is in a jar file, we must create a new file
	//		which is outside of the jar file and we need that ugly line.
	// In that notation line:
	// getClass().getProtectionDomain().getCodeSource().getLocation().getPath()
	// 		gives the path with the jar file's name to get directory of the jar file 
	//		we create a new File then get its parent with getParent().
	// After that we put a file separator -which is different for Linux/Unix and Windows-
	// 		and the file name __score_data__.txt
	private final String fileScore =
			new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getParent() +
			File.separator + "__score_data__.txt";
	
	private final String fileCredits = "data/credits.txt";
	
	private Font defaultFont;
	private JPanel menuPanel, highScorePanel, creditsPanel;
	private JButton playButton, quitButton, creditsButton, highScoreButton,
			backButton0, backButton1;
	private JTextField txtHighScore;
	
	public enum GameState {NEW, CONTINUE, OVER, HIGHSCORE, CREDITS, WAIT, QUIT};
	public static GameState state;
	
	public Game() throws HeadlessException, IOException {
		this("SHOOTER GAME");
	}

	public Game(String title) throws HeadlessException, IOException {
		super(title);
		
		initCursors();
		loadHighScore();
		setBackButtons();
		
		defaultFont = new Font(Font.SERIF, Font.BOLD, 24);
		
		setMenuPanel();
		setHighScorePanel();
		setCreditsPanel();
		
		// Set layout as null since every component's position are declared by setLocation or setBounds functions.
		setLayout(null);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.WHITE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		pack();
	}

	private void setBackButtons() {
		backButton0 = createButton(new String[] {"images/buttons/back0.png",
												"images/buttons/back1.png",
												"images/buttons/back2.png"});
		backButton1 = createButton(new String[] {"images/buttons/back0.png",
												 "images/buttons/back1.png",
												 "images/buttons/back2.png"});		
	}

	public void initCursors() {
		// Get default tookit to create new cursors.
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		
		// Read images from filepaths.
		Image lockedImage = toolkit.getImage(getClass().getResource(fileCursorLocked));
		Image unlockedImage = toolkit.getImage(getClass().getResource(fileCursorUnlocked));
		Image menuImage = toolkit.getImage(getClass().getResource(fileCursorDefault));
		
		// Create new cursors with desired parameters.
		// 		Since locked and unlocked cursors 40x40 images, hotspots are (20, 20).
		CURSOR_LOCKED = toolkit.createCustomCursor(lockedImage, new Point(20, 20), "cursorLocked");
		CURSOR_UNLOCKED = toolkit.createCustomCursor(unlockedImage, new Point(20, 20), "cursorUnlocked");
		CURSOR_DEFAULT = toolkit.createCustomCursor(menuImage, new Point(16, 16), "cursorDefault");
	}
	
	public void loadHighScore() throws IOException {
		// new File with the file path, it decodes as UTF-8 because there might be some special characters in the file path.
		File file = new File(URLDecoder.decode(fileScore, "UTF-8"));
		
		// If file does not exist create a new file and set highScore as 0.
		if(!file.canRead()) {
			file.createNewFile();
			setHighScore(0);
		}
		else {
			BufferedReader reader = new BufferedReader(new FileReader(file));//(new InputStreamReader(getClass().getResourceAsStream(fileScore)));
			String line = reader.readLine();
			// If file is empty set highScore as 0.
			setHighScore( (line != null) ? Integer.parseInt(line) : 0 );
			reader.close();
		}
	}

	public static void setState(GameState state) {
		Game.state = state;
	}

	public static void setHighScore(int score) {
		if(score < 0)
			throw new IllegalArgumentException("High Score CANNOT BE a negative number!");
		if(score > highScore)
			highScore = score;
	}
	
	public static int getHighScore() {
		return highScore;
	}

	public void saveHighScore() throws IOException, SecurityException {
		// new File with the file path, it decodes as UTF-8 because there might be some special characters in the file path.
		File file = new File(URLDecoder.decode(fileScore, "UTF-8"));
		
		if(!file.canWrite())
			file.createNewFile();
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		
		writer.write(Integer.toString(getHighScore()));
		writer.close();
	}
	
	public void setMenuPanel() {
		menuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		menuPanel.setBounds(0, 0, WIDTH, HEIGHT);
		menuPanel.setBackground(Color.WHITE);
		menuPanel.setCursor(Game.CURSOR_DEFAULT);
		
		JPanel innerPanel = new JPanel(new GridLayout(4, 1, 5, 5));
		innerPanel.setPreferredSize(new Dimension(310, 440));
		innerPanel.setBackground(Color.WHITE);
		
		playButton = createButton(new String[] {"images/buttons/play0.png",
												"images/buttons/play1.png",
												"images/buttons/play2.png"});
		
		highScoreButton = createButton(new String[] {"images/buttons/highscore0.png",
													 "images/buttons/highscore1.png",
													 "images/buttons/highscore2.png"});
		
		creditsButton = createButton(new String[] {"images/buttons/credits0.png",
													"images/buttons/credits1.png",
													"images/buttons/credits2.png"});
		
		quitButton = createButton(new String[] {"images/buttons/quit0.png",
												"images/buttons/quit1.png",
												"images/buttons/quit2.png"});
		innerPanel.add(playButton);
		innerPanel.add(highScoreButton);
		innerPanel.add(creditsButton);
		innerPanel.add(quitButton);
		
		menuPanel.add(innerPanel);
	}
	
	public void setHighScorePanel() {
		highScorePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		highScorePanel.setBounds(0, 0, WIDTH, HEIGHT);
		highScorePanel.setBackground(Color.WHITE);
		highScorePanel.setCursor(Game.CURSOR_DEFAULT);
		
		JPanel innerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
		innerPanel.setPreferredSize(new Dimension(800, 220));
		innerPanel.setBackground(Color.WHITE);
		
		JLabel lblMessage = new JLabel("High Score", JLabel.CENTER);
		lblMessage.setForeground(Color.BLACK);
		lblMessage.setFont(defaultFont);
		lblMessage.setHorizontalTextPosition(JLabel.CENTER);
		
		txtHighScore = new JTextField(Integer.toString(highScore), 6);
		txtHighScore.setFont(defaultFont);
		txtHighScore.setHorizontalAlignment(JTextField.CENTER);
		txtHighScore.setEditable(false);
		
		innerPanel.add(lblMessage, BorderLayout.NORTH);
		innerPanel.add(txtHighScore, BorderLayout.CENTER);
		
		highScorePanel.add(innerPanel);
		highScorePanel.add(backButton0);
	}
	
	public void setCreditsPanel() throws IOException {
		creditsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		creditsPanel.setBounds(0, 0, WIDTH, HEIGHT);
		creditsPanel.setBackground(Color.WHITE);
		creditsPanel.setCursor(Game.CURSOR_DEFAULT);
		
		String text = "CREDITS";

		if(getClass().getResourceAsStream(fileCredits) == null)
			throw new FileNotFoundException("credits.txt could NOT be found!");
		else {
			BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(fileCredits)));
			String line = " ";
			while(line != null) {
				text = text + "\n" + line;
				line = reader.readLine();
			}
			reader.close();
		}
		
		JTextPane txtPane = new JTextPane();
		txtPane.setText(text);
		txtPane.setEditable(false);
		
		// StyledDocument for changing font and aligment of the text.
		StyledDocument doc = txtPane.getStyledDocument();
		
		SimpleAttributeSet body = new SimpleAttributeSet();
		SimpleAttributeSet header = new SimpleAttributeSet();
		
		// Font size is 16 for body and aligment is center.
		StyleConstants.setAlignment(body, StyleConstants.ALIGN_CENTER);
		StyleConstants.setFontSize(body, 16);
		
		// Font is bold and size is 16 for header and aligment is center.
		StyleConstants.setFontSize(header, 24);
		StyleConstants.setBold(header, true);
		StyleConstants.setAlignment(header, StyleConstants.ALIGN_CENTER);
		
		doc.setParagraphAttributes(7, doc.getLength(), body, false);
		doc.setParagraphAttributes(0, 7, header, false);
		
		JScrollPane scrollPane = new JScrollPane(txtPane);
		scrollPane.setPreferredSize(new Dimension(850, HEIGHT - 200));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		creditsPanel.add(scrollPane);
		creditsPanel.add(backButton1);
	}
	
	// Helper function to create a button.
	// Gets 3 file paths to creates 3 icons.
	// icon[0]: button icon
	// icon[1]: roll over icon.
	// icon[2]: pressed icon.
	private JButton createButton(String[] iconFiles) {
		ImageIcon[] icon = new ImageIcon[iconFiles.length];
		for(int i = 0; i < iconFiles.length; ++i)
			icon[i] = new ImageIcon(getClass().getResource(iconFiles[i]));

		JButton button = new JButton(icon[0]);
		button.setBorderPainted(false);
		button.setFocusable(true);
		button.setFocusPainted(false);
		button.setRolloverEnabled(true);
		button.setRolloverIcon(icon[1]);
		button.setPressedIcon(icon[2]);
		button.setContentAreaFilled(false);
		button.addActionListener(this);
		
		return button;
	}
	
	public void startGame() throws IOException {
		
		// Main thread of the program for games states.
		Thread t = new Thread(new Runnable() {		
			@Override
			public void run() {
				// menu will be shown at the beginning.
				setState(GameState.WAIT);
				add(menuPanel);
				GameBoard board = null;
				
				// Loop until quit.
				while(!state.equals(GameState.QUIT)) {
					switch (state) {
					// Waits for 200 ms.
					case WAIT:
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							exceptionOther(e.getMessage());
						}
						break;
					// Removes menu and creates a new gameBoard.
					case NEW:
						remove(menuPanel);
						repaint();
						try {
							board = new GameBoard();
						} catch (IOException e) {
							exceptionIO(e.getMessage());
						}
						add(board);
						pack();
						board.gameLoop();
						setState(GameState.WAIT);
						break;
					// After game over if user wants to play a new game.
					case CONTINUE:
						try {
							saveHighScore();
						} catch (IOException e) {
							exceptionIO(e.getMessage());
						} catch (SecurityException e) {
							exceptionSecurity(e.getMessage());
						}
						remove(board);
						repaint();
						try {
							board = new GameBoard();
						} catch (IOException e) {
							exceptionIO(e.getMessage());
						}
						add(board);
						repaint();
						pack();
						board.gameLoop();
						setState(GameState.WAIT);
						break;
					// If game is over and user do not want to play a new game back to the menu.
					case OVER:
						try {
							saveHighScore();
						} catch (IOException e) {
							exceptionIO(e.getMessage());
						} catch (SecurityException e) {
							exceptionSecurity(e.getMessage());
						}
						remove(board);
						repaint();
						add(menuPanel);
						repaint();
						pack();
						setState(GameState.WAIT);
						break;
					// Show high score panel.
					case HIGHSCORE:
						txtHighScore.setText(Integer.toString(getHighScore()));
						remove(menuPanel);
						repaint();
						add(highScorePanel);
						repaint();
						pack();
						setState(GameState.WAIT);
						break;
					// Show credits panel.
					case CREDITS:
						remove(menuPanel);
						repaint();
						add(creditsPanel);
						repaint();
						pack();
						setState(GameState.WAIT);
						break;
					case QUIT:
						break;
					}// End of switch-case
				}// End of while
				// When loop is done quit the program.
				dispose();
				System.exit(0);
			}// End of run.
		});// End of Thread t.
		t.start();
	}

	private void exceptionIO(String e) {
		JOptionPane.showMessageDialog(null,
				"It seems that there is a problem on your file system!\nError: " + e,
				"Opps!! Something went wrong!", JOptionPane.ERROR_MESSAGE);
	}
	private void exceptionSecurity(String e) {
		JOptionPane.showMessageDialog(null,
				"It seems that there is a problem about file's permission!\nError: " + e,
				"Opps!! Something went wrong!", JOptionPane.ERROR_MESSAGE);
	}
	private void exceptionOther(String e) {
		JOptionPane.showMessageDialog(null,
				"We are sorry about that!\nError: " + e,
				"Opps!! Something went wrong!", JOptionPane.ERROR_MESSAGE);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(playButton))
			setState(GameState.NEW);
		
		else if(e.getSource().equals(highScoreButton))
			setState(GameState.HIGHSCORE);
		
		else if(e.getSource().equals(backButton0) || e.getSource().equals(backButton1)) {
			remove(((JButton) e.getSource()).getParent());
			repaint();
			add(menuPanel);
			pack();
			setState(GameState.WAIT);
		}
		
		else if(e.getSource().equals(quitButton))
			setState(GameState.QUIT);
		
		else if(e.getSource().equals(creditsButton))
			setState(GameState.CREDITS);
	}
	
}
