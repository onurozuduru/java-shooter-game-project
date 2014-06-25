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
