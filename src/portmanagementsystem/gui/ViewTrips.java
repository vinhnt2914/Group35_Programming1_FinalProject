package portmanagementsystem.gui;

import portmanagementsystem.DataManager;
import portmanagementsystem.models.Trip;
import portmanagementsystem.models.Port;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.time.LocalDate;

public class ViewTrips implements PanelAction {
    private JPanel mainPanel; // Main panel containing the View Trips UI components
    private JList<Trip> listView; // JList for displaying the list of trips
    private DefaultListModel<Trip> tripListModel; // DefaultListModel to manage the list of trips
    private JPopupMenu popupMenu; // Popup menu for actions on selected trips

    public ViewTrips() {
        // Create pop-up menu
        popupMenu = new JPopupMenu();
        JMenuItem updateTrip = new JMenuItem("Update Trip");
        popupMenu.add(updateTrip);

        // Add the pop-up menu to the list view
        listView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int index = listView.locationToIndex(e.getPoint());
                    if (index >= 0 && index < listView.getModel().getSize()) {
                        // Set the selected item in the JList
                        listView.setSelectedIndex(index);

                        // Show the pop-up menu
                        popupMenu.show(listView, e.getX(), e.getY());
                    }
                }
            }
        });

        // ActionListener for the "Update Trip" menu item in the pop-up menu
        updateTrip.addActionListener(e -> {
            // Open the Update Trip panel with the selected trip's data
            AppFrame.getCardPanel().add(new UpdateTrip(listView.getSelectedValue()).getMainPanel(), "UpdateTripPanel");
            AppFrame.getCardLayout().show(AppFrame.getCardPanel(), "UpdateTripPanel");
        });

        load(); // Load the initial list of trips

    }

    // Version for port manager
    public ViewTrips(Port controlPort) {
        load(controlPort);
    }

    @Override
    public void load() {
        tripListModel = new DefaultListModel<>();
        tripListModel.addAll(DataManager.getTripList());
        listView.setModel(tripListModel);
        System.out.println("Trips read successfully");
    }

    public void load(Port controlPort) {
        tripListModel = new DefaultListModel<>();
        for (Trip trip : DataManager.getTripList()) {
            if (trip.getDestinatedPort().equals(controlPort)) {
                tripListModel.addElement(trip);
            }
        }
        listView.setModel(tripListModel);
        System.out.println("Trips read successfully");
    }

    // Get the main panel containing the View Trips UI
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
