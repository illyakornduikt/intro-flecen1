package game;

import javax.swing.*;
import java.awt.*;

public class GameSelectionFrame extends JFrame {

	public GameSelectionFrame() {
		setTitle("Game Selection");
		setSize(1100, 815);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel backgroundPanel = new JPanel();
		backgroundPanel.setLayout(null);
		backgroundPanel.setBackground(Utils.getBlackColor());

		JLabel titleLabel = new JLabel("Select a Game", SwingConstants.CENTER);
		titleLabel.setBounds(300, 10, 500, 50);
		titleLabel.setFont(Utils.getFont());
		titleLabel.setForeground(Utils.getWhiteColor());

		JButton pingPongButton = new JButton("PING PONG");
		pingPongButton.setBounds(50, 100, 200, 200);
		pingPongButton.setBackground(Utils.getBlackColor());
		pingPongButton.setForeground(Utils.getWhiteColor());
		pingPongButton.setFont(Utils.getFont());
		pingPongButton.setBorder(BorderFactory.createLineBorder(Utils.getWhiteColor(), 2));
		pingPongButton.setFocusPainted(false);
		pingPongButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		pingPongButton.addActionListener(e -> startPingPongGame());
		pingPongButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				pingPongButton.setBackground(Utils.getWhiteColor());
				pingPongButton.setForeground(Utils.getBlackColor());
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				pingPongButton.setBackground(Utils.getBlackColor());
				pingPongButton.setForeground(Utils.getWhiteColor());
			}
		});

		backgroundPanel.add(titleLabel);
		backgroundPanel.add(pingPongButton);

		add(backgroundPanel);

		setVisible(true);
	}

	private void startPingPongGame() {
		
		getContentPane().removeAll();
		setLayout(new BorderLayout());
		Game game = new Game();
		add(game, BorderLayout.CENTER);
		revalidate();
		game.requestFocusInWindow();
	}
}
