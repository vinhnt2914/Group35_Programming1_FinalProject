package portmanagementsystem.gui;

import portmanagementsystem.DataManager;
import portmanagementsystem.models.Manager;
import portmanagementsystem.models.Port;

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

public class ViewPorts implements PanelAction {
    private JPanel mainPanel; // Main panel containing the View Ports UI components
    private JList<Port> listView; // JList for displaying the list of ports
    private JTextField searchField; // Text field for searching ports
    private JButton deleteButton; // Button for deleting a selected port
    private DefaultListModel<Port> portListModel; // DefaultListModel to manage the list of ports
    private JPopupMenu popupMenu; // Popup menu for actions on selected ports

    public ViewPorts() {
        load(); // Load the initial list of ports

        // Create and configure the popup menu
        popupMenu = new JPopupMenu();
        JMenuItem updatePort = new JMenuItem("Update Port");
        popupMenu.add(updatePort);

        // Add mouse listener to the list view to show the popup menu on right-click
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

        // ActionListener for the "Update Port" menu item in the popup menu
        updatePort.addActionListener(e -> {
            // Open the Update Port panel with the selected port's data
            AppFrame.getCardPanel().add(new UpdatePort(listView.getSelectedValue()).getMainPanel(), "UpdatePortPanel");
            AppFrame.getCardLayout().show(AppFrame.getCardPanel(), "UpdatePortPanel");
        });

        // DocumentListener for the searchField to update search results dynamically
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

        // ActionListener for the deleteButton to delete a selected port
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Objects.isNull(listView.getSelectedValue())) {
                    JOptionPane.showMessageDialog(null, "Please select a port!", "Failed", JOptionPane.ERROR_MESSAGE);
                } else {
                    Port selectedPort = listView.getSelectedValue();
                    if (!selectedPort.getListOfContainers().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "You cannot delete this port yet. There are still containers on the port!", "Cannot delete port", JOptionPane.ERROR_MESSAGE);
                    } else if (!selectedPort.getListOfVehicles().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "You cannot delete this port yet. There are still vehicles on the port!", "Cannot delete port", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // Remove the manager associated with the selected port
                        Manager portManager = selectedPort.getManager();
                        DataManager.getManagerList().remove(portManager);

                        // Remove the selected port
                        DataManager.getPortList().remove(selectedPort);

                        try {
                            // Write updated data to files and reload the ports
                            DataManager.writePorts();
                            DataManager.writeManagers();
                            load();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        });
    }

    // Get the main panel containing the View Ports UI
    public JPanel getMainPanel() {
        return mainPanel;
    }

    // Update search results according to searchField input
    private void updateSearchResults() {
        String searchID = searchField.getText();
        List<Port> sortedList = new ArrayList<>();

        if (searchID.isBlank()) {
            portListModel.clear();
            portListModel.addAll(DataManager.getPortList());
        } else {
            DataManager.getPortList().forEach(port -> {
                if (port.getId().equalsIgnoreCase(searchID))
                    sortedList.add(port);
            });
            portListModel.clear();
            portListModel.addAll(sortedList);
        }
    }

    // Load the list of ports and display them in the JList
    @Override
    public void load() {
        portListModel = new DefaultListModel<>();
        portListModel.addAll(DataManager.getPortList());
        listView.setModel(portListModel);
        System.out.println("Port read successfully");
    }
}
