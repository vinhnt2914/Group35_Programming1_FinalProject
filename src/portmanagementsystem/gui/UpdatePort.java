package portmanagementsystem.gui;

import portmanagementsystem.DataManager;
import portmanagementsystem.models.Port;
import portmanagementsystem.models.Vehicle;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class UpdatePort {
    private JTextField nameField; // Text field for entering the updated port name
    private JTextField latitudeField; // Text field for entering the updated latitude
    private JTextField longitudeField; // Text field for entering the updated longitude
    private JTextField storingField; // Text field for entering the updated storing capacity
    private JComboBox<Boolean> landingBox; // Combo box for selecting the updated landing ability
    private JPanel mainPanel; // Main panel containing the update port UI components
    private JButton updatePortButton; // Button for initiating the port update

    // Constructor for the UpdatePort class
    public UpdatePort(Port port) {
        landingBox.addItem(true); // Add "true" option to the landing ability combo box
        landingBox.addItem(false); // Add "false" option to the landing ability combo box
        load(port); // Load port data into the form fields

        // ActionListener for the updatePortButton
        updatePortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nameField.getText().isEmpty() || latitudeField.getText().isEmpty() ||
                        longitudeField.getText().isEmpty() || storingField.getText().isEmpty()) {
                    // Check if any of the required fields is empty and show an error message
                    JOptionPane.showMessageDialog(null, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String portName = nameField.getText(); // Get the updated port name from the text field
                    double portLatitude;
                    double portLongitude;
                    double portStoring;

                    // Validate user input for latitude, longitude, and storing capacity
                    try {
                        portLatitude = Double.parseDouble(latitudeField.getText());
                    } catch (NumberFormatException exception) {
                        JOptionPane.showMessageDialog(null, "Invalid input for Port Latitude, must be a number", "Error", JOptionPane.ERROR_MESSAGE);
                        return; // Exit the method early to prevent further processing
                    }

                    try {
                        portLongitude = Double.parseDouble(longitudeField.getText());
                    } catch (NumberFormatException exception) {
                        JOptionPane.showMessageDialog(null, "Invalid input for Port Longitude, must be a number", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    try {
                        portStoring = Double.parseDouble(storingField.getText());
                    } catch (NumberFormatException exception) {
                        JOptionPane.showMessageDialog(null, "Invalid input for Port Storing, must be a number", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Validate if latitude and longitude are within valid ranges
                    if (portLatitude > 90 || portLatitude < -90) {
                        JOptionPane.showMessageDialog(null, "Latitude value is not valid", "Error", JOptionPane.ERROR_MESSAGE);
                    } else if (portLongitude > 180 || portLongitude < -180) {
                        JOptionPane.showMessageDialog(null, "Longitude value is not valid", "Error", JOptionPane.ERROR_MESSAGE);
                    } if (portStoring <= 0) {
                        JOptionPane.showMessageDialog(null, "Port storing must be greater than 0", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        boolean portLanding = (boolean) landingBox.getSelectedItem();
                        boolean ableToUpdate = true;

                        if (!portLanding) {
                            // Check if the landing ability is being set to false and there are trucks on the port
                            for (Vehicle vehicle : port.getListOfVehicles()) {
                                if (!vehicle.getVehicleType().equals("SHIP")) {
                                    ableToUpdate = false;
                                    JOptionPane.showMessageDialog(null, "Cannot change the port landing ability to false, there are trucks on the port", "Error", JOptionPane.ERROR_MESSAGE);
                                    break;
                                }
                            }
                        }

                        if (ableToUpdate) {
                            // If all validations passed, update the port's data
                            port.setName(portName);
                            port.setLatitude(portLatitude);
                            port.setLongitude(portLongitude);
                            port.setLandingAbility(portLanding);

                            // If the new storing capacity is greater than the old capacity, update remaining capacity
                            if (portStoring > port.getStoringCapacity()) {
                                port.addRemainingCapacity(portStoring - port.getStoringCapacity());
                                port.setStoringCapacity(portStoring);
                            } else {
                                port.setStoringCapacity(portStoring);
                                port.setRemainingCapacity(portStoring);
                            }

                            // Update the port and related data in the data manager
                            DataManager.updatePort(port);
                            DataManager.getContainerList().forEach(DataManager::updateContainer);
                            DataManager.getVehicleList().forEach(DataManager::updateVehicle);
                            DataManager.getManagerList().forEach(DataManager::updateManager);

                            JOptionPane.showMessageDialog(null, "Port updated!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            }
        });
    }

    // Load the port's data into the form fields
    public void load(Port port) {
        nameField.setText(port.getName());
        latitudeField.setText(String.valueOf(port.getLatitude()));
        longitudeField.setText(String.valueOf(port.getLongitude()));
        storingField.setText(String.valueOf(port.getStoringCapacity()));
        landingBox.setSelectedItem(port.isLandable());
    }

    // Get the main panel containing the update port UI
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
