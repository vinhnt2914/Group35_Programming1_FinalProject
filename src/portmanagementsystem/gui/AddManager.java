package portmanagementsystem.gui;

import portmanagementsystem.DataManager;
import portmanagementsystem.models.Container;
import portmanagementsystem.models.Manager;
import portmanagementsystem.models.Port;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

public class AddManager {
    private JTextField usernameField;
    private JTextField passwordField;
    private JButton addManagerButton;
    private JList<Port> listView;
    private JPanel mainPanel;


    public AddManager() {
        // Call load function
        load();

        // Function for add manager button
        addManagerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if the selected Port already have a manager
                if (Objects.isNull(listView.getSelectedValue().getManager())) {
                    // Get username and password from the input field
                    String username = usernameField.getText();
                    String password = passwordField.getText();
                    // Checking if the username is already in used
                    boolean validUsername = true;
                    for (Manager manager : DataManager.getManagerList()) {
                        if (manager.getUsername().equals(username))
                            validUsername = false;
                    }
                    if (validUsername) {
                        Port port = listView.getSelectedValue();
                        Manager manager = new Manager(username, password, port);
                        DataManager.getManagerList().add(manager);
                        port.setManager(manager);
                        DataManager.updatePort(port);
                        try {
                            DataManager.writeManagers();
                            JOptionPane.showMessageDialog(null, "Manager created!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Username is already in used!", "Failed", JOptionPane.ERROR_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "This port already have a manager!", "Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // Function for load data into listView
    public void load() {
        DefaultListModel<Port> portListModel = new DefaultListModel<>();
        portListModel.addAll(DataManager.getPortList());
        listView.setModel(portListModel);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
