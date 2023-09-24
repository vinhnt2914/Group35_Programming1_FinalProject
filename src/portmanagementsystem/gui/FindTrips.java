package portmanagementsystem.gui;

import portmanagementsystem.DataManager;
import portmanagementsystem.models.Trip;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FindTrips implements PanelAction{
    private JPanel mainPanel;
    private JList<Trip> listView;
    private JRadioButton specificDateOption;
    private JRadioButton dateRangeOption;
    private JFormattedTextField startDateField;
    private JFormattedTextField endDateField;
    private JButton searchButton;
    private JLabel startDateLabel;
    private JLabel endDateLabel;
    private ButtonGroup optionGroup;
    private DefaultListModel<Trip> tripListModel;

    public FindTrips() {
        load();
        optionGroup = new ButtonGroup();

        // Add the radio buttons to the ButtonGroup
        optionGroup.add(specificDateOption);
        optionGroup.add(dateRangeOption);

        // By default, select the "Specific Date" option
        dateRangeOption.setSelected(true);

        specificDateOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // When "Specific Date" is selected, hide the endDateField
                startDateLabel.setText("Select Date: ");
                endDateField.setVisible(false);
                endDateLabel.setVisible(false);
            }
        });

        // Add an ActionListener to the dateRangeOption radio button
        dateRangeOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // When "Date Range" is selected, show the endDateField
                startDateLabel.setText("Start Date: ");
                endDateField.setVisible(true);
                endDateLabel.setVisible(true);
            }
        });

        MaskFormatter dateFormatter = null;
        try {
            dateFormatter = new MaskFormatter("####/##/##");
            dateFormatter.setPlaceholderCharacter('_'); // Use underscores for empty positions
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Set the MaskFormatter as the formatter factory for the JFormattedTextField
        if (dateFormatter != null) {
            startDateField.setFormatterFactory(new DefaultFormatterFactory(dateFormatter));
            endDateField.setFormatterFactory(new DefaultFormatterFactory(dateFormatter));
        }

        // Function for search button
        searchButton.addActionListener(e -> {
            // For specific date opt
            if (specificDateOption.isSelected()) {
                String specificDateText = startDateField.getText();
                if (!specificDateText.isEmpty() && !specificDateText.contains("_")) {
                    updateListView(specificDateText);
                } else {
                    load();
                }

            // For date range opt
            } else {
                String startDateText = startDateField.getText();
                String endDateText = endDateField.getText();
                System.out.println(startDateText);
                System.out.println(endDateText);
                if ((!startDateText.isEmpty() && !endDateText.isEmpty() && !startDateText.contains("_") && !endDateText.contains("_"))) {
                    System.out.println("run1");
                    updateListView(startDateText, endDateText);
                } else {
                    System.out.println("run3");
                    JOptionPane.showMessageDialog(mainPanel, "Please enter both start and end dates for the date range.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void updateListView(String specificDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate parsedDate = null;
        boolean validDate = false;
        try {
            parsedDate = LocalDate.parse(specificDate, formatter);
            validDate = true;
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(mainPanel, "Invalid date, please enter a valid one", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Create a new DefaultListModel and add trips that match the specific date
        if (validDate) {
            tripListModel.clear();
            DefaultListModel<Trip> filteredListModel = new DefaultListModel<>();
            for (Trip trip : DataManager.getTripList()) {
                if (trip.getArrivalDate().isEqual(parsedDate)) {
                    filteredListModel.addElement(trip);
                }
            }
            // Set the new filteredListModel to the listView
            listView.setModel(filteredListModel);
        }
    }

    private void updateListView(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate parsedStartDate = null;
        LocalDate parsedEndDate = null;
        boolean validDate = false;
        try {
            parsedStartDate = LocalDate.parse(startDate, formatter);
            parsedEndDate = LocalDate.parse(endDate, formatter);
            validDate = true;
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(mainPanel, "Invalid date, please enter a valid one", "Error", JOptionPane.ERROR_MESSAGE);
        }

        if (validDate) {
            tripListModel.clear();

            // Create a new DefaultListModel and add trips that match the date range
            DefaultListModel<Trip> filteredListModel = new DefaultListModel<>();
            for (Trip trip : DataManager.getTripList()) {
                if (!trip.getArrivalDate().isBefore(parsedStartDate) && !trip.getArrivalDate().isAfter(parsedEndDate)) {
                    filteredListModel.addElement(trip);
                }
            }

            // Set the new filteredListModel to the listView
            listView.setModel(filteredListModel);
        }
    }

    @Override
    public void load() {
        // Load all trips when the panel is loaded
        tripListModel = new DefaultListModel<>();
        tripListModel.addAll(DataManager.getTripList());
        listView.setModel(tripListModel);
    }
}
