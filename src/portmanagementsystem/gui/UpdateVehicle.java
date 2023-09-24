package portmanagementsystem.gui;

import portmanagementsystem.DataManager;
import portmanagementsystem.models.Vehicle;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class UpdateVehicle{
    private JTextField nameField;
    private JTextField capacityField;
    private JTextField fuelField;
    private JComboBox<String> typeBox;
    private JButton updateVehicleButton;
    private JPanel mainPanel;

    public UpdateVehicle(Vehicle vehicle) {
        load(vehicle);
        updateVehicleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nameField.getText().isEmpty() || capacityField.getText().isEmpty() || fuelField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all field", "Error", JOptionPane.ERROR_MESSAGE);
                    // Check if user hasn't select a port
                } else {
                    // Retrieve data from all fields to create a vehicle and add it into the database
                    String vehicleName = nameField.getText();
                    double vehicleCapacity;
                    double vehicleFuel;
                    // Check if user input is valid for capacity field
                    try {
                        vehicleCapacity = Double.parseDouble(capacityField.getText());
                    } catch (NumberFormatException exception) {
                        JOptionPane.showMessageDialog(null, "Invalid input for Capacity, must be a number", "Error", JOptionPane.ERROR_MESSAGE);
                        return; // Exit the method early to prevent further processing
                    }
                    // Check if user input is valid for fuel field
                    try {
                        vehicleFuel = Double.parseDouble(fuelField.getText());
                    } catch (NumberFormatException exception) {
                        JOptionPane.showMessageDialog(null, "Invalid input for Fuel, must be a number", "Error", JOptionPane.ERROR_MESSAGE);
                        return; // Exit the method early to prevent further processing
                    }

                    // Check if vehicle capacity and fuel is smaller than 0
                    if (vehicleCapacity <= 0) {
                        JOptionPane.showMessageDialog(null, "Vehicle capacity must be greater than 0", "Error", JOptionPane.ERROR_MESSAGE);
                    } else if (vehicleFuel <= 0) {
                        JOptionPane.showMessageDialog(null, "Vehicle fuel must be greater than 0", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // get vehicle type
                        String vehicleType = (String) typeBox.getSelectedItem();
                        // check if the port is landable
                        if ((!vehicleType.equals("SHIP")) && !vehicle.getCurrentPort().isLandable()) {
                            JOptionPane.showMessageDialog(null, "Cannot change to this vehicle type, the current port does not support it!", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            vehicle.setName(vehicleName);
                            vehicle.setVehicleType(vehicleType);
                            // Calculate and update remaining capacity and fuel
                            if (vehicleCapacity > vehicle.getCarryingCapacity()) {
                                vehicle.addRemainingCapacity(vehicleCapacity - vehicle.getRemainingCapacity());
                                vehicle.setCarryingCapacity(vehicleCapacity);
                            } else {
                                vehicle.setRemainingCapacity(vehicleCapacity);
                                vehicle.setCarryingCapacity(vehicleCapacity);
                            }

                            if (vehicleFuel > vehicle.getFuelCapacity()) {
                                vehicle.addCurrentFuel(vehicleFuel - vehicle.getFuelCapacity());
                                vehicle.setFuelCapacity(vehicleFuel);
                            } else {
                                vehicle.setCurrentFuel(vehicleFuel);
                                vehicle.setFuelCapacity(vehicleFuel);
                            }

                            // Add vehicle into database
                            DataManager.updateVehicle(vehicle);
                            JOptionPane.showMessageDialog(null, "Vehicle updated!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }


                }
            }
        });
    }

    public void load(Vehicle vehicle) {
        // Add item into the type box
        typeBox.addItem("SHIP");
        typeBox.addItem("BASIC TRUCK");
        typeBox.addItem("REEFER TRUCK");
        typeBox.addItem("TANKER TRUCK");

        // Set placeholder value for field
        nameField.setText(vehicle.getName());
        capacityField.setText(String.valueOf(vehicle.getCarryingCapacity()));
        fuelField.setText(String.valueOf(vehicle.getFuelCapacity()));
        typeBox.setSelectedItem(vehicle.getVehicleType());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
