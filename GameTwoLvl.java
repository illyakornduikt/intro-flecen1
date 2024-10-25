package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameTwoLvl extends JPanel {
	public static final int WIDGHT = 1090;
	public static final int HEIGHT = 780;
	public static final int PADDLE_WIDTH = 20;
	public static final int PADDLE_HEIGHT = 100;
	public static final int PADDLE_SPEED = 3;
	public static final int LEFT_TOP_X = 10;
	public static final int LEFT_TOP_Y = 10;
	public static final int RIGHT_BOT_X = WIDGHT - 10;
	public static final int RIGHT_BOT_Y = HEIGHT - 10;
	public static final int DIAMETER_BALL = 25;
	public static final int FPS = 1000 / 120;

	private int frameCount = 0;
	private long elapsedTime = 0;
	private long lastTime = System.currentTimeMillis();
	private int fps = 0;

	static Random random1 = new Random();
	public static int ballX = random1.nextInt(WIDGHT - DIAMETER_BALL);
	public static int ballY = random1.nextInt(HEIGHT - DIAMETER_BALL);
	public static int ballVelocityX = 2;
	public static int ballVelocityY = 3;
	public static int leftPaddleY = (HEIGHT - PADDLE_HEIGHT) / 2;
	public static int rightPaddleY = (HEIGHT - PADDLE_HEIGHT) / 2;

	private int leftScore = 0;
	private int rightScore = 0;

	public GameTwoLvl() {
		setLayout(null);

		Timer timer = new Timer(1, e -> {

			long currentTime = System.currentTimeMillis();
			long deltaTime = currentTime - lastTime;
			lastTime = currentTime;

			frameCount++;
			elapsedTime += deltaTime;

			if (elapsedTime >= 1000) {
				fps = (int) (frameCount * 1000 / elapsedTime);
				frameCount = 0;
				elapsedTime = 0;
			}

			update();
			repaint();

			requestFocusInWindow();
		});
		timer.start();

		setFocusable(true);
		requestFocusInWindow();
		addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {

				int key = e.getKeyCode();
				if (key == KeyEvent.VK_W) {
					moveLeftPaddleUp();
				} else if (key == KeyEvent.VK_S) {
					moveLeftPaddleDown();
				} else if (key == KeyEvent.VK_P) {
					moveRightPaddleUp();
				} else if (key == KeyEvent.VK_L) {
					moveRightPaddleDown();
				}
			}

		});
	}

	void moveLeftPaddleUp() {
		if (leftPaddleY - PADDLE_SPEED >= LEFT_TOP_Y) {
			leftPaddleY -= PADDLE_SPEED;
		}
	}

	void moveLeftPaddleDown() {
		if (leftPaddleY + PADDLE_HEIGHT + PADDLE_SPEED <= HEIGHT - LEFT_TOP_Y) {
			leftPaddleY += PADDLE_SPEED;
		}
	}

	void moveRightPaddleUp() {
		if (rightPaddleY - PADDLE_SPEED >= LEFT_TOP_Y) {
			rightPaddleY -= PADDLE_SPEED;
		}
	}

	void moveRightPaddleDown() {
		if (rightPaddleY + PADDLE_HEIGHT + PADDLE_SPEED <= HEIGHT - LEFT_TOP_Y) {
			rightPaddleY += PADDLE_SPEED;
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		draw(g2d);
		g.setColor(Utils.getWhiteColor());
		g.setFont(Utils.getFont());
		g.drawString("FPS: " + fps, 1000, 30);
		g.drawString("Left Score: " + leftScore, 20, 760);
		g.drawString("Right Score: " + rightScore, 900, 760);
	}

	public void update() {
		ballX += ballVelocityX;
		ballY += ballVelocityY;

		checkCollision();
	}

	public void draw(Graphics2D g) {
		g.setColor(Utils.getBlackColor());
		g.fillRect(0, 0, WIDGHT, HEIGHT);

		g.setColor(Utils.getRedColor());
		g.drawRect(LEFT_TOP_X, LEFT_TOP_Y, WIDGHT - 20, HEIGHT - 20);

		g.setColor(Utils.getGreenColor());
		g.fillOval(ballX, ballY, DIAMETER_BALL, DIAMETER_BALL);

		g.setColor(Utils.getBlueColor());
		g.fillRect(LEFT_TOP_X, leftPaddleY, PADDLE_WIDTH, PADDLE_HEIGHT);

		g.setColor(Utils.getYellowColor());
		g.fillRect(RIGHT_BOT_X - PADDLE_WIDTH, rightPaddleY, PADDLE_WIDTH, PADDLE_HEIGHT);
	}

	public void checkCollision() {
		if (ballY <= LEFT_TOP_Y || ballY + DIAMETER_BALL >= RIGHT_BOT_Y) {
			ballVelocityY *= -1;
		}

		if (ballX <= LEFT_TOP_X + PADDLE_WIDTH && ballY + DIAMETER_BALL >= leftPaddleY
				&& ballY <= leftPaddleY + PADDLE_HEIGHT) {
			ballX = LEFT_TOP_X + PADDLE_WIDTH;
			ballVelocityX *= -1;
		}

		if (ballX + DIAMETER_BALL >= RIGHT_BOT_X - PADDLE_WIDTH && ballY + DIAMETER_BALL >= rightPaddleY
				&& ballY <= rightPaddleY + PADDLE_HEIGHT) {
			ballX = RIGHT_BOT_X - PADDLE_WIDTH - DIAMETER_BALL;
			ballVelocityX *= -1;
		}

		if (ballX <= LEFT_TOP_X) {
			ballVelocityX *= -1;
			rightScore++;
		}

		if (ballX + DIAMETER_BALL >= RIGHT_BOT_X) {
			ballVelocityX *= -1;
			leftScore++;
		}
	}
}