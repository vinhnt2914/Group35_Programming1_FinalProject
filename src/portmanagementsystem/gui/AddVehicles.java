package portmanagementsystem.gui;

import portmanagementsystem.DataManager;
import portmanagementsystem.models.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

public class AddVehicles {
    private JComboBox<String> vehicleTypeBox;
    private JTextField vehicleNameField;
    private JTextField vehicleCapacityField;
    private JTextField vehicleFuelField;
    private JButton createButton;
    private JPanel mainPanel;
    private JList<Port> listView;
    private DefaultListModel<Port> portListModel;

    public AddVehicles() throws IOException {
        // Load ports and initialize the form
        load();

        // Action Listener for createButton
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if any text field is empty
                if (vehicleNameField.getText().isEmpty() || vehicleCapacityField.getText().isEmpty() || vehicleFuelField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    // Check if the user hasn't selected a port
                } else if (Objects.isNull(listView.getSelectedValue())) {
                    JOptionPane.showMessageDialog(null, "Please select the port for this vehicle", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Retrieve data from all fields to create a vehicle and add it to the database
                    String vehicleName = vehicleNameField.getText();
                    double vehicleCapacity;
                    double vehicleFuel;

                    // Check if user input is valid for capacity field
                    try {
                        vehicleCapacity = Double.parseDouble(vehicleCapacityField.getText());
                    } catch (NumberFormatException exception) {
                        JOptionPane.showMessageDialog(null, "Invalid input for Capacity, must be a number", "Error", JOptionPane.ERROR_MESSAGE);
                        return; // Exit the method early to prevent further processing
                    }

                    // Check if user input is valid for fuel field
                    try {
                        vehicleFuel = Double.parseDouble(vehicleFuelField.getText());
                    } catch (NumberFormatException exception) {
                        JOptionPane.showMessageDialog(null, "Invalid input for Fuel, must be a number", "Error", JOptionPane.ERROR_MESSAGE);
                        return; // Exit the method early to prevent further processing
                    }

                    if (vehicleCapacity <= 0) {
                        JOptionPane.showMessageDialog(null, "Vehicle carrying capacity cannot be 0 or smaller", "Error", JOptionPane.ERROR_MESSAGE);
                    } else if (vehicleFuel <= 0) {
                        JOptionPane.showMessageDialog(null, "Vehicle fuel capacity cannot be 0 or smaller", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        String vehicleType = (String) vehicleTypeBox.getSelectedItem();
                        Port port = listView.getSelectedValue();

                        // Check if the selected port supports the selected vehicle type
                        if ((!vehicleType.equals("SHIP")) && !port.isLandable()) {
                            JOptionPane.showMessageDialog(null, "This port doesn't support landing for trucks, please select another one!", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            // Create a vehicle of the selected type
                            Vehicle vehicle = createVehicleOfType(vehicleType, vehicleName, vehicleCapacity, vehicleFuel, port);

                            // Add the vehicle to the database and update the port
                            DataManager.getVehicleList().add(vehicle);
                            port.addNumberOfVehicle(1);
                            port.getListOfVehicles().add(vehicle);
                            DataManager.updatePort(port);
                            try {
                                DataManager.writeVehicles();
                                JOptionPane.showMessageDialog(null, "Vehicle created!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            } catch (IOException ex) {
                                System.out.println("Vehicle write unsuccessfully");
                                throw new RuntimeException(ex);
                            }
                        }
                    }
                }
            }
        });
    }

    // Create a vehicle of the specified type
    private Vehicle createVehicleOfType(String type, String vehicleName, double vehicleCapacity, double vehicleFuel, Port currentPort) {
        switch (type) {
            case "SHIP":
                return new Ship(vehicleName, vehicleCapacity, vehicleFuel, currentPort);
            case "BASIC TRUCK":
                return new BasicTruck(vehicleName, vehicleCapacity, vehicleFuel, currentPort);
            case "REEFER TRUCK":
                return new ReeferTruck(vehicleName, vehicleCapacity, vehicleFuel, currentPort);
            case "TANKER TRUCK":
                return new TankerTruck(vehicleName, vehicleCapacity, vehicleFuel, currentPort);
            default:
                throw new IllegalArgumentException("Invalid vehicle type: " + type);
        }
    }

    // Load vehicle types and available ports
    public void load() {
        vehicleTypeBox.addItem("SHIP");
        vehicleTypeBox.addItem("BASIC TRUCK");
        vehicleTypeBox.addItem("REEFER TRUCK");
        vehicleTypeBox.addItem("TANKER TRUCK");

        portListModel = new DefaultListModel<>();
        portListModel.addAll(DataManager.getPortList());
        listView.setModel(portListModel);
    }

    // Get the main panel associated with this class
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
