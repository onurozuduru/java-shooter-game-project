/*********************************************************************************
*File: BlueAlien.java
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

import java.awt.image.BufferedImage;
import java.io.IOException;

public class BlueAlien extends Alien {
	private final int scorePoint = 200;
	
	protected int strength, shotCount;
	private int limit;// will be used as second living limit.
	
	// Constructor calls Class Alien's constructer with given arguments and
	//		half of the given frameLivingLimit argument because BlueAlien has
	//		a strength value which means that after one shot its shape will be changed
	//		but it will not die untill the shotCount value reaches to strength value.
	public BlueAlien(BufferedImage[] frames, int frameLivingLimit, int x, int y) {
		super(frames, frameLivingLimit / 2, x, y);
		
		setMoveSpeed(6);
		limit = frameLivingLimit;
		shotCount = 0;
		
		// Die after 3 shots.
		setStrength(3);
	}
	
	public BlueAlien(String filePath, int row, int col, int frameLivingLimit,
			int x, int y) throws IOException {
		this(SpriteSheetLoader.createSprites(filePath, row, col), frameLivingLimit, x, y);
	}
	
	// Strength value may be greater or equal to 0 otherwise it throws an exception.
	public void setStrength(int strength) {
		if(strength >= 0)
			this.strength = strength;
		else
			throw new IllegalArgumentException("Value of strength CANNOT BE a negative number!");
	}

	@Override
	public void shooting() {
		// After the first shot the animation will start from 6th frame so
		//		the shape will be changed.
		// When shotCount value equals to strength value the manIsDown will be become to true,
		//		so it will be dead.
		if(++shotCount < strength) {
			frameStart = 6;
			frameLivingLimit = limit;
		}
		else
			manIsDown = true;
		repaint();
	}

	// BlueAlien has own scorePoint value which is different from Alien's default value 100.
	@Override
	public int getScorePoint() {
		return this.scorePoint;
	}
}
