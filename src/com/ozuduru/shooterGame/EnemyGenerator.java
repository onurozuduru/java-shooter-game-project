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
