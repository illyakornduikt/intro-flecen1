package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Game extends JPanel {

    public static final int W = 1090;
    public static final int H = 780;
    public static final int PADDLE_WIDTH = 20;
    public static final int PADDLE_HEIGHT = 100;
    public static final double PADDLE_SPEED = 5000;
    public static final int LEFT_TOP_X = 10;
    public static final int LEFT_TOP_Y = 10;
    public static final int RIGHT_BOT_X = W - 10;
    public static final int RIGHT_BOT_Y = H - 10;
    public static final int DIAMETER_BALL = 20;

    private int leftScore = 0;
    private int rightScore = 0;
    private static final int WINNING_SCORE = 1;

    static Random random1 = new Random();
    public static double ballX = random1.nextInt(Game.W - Game.DIAMETER_BALL);
    public static double ballY = random1.nextInt(Game.H - Game.DIAMETER_BALL);
    public static double ballSpeed = 10000;
    public static double ballDirectionX = 3;
    public static double ballDirectionY = 3;

    public static double leftPaddleY = 350;
    public static double rightPaddleY = 350;
    private double leftPaddleSpeedAccumulator = 0;

    private boolean gameOver = false;
    private boolean gamePaused = false;
    private FPSManager fpsManager;

    public Game() {
        normalizeBallDirection();
        setFocusable(true);
        fpsManager = new FPSManager();

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_W) {
                    moveLeftPaddleUp();
                } else if (key == KeyEvent.VK_S) {
                    moveLeftPaddleDown();
                } else if (key == KeyEvent.VK_P) {
                    togglePause();
                } else if (key == KeyEvent.VK_ENTER && gameOver) {
                    restartGame();
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (gamePaused) {
                    togglePause();
                }
            }
        });

        new Thread(() -> {
            while (true) {
                fpsManager.update();
                updateGame(1.0 / 1000.0);
                repaint();
            }
        }).start();
    }

    public void normalizeBallDirection() {
        double magnitude = Math.sqrt(ballDirectionX * ballDirectionX + ballDirectionY * ballDirectionY);
        ballDirectionX /= magnitude;
        ballDirectionY /= magnitude;
    }

    void moveLeftPaddleUp() {
        leftPaddleSpeedAccumulator = -PADDLE_SPEED;
    }

    void moveLeftPaddleDown() {
        leftPaddleSpeedAccumulator = PADDLE_SPEED;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        draw(g2d);

        g.setColor(Utils.getWhiteColor());
        g.setFont(Utils.getFont());
        g.drawString("Left Score: " + leftScore, 20, 760);
        g.drawString("Right Score: " + rightScore, 900, 760);

        g.setColor(Utils.getWhiteColor());
        g.setFont(Utils.getFont());
        g.drawString("FPS: " + fpsManager.getFPS(), 990, 30);


        if (gamePaused) {
            g.setColor(Utils.getWhiteColor());
            g.setFont(Utils.getFont());
            g.drawString("PAUSED", W / 2 - 55, H / 2);
            g.setFont(Utils.getFont());
            g.drawString("Click to continue", W / 2 - 120, H / 2 + 30);
        }
    }

    public void updateGame(double deltaSeconds) {
        if (!gameOver && !gamePaused) { 
            ballX += ballSpeed * ballDirectionX * deltaSeconds;
            ballY += ballSpeed * ballDirectionY * deltaSeconds;

            checkCollision();
            moveRightPaddle(deltaSeconds);
            updateLeftPaddle(deltaSeconds);
        }
    }

    private void updateLeftPaddle(double deltaSeconds) {
        double moveAmount = leftPaddleSpeedAccumulator * deltaSeconds;

        if (leftPaddleY + moveAmount >= LEFT_TOP_Y && leftPaddleY + PADDLE_HEIGHT + moveAmount <= H - LEFT_TOP_Y) {
            leftPaddleY += moveAmount;
        }
    }

    private void moveRightPaddle(double deltaSeconds) {
        int paddleCenter = (int) (rightPaddleY + PADDLE_HEIGHT / 2);
        int ballCenter = (int) (ballY + DIAMETER_BALL / 2);

        int distance = ballCenter - paddleCenter;

        int maxSpeed = 10;

        int speed = Math.min(Math.abs(distance), maxSpeed);

        int direction = Integer.compare(distance, 0);

        rightPaddleY += speed * direction;

        if (rightPaddleY < LEFT_TOP_Y) {
            rightPaddleY = LEFT_TOP_Y;
        }

        if (rightPaddleY + PADDLE_HEIGHT > RIGHT_BOT_Y) {
            rightPaddleY = RIGHT_BOT_Y - PADDLE_HEIGHT;
        }
    }

    public void draw(Graphics2D g) {
        g.setColor(Utils.getBlackColor());
        g.fillRect(0, 0, W, H);
        g.setColor(Utils.getRedColor());
        g.drawRect(LEFT_TOP_X, LEFT_TOP_Y, W - 20, H - 20);
        g.setColor(Utils.getGreenColor());
        g.fillOval((int) ballX, (int) ballY, DIAMETER_BALL, DIAMETER_BALL);
        g.setColor(Utils.getBlueColor());
        g.fillRect(LEFT_TOP_X, (int) leftPaddleY, PADDLE_WIDTH, PADDLE_HEIGHT);
        g.setColor(Utils.getYellowColor());
        g.fillRect(RIGHT_BOT_X - PADDLE_WIDTH, (int) rightPaddleY, PADDLE_WIDTH, PADDLE_HEIGHT);
    }

    private void showEndGameDialog(String message, boolean showNextLevelButton) {
        EndGameDialog endGameDialog = new EndGameDialog((JFrame) SwingUtilities.getWindowAncestor(this), message);
        endGameDialog.nextLevelButton.setVisible(showNextLevelButton);
        endGameDialog.setVisible(true);

        if (endGameDialog.isPlayAgain()) {
            restartGame();
        }
    }

    private void restartGame() {
        leftScore = 0;
        rightScore = 0;
        resetBall();
        gameOver = false;
        gamePaused = false;
    }

    private void resetBall() {
        Random random = new Random();
        ballX = random.nextInt(Game.W - Game.DIAMETER_BALL);
        ballY = random.nextInt(Game.H - Game.DIAMETER_BALL);
        ballDirectionX = random.nextBoolean() ? 1 : -1;
        ballDirectionY = random.nextBoolean() ? 1 : -1;
        normalizeBallDirection();
    }

    public void checkCollision() {
        
        if (ballX <= LEFT_TOP_X) {
            rightScore++; 
            if (rightScore >= WINNING_SCORE) {
                gameOver = true;
                showEndGameDialog("Правий гравець виграв!", false);
            } else {
                resetBall();
            }
            return; 
        }

       
        if (ballY <= LEFT_TOP_Y) {
            ballY = LEFT_TOP_Y;
            ballDirectionY *= -1;
            normalizeBallDirection();
        }

        
        if (ballX + DIAMETER_BALL >= RIGHT_BOT_X) {
            leftScore++; 
            if (leftScore >= WINNING_SCORE) {
                gameOver = true;
                showEndGameDialog("Лівий гравець виграв!", true); 
            } else {
                resetBall(); 
            }
            return; 
        }

  
        if (ballY + DIAMETER_BALL > RIGHT_BOT_Y) {
            ballY = RIGHT_BOT_Y - DIAMETER_BALL;
            ballDirectionY *= -1;
            normalizeBallDirection();
        }

      
        if (ballX <= LEFT_TOP_X + PADDLE_WIDTH && ballY + DIAMETER_BALL >= leftPaddleY
                && ballY <= leftPaddleY + PADDLE_HEIGHT) {
            ballX = LEFT_TOP_X + PADDLE_WIDTH;
            ballDirectionX *= -1;
            normalizeBallDirection();
        }

        
        if (ballX + DIAMETER_BALL >= RIGHT_BOT_X - PADDLE_WIDTH && ballY + DIAMETER_BALL >= rightPaddleY
                && ballY <= rightPaddleY + PADDLE_HEIGHT) {
            ballX = RIGHT_BOT_X - PADDLE_WIDTH - DIAMETER_BALL;
            ballDirectionX *= -1;
            normalizeBallDirection();
        }
    }

    private void togglePause() {
        gamePaused = !gamePaused; 
    }
}