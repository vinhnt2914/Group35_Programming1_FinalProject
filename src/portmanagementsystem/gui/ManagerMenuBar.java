package portmanagementsystem.gui;

import portmanagementsystem.DataManager;
import portmanagementsystem.models.Manager;
import portmanagementsystem.models.Port;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ManagerMenuBar extends MenuBarFrame{
    private JMenuBar menuBar;

    public ManagerMenuBar(Manager manager) {
        createMenuBar(manager.getControlPort());
    }

    // Create menu bar for port managers, this is similar to admin, but with less function and port-related
    private void createMenuBar(Port controlPort) {
        menuBar = new JMenuBar();

        JMenu containers = new JMenu("Containers");
        JMenu vehicles = new JMenu("Vehicles");
        JMenu port = new JMenu("Port");
        JMenu trips = new JMenu("Trips");
        JMenu statistics = new JMenu("Statistics");

        JMenuItem viewContainers = new JMenuItem("View Containers");
        JMenuItem addContainers = new JMenuItem("Add Containers");
        JMenuItem updateContainers = new JMenuItem("Update Containers");
        JMenuItem viewVehicles = new JMenuItem("View Vehicles");
        JMenuItem updateVehicles = new JMenuItem("Update Vehicles");
        JMenuItem myPort = new JMenuItem("My Port");
        JMenuItem updatePort = new JMenuItem("Update Port");
        JMenuItem viewTrips = new JMenuItem("View Trips");
        JMenuItem calculateContainersWeight = new JMenuItem("Calculate Containers Weight");
        JMenuItem fuelUsagePerDay = new JMenuItem("Fuel Usage Per Day");
        JMenuItem findTrips = new JMenuItem("Find Trips");
        JMenuItem listShipsInPort = new JMenuItem("List Ships In Port");

        containers.add(viewContainers);
        containers.add(addContainers);
        vehicles.add(viewVehicles);
        port.add(myPort);
        port.add(updatePort);
        trips.add(viewTrips);
        statistics.add(calculateContainersWeight);
        statistics.add(fuelUsagePerDay);
        statistics.add(findTrips);
        statistics.add(listShipsInPort);

        menuBar.add(containers);
        menuBar.add(vehicles);
        menuBar.add(port);
        menuBar.add(trips);
        menuBar.add(statistics);


        /* Containers Menu Action Listeners*/
        viewContainers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppFrame.getCardPanel().add(new ViewContainers(controlPort).getMainPanel(), "ViewContainersPanelManager");
                AppFrame.getCardLayout().show(AppFrame.getCardPanel(), "ViewContainersPanelManager");
            }
        });

        addContainers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppFrame.getCardPanel().add(new AddContainers(controlPort).getMainPanel(), "AddContainerPanelManager");
                AppFrame.getCardLayout().show(AppFrame.getCardPanel(), "AddContainerPanelManager");
            }
        });

        viewVehicles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppFrame.getCardPanel().add(new ViewVehicles(controlPort).getMainPanel(), "ViewVehiclesPanelManager");
                AppFrame.getCardLayout().show(AppFrame.getCardPanel(), "ViewVehiclesPanelManager");
            }
        });

        myPort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppFrame.getCardPanel().add(new MyPort(controlPort).getMainPanel(), "MyPortPanel");
                AppFrame.getCardLayout().show(AppFrame.getCardPanel(), "MyPortPanel");
            }
        });

        updatePort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppFrame.getCardPanel().add(new UpdatePort(controlPort).getMainPanel(), "UpdatePortPanelManager");
                AppFrame.getCardLayout().show(AppFrame.getCardPanel(), "UpdatePortPanelManager");
            }
        });

        /* Trip Menu Action Listeners*/
        viewTrips.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppFrame.getCardPanel().add(new ViewTrips(controlPort).getMainPanel(), "ViewTripsPanelManager");
                AppFrame.getCardLayout().show(AppFrame.getCardPanel(), "ViewTripsPanelManager");
            }
        });

        /* Statistics Menu Action Listeners*/
        calculateContainersWeight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppFrame.getCardPanel().add(new CalculateContainerWeight().getMainPanel(), "CalculateContainerWeightPanel");
                AppFrame.getCardLayout().show(AppFrame.getCardPanel(), "CalculateContainerWeightPanel");
            }
        });

        fuelUsagePerDay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppFrame.getCardPanel().add(new FuelUsagePerDay().getMainPanel(), "FuelUsagePanel");
                AppFrame.getCardLayout().show(AppFrame.getCardPanel(), "FuelUsagePanel");
            }
        });

        findTrips.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppFrame.getCardPanel().add(new FindTrips().getMainPanel(), "FindTripsPanel");
                AppFrame.getCardLayout().show(AppFrame.getCardPanel(), "FindTripsPanel");
            }
        });

        listShipsInPort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppFrame.getCardPanel().add(new ListShipsInPort().getMainPanel(), "ListShipsInPortPanel");
                AppFrame.getCardLayout().show(AppFrame.getCardPanel(), "ListShipsInPortPanel");
            }
        });


    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }
}
