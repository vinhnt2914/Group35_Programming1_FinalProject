package portmanagementsystem.gui;

import portmanagementsystem.DataManager;
import portmanagementsystem.models.Port;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AddPort {
    private JTextField portNameField;
    private JTextField portLatitudeField;
    private JTextField portLongtitudeField;
    private JTextField portStoringField;
    private JComboBox<Boolean> portLandingBox;
    private JButton addPortButton;
    private JPanel mainPanel;

    public AddPort() {
        // Add true and false options to the portLandingBox combo box
        portLandingBox.addItem(true);
        portLandingBox.addItem(false);

        // Action Listener for addPort button
        addPortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if there are any empty fields
                if (portNameField.getText().isEmpty() || portLatitudeField.getText().isEmpty() ||
                        portLongtitudeField.getText().isEmpty() || portStoringField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Retrieve input values from the fields
                    String portName = portNameField.getText();
                    double portLatitude;
                    double portLongitude;
                    double portStoring;

                    // Validate user input for latitude, longitude, and port storing
                    try {
                        portLatitude = Double.parseDouble(portLatitudeField.getText());
                    } catch (NumberFormatException exception) {
                        JOptionPane.showMessageDialog(null, "Invalid input for Port Latitude, must be a number", "Error", JOptionPane.ERROR_MESSAGE);
                        return; // Exit the method early to prevent further processing
                    }

                    try {
                        portLongitude = Double.parseDouble(portLongtitudeField.getText());
                    } catch (NumberFormatException exception) {
                        JOptionPane.showMessageDialog(null, "Invalid input for Port Longitude, must be a number", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    try {
                        portStoring = Double.parseDouble(portStoringField.getText());
                    } catch (NumberFormatException exception) {
                        JOptionPane.showMessageDialog(null, "Invalid input for Port Storing, must be a number", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Validate if latitude and longitude are within valid ranges
                    try {
                        if (portLatitude > 90 || portLatitude < -90) {
                            JOptionPane.showMessageDialog(null, "Latitude value is not valid", "Error", JOptionPane.ERROR_MESSAGE);
                        } else if (portLongitude > 180 || portLongitude < -180) {
                            JOptionPane.showMessageDialog(null, "Longitude value is not valid", "Error", JOptionPane.ERROR_MESSAGE);
                        } else if (portStoring <= 0) {
                            JOptionPane.showMessageDialog(null, "Port storing cannot be 0 or smaller", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            // Create a new Port object with the provided data
                            boolean portLanding = (boolean) portLandingBox.getSelectedItem();
                            Port port = new Port(portName, portLatitude, portLongitude, portStoring, portLanding);

                            // Add the new Port to the list and write it to storage
                            DataManager.getPortList().add(port);
                            DataManager.writePorts();

                            // Show a success message
                            JOptionPane.showMessageDialog(null, "Port created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (IOException ex) {
                        System.out.println("Port write unsuccessfully!");
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }

    // Get the main panel associated with this class
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
