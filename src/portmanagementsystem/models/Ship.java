package portmanagementsystem.models;

import portmanagementsystem.DataManager;

import javax.swing.*;
import java.io.*;

// Define a class named Ship that extends the Vehicle class, implements the VehicleAction interface, and implements the Serializable interface.
public class Ship extends Vehicle implements VehicleAction, Serializable {

    // SerialVersionUID is used for version control during deserialization.
    @Serial
    private static final long serialVersionUID = 31L;

    // Define a constant for the counter file path.
    private static final String COUNTER_FILE = "src/data/shipCounter.txt";

    // Initialize the counter with the value loaded from the file.
    private static int counter = loadCounter();

    // Constructor for the Ship class.
    public Ship(String name, double carryingCapacity, double fuelCapacity, Port currentPort) {
        // Call the constructor of the superclass (Vehicle) and pass the provided parameters.
        super(name, carryingCapacity, fuelCapacity, currentPort);
        // Generate a unique ship ID.
        this.id = generateShipId();
        // Set the vehicle type to "SHIP."
        setVehicleType("SHIP");
        // Save the counter value to the file.
        Ship.saveCounter();
    }

    // Generate a unique ship ID.
    private static String generateShipId() {
        return "SH-" + counter++;
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

    // Override the loadContainer method to load a container onto the ship.
    @Override
    public boolean loadContainer(Container container) {
        if (this.getRemainingCapacity() >= container.getWeight()) {
            Port port = this.getCurrentPort();
            this.calculateRemainingCapacity(container.getWeight());
            this.getListOfContainers().add(container);
            this.getContainerCount().put(container.getType(), this.getContainerCount().getOrDefault(container.getType(), 0) + 1);
            this.addNumberOfContainers(1);

            container.setCurrentPort(null);

            port.getListOfContainers().remove(container);
            port.addRemainingCapacity(container.getWeight());
            port.minusNumberOfContainer(1);

            // Update data using the DataManager class.
            DataManager.updatePort(port);
            DataManager.updateVehicle(this);
            DataManager.updateContainer(container);

            // Display a success message.
            JOptionPane.showMessageDialog(null, "Container is loaded onto the vehicle", "Success", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Container loaded onto vehicle");
            return true;
        } else {
            // Display an error message if vehicle storage is not enough.
            JOptionPane.showMessageDialog(null, "Vehicle storage is not enough to contain this container", "Failed", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    // Override the calulateRequiredFuel method to calculate the required fuel for a trip to a destination port.
    @Override
    public double calculateRequiredFuel(Port destinationPort) {
        double requiredFuel = 0;
        double distance = Port.calculateDistance(this.getCurrentPort(), destinationPort);
        double fuelForDistanceOnly = distance * 2;
        for (Container c : getListOfContainers()) {
            requiredFuel += c.getFuelConsumption(Container.VehicleType.SHIP) * distance;
        }
        requiredFuel += fuelForDistanceOnly;
        System.out.println(requiredFuel);
        return requiredFuel;
    }
}
