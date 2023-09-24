package portmanagementsystem.gui;

import portmanagementsystem.DataManager;
import portmanagementsystem.models.Manager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class UpdateManager {
    private JTextField usernameField; // Text field for entering the updated username
    private JTextField passwordField; // Text field for entering the updated password
    private JButton updateManagerButton; // Button for initiating the manager update
    private JPanel mainPanel; // Main panel containing the update manager UI components

    // Constructor for the UpdateManager class
    public UpdateManager(Manager manager) {
        load(manager); // Load manager data into the form fields

        // ActionListener for the updateManagerButton
        updateManagerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText(); // Get the updated username from the text field
                String password = passwordField.getText(); // Get the updated password from the text field
                boolean validUsername = true;

                // Check if the updated username is unique among managers
                for (Manager manager : DataManager.getManagerList()) {
                    if (manager.getUsername().equals(username))
                        validUsername = false;
                }

                // If the username is unique, update the manager's data
                if (validUsername) {
                    manager.setUsername(username); // Set the updated username
                    manager.setPassword(password); // Set the updated password
                    DataManager.updateManager(manager); // Update the manager in the data store
                    JOptionPane.showMessageDialog(null, "Manager updated!", "Success", JOptionPane.INFORMATION_MESSAGE); // Show success message
                } else {
                    // If the username is not unique, show an error message
                    JOptionPane.showMessageDialog(null, "Username is already in use!", "Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // Load the manager's data into the form fields
    private void load(Manager manager) {
        usernameField.setText(manager.getUsername()); // Set the username field with the current username
        passwordField.setText(manager.getPassword()); // Set the password field with the current password
    }

    // Get the main panel containing the update manager UI
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
