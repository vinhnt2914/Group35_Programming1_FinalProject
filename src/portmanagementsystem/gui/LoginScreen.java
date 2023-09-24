package portmanagementsystem.gui;

import portmanagementsystem.DataManager;
import portmanagementsystem.models.Manager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginScreen {
    private JPanel mainPanel;
    private JTextField usernameField;
    private JButton logInButton;
    private JPasswordField passwordField;

    public LoginScreen() {
        // Load managers into database
        try {
            DataManager.readManagers();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();

                if (username.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Username cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Password cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    boolean found = false; // Flag to track if a matching account is found

                    // Check if is admin
                    if (username.equals("admin") && password.equals("123456")) {
                        System.out.println("Log in successfully");
                        LoginFrame.getMainFrame().dispose();
                        new AppFrame(new AdminMenuBar());
                        found = true;
                        // Check if is manager
                    } else {
                        for (Manager manager : DataManager.getManagerList()) {
                            if (manager.getUsername().equals(username) && manager.getPassword().equals(password)) {
                                LoginFrame.getMainFrame().dispose();
                                new AppFrame(new ManagerMenuBar(manager));
                                found = true;
                                break; // No need to continue searching
                            }
                        }
                    }

                    // If account is not found, alert the user
                    if (!found) {
                        JOptionPane.showMessageDialog(null, "Account doesn't exist or credentials are incorrect.", "Login Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }


    public JPanel getMainPanel() {
        return mainPanel;
    }
}
