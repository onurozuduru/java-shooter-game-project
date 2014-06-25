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

import javax.swing.Timer;

public interface Creature {
	public void move();// Calculates new position of the Creature.
	public boolean isAlive();
	public void update();// Decides which frame should be current.
	public int getScorePoint();
	Timer getAnimationTimer();
}
