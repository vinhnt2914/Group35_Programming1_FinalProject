package portmanagementsystem.gui;

import portmanagementsystem.DataManager;
import portmanagementsystem.models.Container;
import portmanagementsystem.models.Port;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateContainer {
    private JTextField weightField;
    private JPanel mainPanel;
    private JButton updateContainerButton;
    private JComboBox<Container.ContainerType> typeBox;

    public UpdateContainer(Container container) {
        for (Container.ContainerType containerType : Container.ContainerType.values()) {
            typeBox.addItem(containerType);
        }
        load(container);

        updateContainerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if user enters nothing
                if (weightField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Container Weight can not be empty", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    double containerWeight = 0;
                    // Try catch for data validation
                    try {
                        containerWeight = Double.parseDouble(weightField.getText());
                        // Check if container weight is smaller than 0
                        if (containerWeight <= 0) {
                            JOptionPane.showMessageDialog(null, "Container weight must be greater than 0!", "Fail", JOptionPane.ERROR_MESSAGE);
                            // Check if new container weight exceed port storing capacity
                        } else if (containerWeight - container.getWeight() > container.getCurrentPort().getRemainingCapacity()){
                            JOptionPane.showMessageDialog(null, "Invalid weight input, it has exceed the remaining capacity of the port!", "Fail", JOptionPane.ERROR_MESSAGE);
                        } else {
                            Port port = container.getCurrentPort();
                            port.calculateRemainingCapacity(containerWeight - container.getWeight());
                            container.setWeight(containerWeight);
                            container.setType((Container.ContainerType) typeBox.getSelectedItem());
                            // Add the container to the new Port and decrease port remaining capacity
                            DataManager.updateContainer(container);
                            DataManager.updatePort(port);
                            JOptionPane.showMessageDialog(null, "Container info updated!", "Success", JOptionPane.INFORMATION_MESSAGE);
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

    public void load(Container container) {

        weightField.setText(String.valueOf(container.getWeight()));

    }
}
