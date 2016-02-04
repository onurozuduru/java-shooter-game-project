/*********************************************************************************
*File: EnemyGenerator.java
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
import java.util.Random;

public class EnemyGenerator {
	private final String fileGreenAlien = "images/creatures/greenalien.png",
			fileBlueAlien = "images/creatures/bluealien.png";
	
	private BufferedImage[] greenFrames, blueFrames;
	
	private Random rand;
	public EnemyGenerator() throws IOException {
		rand = new Random(1000000000);
		greenFrames = SpriteSheetLoader.createSprites(fileGreenAlien, 2, 5);
		blueFrames = SpriteSheetLoader.createSprites(fileBlueAlien, 3, 6);
	}
	
	public Alien generateNewEnemy() {
		// Randomly generates new creatures.
		int creatureType = rand.nextInt(2);
		
		switch (creatureType) {
		case 0:
			return new GreenAlien(greenFrames, 5, (rand.nextInt(100) + (Game.WIDTH - 100)), rand.nextInt(Game.HEIGHT - 200) + 50);
		case 1:
			return new BlueAlien(blueFrames, 12, (rand.nextInt(100) + (Game.WIDTH - 100)), rand.nextInt(Game.HEIGHT - 200) + 50);
		}
		return null;
	}

}
