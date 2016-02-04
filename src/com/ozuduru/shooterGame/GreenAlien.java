/*********************************************************************************
*File: GreenAlien.java
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

public class GreenAlien extends Alien {

	// Constructor calls Class Alien's constructer with given arguments
	//		and sets speed to 5.
	public GreenAlien(BufferedImage[] frames, int frameLivingLimit, int x, int y) {
		super(frames, frameLivingLimit, x, y);
		
		setMoveSpeed(5);
	}

	// Constructor creates a BufferedImage array with given filePath argument
	//		and calls the constructor GreenAlien(BufferedImage[] frames, int frameLivingLimit, int x, int y)
	public GreenAlien(String filePath, int row, int col, int frameLivingLimit,
			int x, int y) throws IOException {
		this(SpriteSheetLoader.createSprites(filePath, row, col), frameLivingLimit, x, y);
	}

	// shooting function only changes manIsDown field to true,
	//		because the strength of the GreenAlien is 0 which
	//		means that it will die after one shot.
	@Override
	public void shooting() {
		manIsDown = true;
	}

}
