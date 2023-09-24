package portmanagementsystem.gui;

import portmanagementsystem.DataManager;
import portmanagementsystem.models.Container;
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

public class ViewContainers implements PanelAction {
    private JPanel mainPanel;
    private JList<Container> listView;
    private JTextField searchField;
    private JButton deleteButton;
    private JPopupMenu popupMenu;
    private DefaultListModel<Container> containerListModel;

    // Version For Admin
    public ViewContainers() {
        // Run the load function
        load();
        // Create the popup menu
        popupMenu = new JPopupMenu();
        JMenuItem updateContainer = new JMenuItem("Update Container");
        popupMenu.add(updateContainer);

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

        updateContainer.addActionListener(e -> {
            AppFrame.getCardPanel().add(new UpdateContainer(listView.getSelectedValue()).getMainPanel(), "UpdateContainerPanel");
            AppFrame.getCardLayout().show(AppFrame.getCardPanel(), "UpdateContainerPanel");
        });

        // Add document listener into the searchField
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

        // Add action listener into the delete button
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if user select nothing
                if (Objects.isNull(listView.getSelectedValue())) {
                    JOptionPane.showMessageDialog(null, "Please select a container!", "Container not found", JOptionPane.ERROR_MESSAGE);
                } else if (Objects.isNull(listView.getSelectedValue().getCurrentPort())) {
                    JOptionPane.showMessageDialog(null, "Cannot delete this container, it's on a vehicle!", "Failed", JOptionPane.ERROR_MESSAGE);
                } else {
                    Container container = listView.getSelectedValue();
                    Port port = listView.getSelectedValue().getCurrentPort();
                    port.getListOfContainers().remove(container);
                    port.minusNumberOfContainer(1);
                    port.addRemainingCapacity(container.getWeight());
                    DataManager.getContainerList().remove(listView.getSelectedValue());
                    try {
                        DataManager.writeContainers();
                        DataManager.updatePort(port);
                        load();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }

    // Version for Managers
    public ViewContainers(Port controlPort) {popupMenu = new JPopupMenu();
        JMenuItem updateContainer = new JMenuItem("Update Container");
        popupMenu.add(updateContainer);

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

        updateContainer.addActionListener(e -> {
            AppFrame.getCardPanel().add(new UpdateContainer(listView.getSelectedValue()).getMainPanel(), "UpdateContainerPanel");
            AppFrame.getCardLayout().show(AppFrame.getCardPanel(), "UpdateContainerPanel");
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

        // Add action listener into the delete button
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if user select nothing
                if (Objects.isNull(listView.getSelectedValue())) {
                    JOptionPane.showMessageDialog(null, "Please select a container!", "Container not found", JOptionPane.ERROR_MESSAGE);
                } else if (Objects.isNull(listView.getSelectedValue().getCurrentPort())) {
                    JOptionPane.showMessageDialog(null, "Cannot delete this container, it's on a vehicle!", "Failed", JOptionPane.ERROR_MESSAGE);
                } else {
                    int indexPort = DataManager.getPortList().indexOf(listView.getSelectedValue().getCurrentPort());
                    DataManager.getPortList().get(indexPort).getListOfContainers().remove(listView.getSelectedValue());
                    DataManager.getContainerList().remove(listView.getSelectedValue());
                    try {
                        DataManager.writeContainers();
                        DataManager.writePorts();
                        load(controlPort);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }

    // update listView according to searchField input
    private void updateSearchResults() {
        String searchID = searchField.getText(); // get user input
        List<Container> sortedList = new ArrayList<>();

        // check if searchID is blank
        if (searchID.isBlank()) {
            containerListModel.clear();
            containerListModel.addAll(DataManager.getContainerList());
        } else {
            DataManager.getContainerList().forEach(container -> {
                // Add the container to sorted list
                if (container.getId().equalsIgnoreCase(searchID))
                    sortedList.add(container);
            });
            // Clear and fill the modal with sorted list
            containerListModel.clear();
            containerListModel.addAll(sortedList);
        }
    }

    // Version for managers
    private void updateSearchResults(Port controlPort) {
        String searchID = searchField.getText();
        List<Container> sortedList = new ArrayList<>();

        if (searchID.isBlank()) {
            containerListModel.clear();
            containerListModel.addAll(controlPort.getListOfContainers());
        } else {
            containerListModel.clear();
            for (Container container : controlPort.getListOfContainers()) {
                if (container.getId().equalsIgnoreCase(searchID))
                    containerListModel.addElement(container);
            }
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    // load data into listView
    @Override
    public void load() {
        containerListModel = new DefaultListModel<>();
        containerListModel.addAll(DataManager.getContainerList());
        listView.setModel(containerListModel);
    }

    // Version for port manager, only display Container on the port
    public void load(Port controlPort) {
        containerListModel = new DefaultListModel<>();

        for (Port p : DataManager.getPortList()) {
            if (Objects.equals(p, controlPort))
                containerListModel.addAll(p.getListOfContainers());
        }
        listView.setModel(containerListModel);
    }
}
