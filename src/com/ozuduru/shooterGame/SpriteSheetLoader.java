/*********************************************************************************
*File: SpriteSheetLoader.java
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
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;

import javax.imageio.ImageIO;

public class SpriteSheetLoader {
	
	public SpriteSheetLoader() {		
	}
	
	// It reads the image file from filePath and creates a BufferedImage array
	//		with dividing the image file into subimages according to given row and col values.
	public static BufferedImage[] createSprites(String filePath, int row, int col) 
			throws IOException {
		if(row <= 0 || col <= 0)
			throw new IllegalArgumentException("row and col must be positive!");
		
		// Size of array will be col * row
		BufferedImage[] sprites = new BufferedImage[row * col];
		
		// Read the Image
		BufferedImage spriteSheet = ImageIO.read(SpriteSheetLoader.class.getResourceAsStream(filePath)); //ImageIO.read(new File(filePath));
		
		// Calculate width and height of the each sprite.
		//		We assume that sprite sheet consists of all same sized sprites.
		int width = spriteSheet.getWidth() / col;
		int height = spriteSheet.getHeight() / row;

		// Get sprites (sub images) by left to right order from sprite sheet. 
		for(int i = 0; i < row; ++i)
			for(int j = 0; j < col; ++j)
				sprites[(i * col) + j] = spriteSheet.getSubimage(j * width, i * height, width, height);
		
		return sprites;
	}
}
