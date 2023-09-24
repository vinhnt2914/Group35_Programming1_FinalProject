package portmanagementsystem.gui;

import portmanagementsystem.DataManager;
import portmanagementsystem.models.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class AddContainersToVehicle{
    private JButton addContainerButton;
    private JLabel vehicleIDLabel;
    private JLabel vehicleNameLabel;
    private JLabel vehicleTypeLabel;
    private JLabel currentFuelLabel;
    private JList<Container> listView;
    private JPanel mainPanel;
    private DefaultListModel<Container> containerListModel;

    public AddContainersToVehicle(Vehicle vehicle) {
        // Call load function
        load(vehicle);

        // Function for load container button
        addContainerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if loadContainer function is true or false. If true remove container from listView
                if (vehicle.loadContainer(listView.getSelectedValue())) {
                    containerListModel.removeElement(listView.getSelectedValue());
                    load(vehicle);
                }
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    // Load data into listView and labels
    public void load(Vehicle vehicle) {
        containerListModel = new DefaultListModel<>();
        DataManager.getContainerList().forEach(container -> {
            if (Objects.equals(container.getCurrentPort(), vehicle.getCurrentPort())) {
                containerListModel.addElement(container);
            }
        });

        listView.setModel(containerListModel);
        System.out.println("Containers read successfully");

        vehicleIDLabel.setText("Vehicle ID: " + vehicle.getId());
        vehicleNameLabel.setText("Vehicle Name: " + vehicle.getName());
        vehicleTypeLabel.setText("Vehicle Type: " + vehicle.getVehicleType());
        currentFuelLabel.setText("Remain Capacity: " + vehicle.getRemainingCapacity());
    }
}
