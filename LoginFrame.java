package game;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame extends JPanel {
    private static final String USER_FILE = "users.txt";
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPanel panel;
    private GridBagConstraints gbc;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JButton loginButton;
    private JButton registerButton;
    private Map<String, String> users;

    public LoginFrame() {
        frame = new JFrame("Login");
        panel = new JPanel(new GridBagLayout());
        panel.setBackground(Utils.getBlackColor());
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(Utils.getPixel(), Utils.getPixel(), Utils.getPixel(), Utils.getPixel());

        usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Utils.getWhiteColor());
        usernameLabel.setFont(Utils.getFont());

        passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Utils.getWhiteColor());
        passwordLabel.setFont(Utils.getFont());

        usernameField = new JTextField(Utils.getNumber());
        usernameField.setBackground(Utils.getBlackColor());
        usernameField.setForeground(Utils.getWhiteColor());
        usernameField.setFont(Utils.getFont());

        passwordField = new JPasswordField(Utils.getNumber());
        passwordField.setBackground(Utils.getBlackColor());
        passwordField.setForeground(Utils.getWhiteColor());
        passwordField.setFont(Utils.getFont());

        loginButton = new JButton("Login");
        loginButton.setBackground(Utils.getBlackColor());
        loginButton.setForeground(Utils.getWhiteColor());
        loginButton.setFont(Utils.getFont());

        registerButton = new JButton("Register");
        registerButton.setBackground(Utils.getBlackColor());
        registerButton.setForeground(Utils.getWhiteColor());
        registerButton.setFont(Utils.getFont());

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(registerButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(loginButton, gbc);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        users = new HashMap<>();
        loadUsersFromFile();

        loginButton.addActionListener(e -> {
            login();
        });

        registerButton.addActionListener(e -> {
            register();
        });
    }

    private void loadUsersFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String username = parts[0];
                    String password = parts[1];
                    users.put(username, password);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void login() {
        String enteredUsername = usernameField.getText();
        String enteredPassword = new String(passwordField.getPassword());

        if (users.containsKey(enteredUsername) && users.get(enteredUsername).equals(enteredPassword)) {
            frame.dispose();
            new GameSelectionFrame(); 
        } else {
            JOptionPane.showMessageDialog(frame, "Неверное имя пользователя или пароль. Попробуйте снова.");
        }
    }

    private void register() {
        String newUsername = usernameField.getText();
        String newPassword = new String(passwordField.getPassword());

        if (newUsername.isEmpty() || newPassword.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Имя пользователя или пароль не могут быть пустыми.");
            return;
        }

        if (users.containsKey(newUsername)) {
            JOptionPane.showMessageDialog(frame, "Пользователь с таким именем уже существует.");
        } else {
            users.put(newUsername, newPassword);
            saveUserToFile(newUsername, newPassword);
            JOptionPane.showMessageDialog(frame, "Пользователь успешно зарегистрирован!");
        }
    }

    private void saveUserToFile(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE, true))) {
            writer.write(username + "," + password);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
