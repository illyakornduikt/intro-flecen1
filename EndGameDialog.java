package game;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;


class EndGameDialog extends JDialog {
    private boolean playAgain = false;
    private boolean goToNextLevel = false;
    JButton nextLevelButton;

    public EndGameDialog(JFrame parent, String message) {
        super(parent, "Гра закінчена", true);
        super.setBackground(Utils.getBlackColor()); 
    	super.setForeground(Utils.getWhiteColor());
    	super.setFont(Utils.getFont()); 
        JPanel panel = new JPanel();
        JButton playAgainButton = new JButton("Restart");
        JButton exitButton = new JButton("Exit");
        nextLevelButton = new JButton("Next level");
        
        panel.setBackground(Utils.getBlackColor());
        
        playAgainButton.setBackground(Utils.getBlackColor());
        playAgainButton.setForeground(Utils.getWhiteColor()); 
        playAgainButton.setFont(Utils.getFont()); 
        
        exitButton.setBackground(Utils.getBlackColor()); 
        exitButton.setForeground(Utils.getWhiteColor());
        exitButton.setFont(Utils.getFont()); 
        
        nextLevelButton.setBackground(Utils.getBlackColor());
        nextLevelButton.setForeground(Utils.getWhiteColor());
        nextLevelButton.setFont(Utils.getFont());
        
        playAgainButton.addActionListener(e -> {
            playAgain = true;
            setVisible(false);
        });

        exitButton.addActionListener(e -> {
            System.exit(0);
        });

        addNextLevelButton();

        panel.add(playAgainButton);
        panel.add(nextLevelButton);
        panel.add(exitButton);
        add(panel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
    }

    public boolean isPlayAgain() {
        return playAgain;
    }

    public boolean isGoToNextLevel() {
        return goToNextLevel;
    }

    private void addNextLevelButton() {
        nextLevelButton.addActionListener(e -> {
            goToNextLevel = true;
            setVisible(false);
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.getContentPane().removeAll();
            frame.add(new GameTwoLvl());
            frame.revalidate();
        });
    }
}