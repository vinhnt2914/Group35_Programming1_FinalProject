package portmanagementsystem.models;

import java.io.*;
import java.time.LocalDate;

// Define a class named Trip that implements the Serializable interface.
public class Trip implements Serializable {

    // SerialVersionUID is used for version control during deserialization.
    @Serial
    private static final long serialVersionUID = 4L;

    // Define a constant for the counter file path.
    private static final String COUNTER_FILE = "src/data/tripCounter.txt";

    // Initialize the counter with the value loaded from the file.
    private static int counter = loadCounter();

    // Declare instance variables for the Trip class.
    private String id;
    private Vehicle vehicle;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private Port departurePort;
    private Port destinatedPort;
    private double fuelConsumption;

    // Constructor for the Trip class.
    public Trip(Vehicle vehicle, LocalDate departureDate, Port departurePort, Port destinatedPort, double fuelConsumption) {
        // Generate a unique trip ID.
        this.id = generateTripID();
        // Set the vehicle, departure date, and other properties.
        this.vehicle = vehicle;
        this.departureDate = departureDate;
        this.arrivalDate = departureDate.plusDays(2); // Assuming a 2-day trip duration.
        this.departurePort = departurePort;
        this.destinatedPort = destinatedPort;
        this.fuelConsumption = fuelConsumption;
        // Save the counter value to the file.
        saveCounter();
    }

    // Generate a unique trip ID.
    private static String generateTripID() {
        return "TRIP-" + counter++;
    }

    // Load the counter value from the counter file.
    private static int loadCounter() {
        try (BufferedReader reader = new BufferedReader(new FileReader(COUNTER_FILE))) {
            return Integer.parseInt(reader.readLine());
        } catch (FileNotFoundException e) {
            // Counter file not found, start from 1
            return 1;
        } catch (IOException e) {
            e.printStackTrace();
            return 1;
        }
    }

    // Save the current counter value to the counter file.
    public static void saveCounter() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(COUNTER_FILE))) {
            writer.write(String.valueOf(counter));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getter methods
    public String getId() {
        return id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public Port getDeparturePort() {
        return departurePort;
    }

    public Port getDestinatedPort() {
        return destinatedPort;
    }

    public double getFuelConsumption() {
        return fuelConsumption;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }
    // Setter method
    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    // Override the equals method to compare trips based on their IDs.
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Trip trip = (Trip) obj;
        return id.equals(trip.id);
    }

    // Override the hashCode method to generate a hash code based on the trip ID.
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    // Override the toString method to provide a string representation of the trip.
    @Override
    public String toString() {
        return String.format("ID: %s, Vehicle: %s, departureDate: %s, arrivalDate: %s, departurePort: %s, destinatedPort: %s, fuelConsumption: %.2f",
                getId(),
                getVehicle().getId(),
                getDepartureDate(),
                getArrivalDate(),
                getDeparturePort().getId(),
                getDestinatedPort().getId(),
                getFuelConsumption());
    }
}
