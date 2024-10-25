package game;

import java.util.Random;
import java.awt.Color;
import java.awt.Font;

class Utils {
	public static double map(double value1, double start1, double stop1, double start2, double stop2) {
		double value2 = start2 + (stop2 - start2) * ((value1 - start1) / (stop1 - start1));
		return value2;
	}

	public static Random random = new Random();

	public static Color getRandomColor() {
		int r = random.nextInt(256);
		int g = random.nextInt(256);
		int b = random.nextInt(256);

		return new Color(r, g, b);
	}
	public static Color getBlackColor() {
		Color black = new Color(0,0,0);
		return black;
	}
	public static Color getRedColor() {
		Color red = new Color(255,0,0);
		return red;
	}
	public static Color getGreenColor() {
		Color green = new Color(0,128,0);
		return green;
	}
	public static Color getBlueColor() {
		Color blue = new Color(0,0,255);
		return blue;
	}
	public static Color getYellowColor() {
		Color yellow = new Color(255, 202, 25);
		return yellow;
	}
	public static Color getWhiteColor() {
		Color white = new Color(255, 255, 255);
		return white;
	}
	public static Font getFont() {
		Font font = new Font("Engravers MT", Font.BOLD, 15);
		return font;
		}
	public static int getNumber() {
		return 7;
		}
	public static int getPixel() {
		return 5;
		}

}
