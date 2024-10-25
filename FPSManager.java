package game;

public class FPSManager {
    private long lastTime;
    private long frameCount;
    private long lastFPSCheck;
    private int fps;
    private static final int TARGET_FPS = 60;
    private static final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

    public FPSManager() {
        lastTime = System.nanoTime();
        frameCount = 0;
        lastFPSCheck = System.currentTimeMillis();
    }

    public void update() {
        long now = System.nanoTime();
        lastTime = now;

        frameCount++;

        // Check FPS
        if (System.currentTimeMillis() - lastFPSCheck >= 1000) {
            fps = (int) frameCount;
            frameCount = 0;
            lastFPSCheck = System.currentTimeMillis();
        }

        // Sleep logic for target FPS
        long sleepTime = (lastTime - now + OPTIMAL_TIME) / 1000000;
        if (sleepTime > 0) {
            try {
                Thread.sleep(sleepTime); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getFPS() {
        return fps;
    }
}
