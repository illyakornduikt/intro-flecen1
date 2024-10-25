package game;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.Timer;

class StarsPanel extends JPanel {

    private final int width;
    private final int height;
    private final Star[] stars;

    public StarsPanel(int width, int height) {
        this.width = width;
        this.height = height;
        this.stars = new Star[5500];
        setBackground(Utils.getBlackColor());
        initializeStars();
        startAnimation();
    }

    private void initializeStars() {
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(width, height);
        }
    }

    private void startAnimation() {
        Timer timer = new Timer(1, e -> {
            for (Star star : stars) {
                star.update();
            }
            repaint();
        });
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D canvas = (Graphics2D) g;
        for (Star star : stars) {
            star.show(canvas);
        }
    }
}