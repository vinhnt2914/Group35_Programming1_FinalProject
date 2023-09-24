package portmanagementsystem.gui;

import portmanagementsystem.DataManager;
import portmanagementsystem.models.Port;
import portmanagementsystem.models.Ship;
import portmanagementsystem.models.Vehicle;

import javax.swing.*;

public class ListShipsInPort implements PanelAction{
    private JPanel mainPanel;
    private JList<String> listView;
    private DefaultListModel<String> portListModel;

    public ListShipsInPort() {
        load();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    // Load data into list View
    @Override
    public void load() {
        portListModel = new DefaultListModel<>();

        // Loop over each port
        for (Port port : DataManager.getPortList()) {
            StringBuilder shipsInPort = new StringBuilder();
            shipsInPort.append("Port ").append(port.getId()).append(": ");

            boolean isFirstShip = true;

            for (Vehicle vehicle : port.getListOfVehicles()) {
                // Check if the vehicle is a ship
                if (vehicle instanceof Ship) {
                    if (!isFirstShip) {
                        shipsInPort.append(", ");
                    }
                    // append ship ID into the string
                    shipsInPort.append(vehicle.getId());
                    isFirstShip = false;
                }
            }
            portListModel.addElement(shipsInPort.toString());
        }
        listView.setModel(portListModel);
    }

}
