package game;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;

class Star {
	private double x, y, z;
	private int width, height;
	private double speed;
	private float alpha;
	private int size;

	public Star(int screenWidth, int screenHeight) {
		width = screenWidth;
		height = screenHeight;
		initialize();
	}

	private void initialize() {
		x = Math.random() * 2 * width - width;
		y = Math.random() * 2 * height - height;
		z = Math.random() * 300 + 1;
		speed = Math.random() * 7 + 1;
		alpha = 1.0f;
		size = (int) (Math.random() * 2 + 1);
	}

	public void show(Graphics2D g) {

		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g.setColor(Utils.getRandomColor());

		int x3D = calculateCoordinate(x);
		int y3D = calculateCoordinate(y);

		g.fillOval(x3D, y3D, size, size);

		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
	}

	public void update() {

		z -= speed;
		if (z < 1) {
			initialize();
		} else {
			alpha = Math.max(0.1f, (float) (z / 300));
		}
	}

	private int calculateCoordinate(double value) {

		double scaledValue = value / z;

		return (int) Utils.map(scaledValue, -1.7, 0.7, 2.2, width);
	}
}
