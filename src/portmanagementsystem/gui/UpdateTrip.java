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

public class UpdateTrip {
    private JFormattedTextField departureDateField;
    private JFormattedTextField arrivalDateField;
    private JButton updateTripButton;
    private JPanel mainPanel;

    public UpdateTrip(Trip trip) {
        load(trip);

        updateTripButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String departureDate = departureDateField.getText();
                String arrivalDate = arrivalDateField.getText();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                LocalDate parsedDepartureDate = LocalDate.parse(departureDate, formatter);
                LocalDate parsedArrivalDate = LocalDate.parse(arrivalDate, formatter);
                if (parsedDepartureDate.isAfter(parsedArrivalDate)) {
                    JOptionPane.showMessageDialog(mainPanel, "Arrival date cannot be before departure date", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    trip.setDepartureDate(parsedDepartureDate);
                    trip.setArrivalDate(parsedArrivalDate);
                    DataManager.updateTrip(trip);
                }
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void load(Trip trip) {
        MaskFormatter dateFormatter = null;
        try {
            dateFormatter = new MaskFormatter("####/##/##");
            dateFormatter.setPlaceholderCharacter('_'); // Use underscores for empty positions
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Set the MaskFormatter as the formatter factory for the JFormattedTextField
        if (dateFormatter != null) {
            departureDateField.setFormatterFactory(new DefaultFormatterFactory(dateFormatter));
            arrivalDateField.setFormatterFactory(new DefaultFormatterFactory(dateFormatter));
        }

        // Format the LocalDate objects to the desired string format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String formattedDepartureDate = trip.getDepartureDate().format(formatter);
        String formattedArrivalDate = trip.getArrivalDate().format(formatter);

        // Set the formatted dates in the text fields
        departureDateField.setText(formattedDepartureDate);
        arrivalDateField.setText(formattedArrivalDate);
    }

}
