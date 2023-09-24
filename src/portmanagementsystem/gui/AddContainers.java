package portmanagementsystem.gui;

import portmanagementsystem.DataManager;
import portmanagementsystem.models.Container;
import portmanagementsystem.models.Port;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

public class AddContainers implements PanelAction{
    private JTextField containerWeightField;
    private JComboBox containerTypeBox;
    private JButton addContainerButton;
    private JPanel mainPanel;
    private JList<Port> listView;
    private DefaultListModel<Port> portListModel;

    public AddContainers() {
        // Load container type into the JComboBox
        for (Container.ContainerType containerType : Container.ContainerType.values()) {
            containerTypeBox.addItem(containerType);
        }

        // Call load function
        load();

        // Function for add container button
        addContainerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if user enters nothing
                if (containerWeightField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Container Weight can not be empty", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    double containerWeight;
                    // Try catch for data validation
                    try {
                        containerWeight = Double.parseDouble(containerWeightField.getText());
                        if (containerWeight <= 0) {
                            JOptionPane.showMessageDialog(null, "Container weight cannot be 0 or smaller!", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            Container.ContainerType containerType = (Container.ContainerType) containerTypeBox.getSelectedItem();
                            // Alert if user didn't choose a port
                            if (Objects.isNull(listView.getSelectedValue())) {
                                JOptionPane.showMessageDialog(null, "Please select a Port!", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            // Check if the selected port still have enough storage
                            else if (containerWeight > listView.getSelectedValue().getRemainingCapacity()) {
                                JOptionPane.showMessageDialog(null, "The selected port doesn't have enough storage!", "Error", JOptionPane.ERROR_MESSAGE);
                            } else {
//                            int indexPort = DataManager.getPortList().indexOf(listView.getSelectedValue());
                                Port port = listView.getSelectedValue();
                                Container container = new Container(containerWeight, containerType, port);
                                port.getListOfContainers().add(container);
                                port.calculateRemainingCapacity(containerWeight);
                                port.addNumberOfContainer(1);
                                DataManager.getContainerList().add(container);
                                // Update data back into database
                                try {
                                    DataManager.updatePort(port);
                                    DataManager.writeContainers();
                                    JOptionPane.showMessageDialog(null, "Container has been added!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                    load();
                                } catch (IOException ex) {
                                    System.out.println("Write unsuccessful");
                                }
                            }
                        }
                    // Alert if user enter invalid input for weight field
                    } catch (NumberFormatException exception) {
                        JOptionPane.showMessageDialog(null, "Invalid weight input. Must be number", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    public AddContainers(Port controlPort) {
        // Hide list view
        listView.setVisible(false);
        // Load container type into the JComboBox
        for (Container.ContainerType containerType : Container.ContainerType.values()) {
            containerTypeBox.addItem(containerType);
        }

        // Function for add container button
        addContainerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if user enters nothing
                if (containerWeightField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(mainPanel, "Container Weight can not be empty", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    double containerWeight;
                    // Try catch for data validation
                    try {
                        containerWeight = Double.parseDouble(containerWeightField.getText());
                        Container.ContainerType containerType = (Container.ContainerType) containerTypeBox.getSelectedItem();
                        // Alert if user didn't choose a port
                        if (containerWeight > controlPort.getRemainingCapacity()) {
                            JOptionPane.showMessageDialog(mainPanel, "This port doesn't have enough storage!", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            int indexPort = DataManager.getPortList().indexOf(controlPort);
                            Container container = new Container(containerWeight, containerType, controlPort);
                            container.getCurrentPort().getListOfContainers().add(container);
                            DataManager.getContainerList().add(container);
                            DataManager.getPortList().get(indexPort).calculateRemainingCapacity(containerWeight);
                            // Update data back into database
                            try {
                                DataManager.writePorts();
                                DataManager.writeContainers();
                                JOptionPane.showMessageDialog(mainPanel, "Container has been added!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                load();
                            } catch (IOException ex) {
                                System.out.println("Write unsuccessful");
                            }
                        }
                        // Alert if user enter invalid input for weight field
                    } catch (NumberFormatException exception) {
                        JOptionPane.showMessageDialog(null, "Invalid weight input. Must be number", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    // Function for loading data into listView
    @Override
    public void load() {
        portListModel = new DefaultListModel<>();
        portListModel.addAll(DataManager.getPortList());
        listView.setModel(portListModel);
    }
}
