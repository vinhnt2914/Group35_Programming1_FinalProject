package portmanagementsystem.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AdminMenuBar extends MenuBarFrame {
    private JMenuBar menuBar;

    public AdminMenuBar() {
        createMenuBar();
    }

    private void createMenuBar() {
        // Create menu bar
        menuBar = new JMenuBar();

        // Create menu for menu bar
        JMenu containers = new JMenu("Containers");
        JMenu ports = new JMenu("Ports");
        JMenu vehicles = new JMenu("Vehicles");
        JMenu trips = new JMenu("Trips");
        JMenu managers = new JMenu("Managers");
        JMenu statistics = new JMenu("Statistics");

        // Create menu items
        JMenuItem viewContainers = new JMenuItem("View Containers");
        JMenuItem addContainers = new JMenuItem("Add Containers");
        JMenuItem viewPorts = new JMenuItem("View Ports");
        JMenuItem addPorts = new JMenuItem("Add Ports");
        JMenuItem addVehicles = new JMenuItem("Add Vehicles");
        JMenuItem viewVehicles = new JMenuItem("View Vehicles");
        JMenuItem viewTrips = new JMenuItem("View Trips");
        JMenuItem viewManagers = new JMenuItem("View Managers");
        JMenuItem addManagers = new JMenuItem("Add Managers");
        JMenuItem calculateContainersWeight = new JMenuItem("Calculate Containers Weight");
        JMenuItem fuelUsagePerDay = new JMenuItem("Fuel Usage Per Day");
        JMenuItem findTrips = new JMenuItem("Find Trips");
        JMenuItem listShipsInPort = new JMenuItem("List Ships In Port");

        // Add menu items into menu
        containers.add(viewContainers);
        containers.add(addContainers);
        containers.add(calculateContainersWeight);
        ports.add(viewPorts);
        ports.add(addPorts);
        vehicles.add(addVehicles);
        vehicles.add(viewVehicles);
        trips.add(viewTrips);
        managers.add(viewManagers);
        managers.add(addManagers);
        statistics.add(calculateContainersWeight);
        statistics.add(fuelUsagePerDay);
        statistics.add(findTrips);
        statistics.add(listShipsInPort);

        // Add menus into the menu bar
        menuBar.add(containers);
        menuBar.add(ports);
        menuBar.add(vehicles);
        menuBar.add(trips);
        menuBar.add(managers);
        menuBar.add(statistics);

        /* Containers Menu Action Listeners*/
        viewContainers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppFrame.getCardPanel().add(new ViewContainers().getMainPanel(), "ViewContainersPanel");
                AppFrame.getCardLayout().show(AppFrame.getCardPanel(), "ViewContainersPanel");
            }
        });

        addContainers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppFrame.getCardPanel().add(new AddContainers().getMainPanel(), "AddContainersPanel");
                AppFrame.getCardLayout().show(AppFrame.getCardPanel(), "AddContainersPanel");
            }
        });

        /* Ports Menu Action Listeners*/
        viewPorts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppFrame.getCardPanel().add(new ViewPorts().getMainPanel(), "ViewPortsPanel");
                AppFrame.getCardLayout().show(AppFrame.getCardPanel(), "ViewPortsPanel");
            }
        });

        addPorts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppFrame.getCardPanel().add(new AddPort().getMainPanel(), "AddPortsPanel");
                AppFrame.getCardLayout().show(AppFrame.getCardPanel(), "AddPortsPanel");
            }
        });

        /* Vehicles Menu Action Listeners*/
        viewVehicles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppFrame.getCardPanel().add(new ViewVehicles().getMainPanel(), "ViewVehiclesPanel");
                AppFrame.getCardLayout().show(AppFrame.getCardPanel(), "ViewVehiclesPanel");
            }
        });

        addVehicles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    AppFrame.getCardPanel().add(new AddVehicles().getMainPanel(), "AddVehiclesPanel");
                } catch (IOException ex) {
                    System.out.println("Add Vehicles load unsuccessfully");
                }
                AppFrame.getCardLayout().show(AppFrame.getCardPanel(), "AddVehiclesPanel");
            }
        });

        /* Trips Menu Action Listeners*/
        viewTrips.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppFrame.getCardPanel().add(new ViewTrips().getMainPanel(), "ViewTripsPanel");
                AppFrame.getCardLayout().show(AppFrame.getCardPanel(), "ViewTripsPanel");
            }
        });

        /* Managers Menu Action Listeners*/
        viewManagers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppFrame.getCardPanel().add(new ViewManagers().getMainPanel(), "ViewManagersPanel");
                AppFrame.getCardLayout().show(AppFrame.getCardPanel(), "ViewManagersPanel");
            }
        });

        addManagers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppFrame.getCardPanel().add(new AddManager().getMainPanel(), "AddManagersPanel");
                AppFrame.getCardLayout().show(AppFrame.getCardPanel(), "AddManagersPanel");
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

    // Return the menu bar
    public JMenuBar getMenuBar() {
        return menuBar;
    }
}
