package game;

import javax.swing.*;
//Work Petrenco Yaroslav KND-21
public class App {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Меню гри");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 815);
        frame.setLocationRelativeTo(null);
        StarsPanel starsPanel = new StarsPanel(600, 600);
        starsPanel.setLayout(null);
        frame.add(starsPanel);
        JButton startButton = new JButton("Start");
        startButton.setBounds(450, 700, 150, 50);
        startButton.setBackground(Utils.getBlackColor());
        startButton.setForeground(Utils.getWhiteColor());
        startButton.setFont(Utils.getFont());
        startButton.addActionListener(e -> {
            frame.getContentPane().removeAll();
            frame.add(new LoginFrame());
            frame.add(starsPanel);
            frame.revalidate();
        });
        starsPanel.add(startButton);

        try {
            SoundPlayer soundPlayer = new SoundPlayer("test.wav");
            soundPlayer.play();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        frame.setVisible(true);
    }
}