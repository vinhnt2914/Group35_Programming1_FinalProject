package portmanagementsystem.gui;

import portmanagementsystem.DataManager;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class FuelUsagePerDay implements PanelAction {

    private JPanel mainPanel;
    private JList<String> listView;
    private JButton searchButton;
    private JFormattedTextField searchField;
    private DefaultListModel<String> fuelListModel;

    public FuelUsagePerDay() {
        initializeComponents();
    }

    private void initializeComponents() {
        // Initialize components as you did before
        load();

        // Implement maskFormatter to restrict user input
        MaskFormatter dateFormatter = null;
        try {
            dateFormatter = new MaskFormatter("####/##/##");
            dateFormatter.setPlaceholderCharacter('_'); // Use underscores for empty positions
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Set the MaskFormatter as the formatter factory for the JFormattedTextField
        if (dateFormatter != null) {
            searchField.setFormatterFactory(new DefaultFormatterFactory(dateFormatter));
        }

        // Add an ActionListener to the search button
        searchButton.addActionListener(e -> {
            // Get the user input from the JFormattedTextField
            String userInput = searchField.getText();

            // Update the list view with filtered data based on user input
            updateListView(userInput);
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    // Load data into listView
    @Override
    public void load() {
        fuelListModel = new DefaultListModel<>();
        List<String> fuelConsumptionString = new ArrayList<>();
        for (Map.Entry<LocalDate, Double> entry : DataManager.calculateTotalFuelConsumptionPerDay().entrySet()) {
            LocalDate date = entry.getKey();
            double fuelConsumption = entry.getValue();
            String displayString = String.format("Date: %s, Fuel Consumed: %.2f", date, fuelConsumption);

            fuelConsumptionString.add(displayString);
        }
        fuelListModel.addAll(fuelConsumptionString);
        listView.setModel(fuelListModel);
    }

    // Update the list view based on user input
    private void updateListView(String userInput) {
        // Clear the existing data
        fuelListModel.clear();

        // Iterate through the data and add matching entries to the list model
        for (Map.Entry<LocalDate, Double> entry : DataManager.calculateTotalFuelConsumptionPerDay().entrySet()) {
            LocalDate date = entry.getKey();
            double fuelConsumption = entry.getValue();
            String displayString = String.format("Date: %s, Fuel Consumed: %.2f", date, fuelConsumption);

            // Check if the date in the entry matches the user input
            if (dateMatchesUserInput(date, userInput)) {
                fuelListModel.addElement(displayString);
            }
        }
    }

    // Helper method to check if a date matches the user input
    private boolean dateMatchesUserInput(LocalDate date, String userInput) {
        // Use SimpleDateFormat or other parsing to match and compare dates
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String formattedDate = sdf.format(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        return formattedDate.equals(userInput);
    }
}
