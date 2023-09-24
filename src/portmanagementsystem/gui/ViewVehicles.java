package portmanagementsystem.gui;

import portmanagementsystem.DataManager;
import portmanagementsystem.models.Port;
import portmanagementsystem.models.Vehicle;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewVehicles implements PanelAction{
    private JPanel mainPanel;
    private JList<Vehicle> listView;
    private JTextField searchField;
    private JButton deleteButton;
    private JPopupMenu popupMenu;
    private DefaultListModel<Vehicle> vehicleListModel;

    public ViewVehicles() {
        // Create popup menu
        popupMenu = new JPopupMenu();
        // Create menu items
        JMenuItem moveToPort = new JMenuItem("Move to Port");
        JMenuItem loadContainers = new JMenuItem("Load Containers");
        JMenuItem unloadContainers = new JMenuItem("Unload Containers");
        JMenuItem refuel = new JMenuItem("Refuel");
        JMenuItem updateVehicle = new JMenuItem("Update Vehicle");
        // Add items to popup menu
        popupMenu.add(moveToPort);
        popupMenu.add(loadContainers);
        popupMenu.add(unloadContainers);
        popupMenu.add(refuel);
        popupMenu.add(updateVehicle);

        load(); // Load data

        // Create document listener for searchField
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateSearchResults();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateSearchResults();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateSearchResults();
            }
        });

        // Add popupMenu
        listView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            if (SwingUtilities.isRightMouseButton(e)) {
                int index = listView.locationToIndex(e.getPoint());
                if (index >= 0 && index < listView.getModel().getSize()) {
                    // Set the selected item in the JList
                    listView.setSelectedIndex(index);

                    // Show the JPopupMenu
                    popupMenu.show(listView, e.getX(), e.getY());
                }
            }
            }
        });

        moveToPort.addActionListener(e -> {
            AppFrame.getCardPanel().add(new MoveToPort(listView.getSelectedValue()).getMainPanel(), "MoveToPortPanel");
            AppFrame.getCardLayout().show(AppFrame.getCardPanel(), "MoveToPortPanel");
        });

        loadContainers.addActionListener(e -> {
            AppFrame.getCardPanel().add(new AddContainersToVehicle(listView.getSelectedValue()).getMainPanel(), "LoadContainersToVehiclePanel");
            AppFrame.getCardLayout().show(AppFrame.getCardPanel(), "LoadContainersToVehiclePanel");
        });

        unloadContainers.addActionListener(e -> {
            listView.getSelectedValue().unloadContainers();
            load();
        });

        refuel.addActionListener(e -> {
            listView.getSelectedValue().refuel();
            DataManager.updateVehicle(listView.getSelectedValue());
            load();
        });

        updateVehicle.addActionListener(e -> {
            AppFrame.getCardPanel().add(new UpdateVehicle(listView.getSelectedValue()).getMainPanel(), "UpdateVehiclePanel");
            AppFrame.getCardLayout().show(AppFrame.getCardPanel(), "UpdateVehiclePanel");
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Objects.isNull(listView.getSelectedValue())) {
                    JOptionPane.showMessageDialog(null, "Please select a port!", "Failed", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (!listView.getSelectedValue().getListOfContainers().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Cannot delete this vehicle, please unload containers first!", "Failed", JOptionPane.ERROR_MESSAGE);
                    } else {
                        Vehicle vehicle = listView.getSelectedValue();
                        Port port = vehicle.getCurrentPort();
                        port.getListOfVehicles().remove(vehicle);
                        port.minusNumberOfVehicle(1);
                        DataManager.getVehicleList().remove(vehicle);
                        try {
                            DataManager.writeVehicles();
                            DataManager.writePorts();
                            load();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        });
    }

    public ViewVehicles(Port controlPort) {
        popupMenu = new JPopupMenu();
        JMenuItem updateVehicle = new JMenuItem("Update Vehicle");
        popupMenu.add(updateVehicle);

        // Add popupMenu
        listView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int index = listView.locationToIndex(e.getPoint());
                    if (index >= 0 && index < listView.getModel().getSize()) {
                        // Set the selected item in the JList
                        listView.setSelectedIndex(index);

                        // Show the JPopupMenu
                        popupMenu.show(listView, e.getX(), e.getY());
                    }
                }
            }
        });

        updateVehicle.addActionListener(e -> {
            AppFrame.getCardPanel().add(new UpdateVehicle(listView.getSelectedValue()).getMainPanel(), "UpdateVehiclePanel");
            AppFrame.getCardLayout().show(AppFrame.getCardPanel(), "UpdateVehiclePanel");
        });

        load(controlPort);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateSearchResults(controlPort);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateSearchResults(controlPort);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateSearchResults(controlPort);
            }
        });
        // Hide delete button to port managers
        deleteButton.setVisible(false);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void updateSearchResults() {
        String searchID = searchField.getText();
        List<Vehicle> sortedList = new ArrayList<>();

        if (searchID.isBlank()) {
            vehicleListModel.clear();
            vehicleListModel.addAll(DataManager.getVehicleList());
        } else {
            DataManager.getVehicleList().forEach(vehicle -> {
                if (vehicle.getId().equalsIgnoreCase(searchID))
                    sortedList.add(vehicle);
            });
            vehicleListModel.clear();
            vehicleListModel.addAll(sortedList);
        }
    }

    private void updateSearchResults(Port controlPort) {
        String searchID = searchField.getText();
//        List<Vehicle> sortedList = new ArrayList<>();

        if (searchID.isBlank()) {
            vehicleListModel.clear();
            vehicleListModel.addAll(controlPort.getListOfVehicles());
        } else {
            vehicleListModel.clear();
            for (Vehicle vehicle : controlPort.getListOfVehicles()) {
                if (vehicle.getId().equalsIgnoreCase(searchID))
                    vehicleListModel.addElement(vehicle);
            }
//            vehicleListModel.addAll(sortedList);
        }
    }

    @Override
    public void load() {
        vehicleListModel = new DefaultListModel<>();
        vehicleListModel.addAll(DataManager.getVehicleList());
        listView.setModel(vehicleListModel);
        System.out.println("Vehicles read successfully");
    }

    public void load(Port controlPort) {
        vehicleListModel = new DefaultListModel<>();
//        int index = DataManager.getPortList().indexOf(controlPort);
//        vehicleListModel.addAll(DataManager.getPortList().get(index).getListOfVehicles());
        vehicleListModel.addAll(controlPort.getListOfVehicles());
        listView.setModel(vehicleListModel);
    }


}
