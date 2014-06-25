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

import java.awt.HeadlessException;
import java.io.IOException;

import javax.swing.JOptionPane;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Create a new Game object and call startGame function.
		// Catch possible exceptions and show an error message to the user.
		try {
			new Game().startGame();
		} catch (HeadlessException e) {
			JOptionPane.showMessageDialog(null,
					"We are sorry about that!\nError: " + e.getMessage(),
					"Opps!! Something went wrong!", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"It seems that there is a problem on your file system!\nError: " + e.getMessage(),
					"Opps!! Something went wrong!", JOptionPane.ERROR_MESSAGE);
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(null,
					"We are sorry about that!\nError: " + e.getMessage(),
					"Opps!! Something went wrong!", JOptionPane.ERROR_MESSAGE);
		}
	}
}
