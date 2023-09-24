package portmanagementsystem.gui;

import portmanagementsystem.DataManager;
import portmanagementsystem.models.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class MoveToPort{
    private JList<Port> listView;
    private JButton moveToPortButton;
    private JPanel mainPanel;
    private JLabel vehicleIDLabel;
    private JLabel vehicleNameLabel;
    private JLabel vehicleTypeLabel;
    private JLabel currentFieldLabel;
    private JLabel portIDLabel;
    private JLabel portNameLabel;

    public MoveToPort(Vehicle vehicle){
        load(vehicle);
        // Function for move to port button
        moveToPortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Objects.isNull(listView.getSelectedValue())) {
                    JOptionPane.showMessageDialog(null, "Please select a port first!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    vehicle.moveToPort(listView.getSelectedValue());
                    load(vehicle);
                }
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void load(Vehicle vehicle) {
        // Load data into listView and labels
        DefaultListModel<Port> portListModel = new DefaultListModel<>();
        portListModel.addAll(DataManager.getPortList());
        listView.setModel(portListModel);

        vehicleIDLabel.setText("Vehicle ID: " + vehicle.getId());
        vehicleNameLabel.setText("Vehicle Name: " + vehicle.getName());
        vehicleTypeLabel.setText("Vehicle Type: " + vehicle.getVehicleType());
        currentFieldLabel.setText("Current Field: " + vehicle.getCurrentFuel());
        portIDLabel.setText("Port ID: " + vehicle.getCurrentPort().getId());
        portNameLabel.setText("Port Name: " + vehicle.getCurrentPort().getName());
    }
}
