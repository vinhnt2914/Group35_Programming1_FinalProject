package portmanagementsystem.gui;

import portmanagementsystem.models.Container;
import portmanagementsystem.models.Port;
import portmanagementsystem.models.Vehicle;

import javax.swing.*;

public class MyPort {
    private JList<Container> containerListView;
    private JList<Vehicle> vehicleListView;
    private JLabel idLabel;
    private JLabel nameLabel;
    private JLabel latitudeLabel;
    private JLabel longitudeLabel;
    private JLabel capacityLabel;
    private JLabel currentCapacityLabel;
    private JLabel numOfContainerLabel;
    private JLabel numOfVehicleLabel;
    private JPanel mainPanel;
    private DefaultListModel<Container> containerListModel;
    private DefaultListModel<Vehicle> vehicleListModel;

    public MyPort(Port port) {
        // Load port details and associated containers and vehicles
        load(port);
    }

    private void load(Port port) {
        // Set labels with port details
        idLabel.setText("Port ID: " + port.getId());
        nameLabel.setText("Port name: " + port.getName());
        latitudeLabel.setText("Port latitude: " + port.getLatitude());
        longitudeLabel.setText("Port longitude: " + port.getLongitude());
        capacityLabel.setText("Port capacity: " + port.getStoringCapacity());
        currentCapacityLabel.setText("Port remaining capacity: " + port.getRemainingCapacity());
        numOfContainerLabel.setText("Number of containers: " + port.getNumberOfContainers());
        numOfVehicleLabel.setText("Number of vehicles: " + port.getNumberOfVehicles());

        // Load containers and vehicles associated with the port
        containerListModel = new DefaultListModel<>();
        vehicleListModel = new DefaultListModel<>();
        containerListModel.addAll(port.getListOfContainers());
        vehicleListModel.addAll(port.getListOfVehicles());
        containerListView.setModel(containerListModel);
        vehicleListView.setModel(vehicleListModel);
    }

    // Get the main panel associated with this class
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
