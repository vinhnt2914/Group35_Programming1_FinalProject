package portmanagementsystem.gui;

import portmanagementsystem.DataManager;
import portmanagementsystem.models.Manager;
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

public class ViewManagers implements PanelAction{
    private JPanel mainPanel;
    private JList<Manager> listView;
    private JTextField searchField;
    private JButton deleteButton;
    private DefaultListModel<Manager> managerListModel;
    private JPopupMenu popupMenu;

    public ViewManagers() {
        // Create pop up menu
        popupMenu = new JPopupMenu();
        JMenuItem updateManager = new JMenuItem("Update Manager");
        popupMenu.add(updateManager);

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

        updateManager.addActionListener(e -> {
            AppFrame.getCardPanel().add(new UpdateManager(listView.getSelectedValue()).getMainPanel(), "UpdateManagerPanel");
            AppFrame.getCardLayout().show(AppFrame.getCardPanel(), "UpdateManagerPanel");
        });

        load();

        // Function for search field
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

        // Function for delete button
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if user has selected a manager or not
                if (Objects.isNull(listView.getSelectedValue())) {
                    JOptionPane.showMessageDialog(null, "Please select a manager!", "Failed", JOptionPane.ERROR_MESSAGE);
                } else {
                    Port port = listView.getSelectedValue().getControlPort();
                    int indexPort = DataManager.getPortList().indexOf(port);
                    DataManager.getPortList().get(indexPort).setManager(null);
                    DataManager.getManagerList().remove(listView.getSelectedValue());
                    try {
                        DataManager.writePorts();
                        DataManager.writeManagers();
                        load();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }

    // update listView content according to searchField input
    private void updateSearchResults() {
        String searchID = searchField.getText();
        List<Manager> sortedList = new ArrayList<>();

        // Check if user enter nothing
        if (searchID.isBlank()) {
            managerListModel.clear();
            managerListModel.addAll(DataManager.getManagerList());
        } else {
            DataManager.getManagerList().forEach(manager -> {
                if (manager.getControlPort().getId().equalsIgnoreCase(searchID))
                    sortedList.add(manager);
            });
            managerListModel.clear();
            managerListModel.addAll(sortedList);
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    @Override
    public void load() {
        managerListModel = new DefaultListModel<>();
        managerListModel.addAll(DataManager.getManagerList());
        listView.setModel(managerListModel);
    }
}
