package portmanagementsystem.models;

import portmanagementsystem.DataManager;

import javax.swing.*;
import java.io.*;

// Define an abstract class named Truck that extends the Vehicle class and implements the Serializable and VehicleAction interfaces.
public abstract class Truck extends Vehicle implements Serializable, VehicleAction {

    // SerialVersionUID is used for version control during deserialization.
    @Serial
    private static final long serialVersionUID = 32L;

    // Define a constant for the counter file path.
    private static final String COUNTER_FILE = "src/data/truckCounter.txt";

    // Initialize the counter with the value loaded from the file.
    private static int counter = loadCounter();

    // Constructor for the Truck class.
    public Truck(String name, double carryingCapacity, double fuelCapacity, Port currentPort) {
        // Call the constructor of the superclass (Vehicle) and pass the provided parameters.
        super(name, carryingCapacity, fuelCapacity, currentPort);
        // Generate a unique truck ID.
        this.id = generateTruckId();
        // Save the counter value to the file.
        Truck.saveCounter();
    }

    // Generate a unique truck ID.
    private static String generateTruckId() {
        return "TR-" + counter++;
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
    // Override the loadContainer method to load a container onto the truck.
// This method is used to load a container onto a vehicle.
    @Override
    public boolean loadContainer(Container container) {
        // Check if there is enough remaining capacity in the vehicle to load the container.
        if (this.getRemainingCapacity() >= container.getWeight()) {
            // Check if the container is compatible with the truck.
            if (isCompatible(container)) {
                Port port = this.getCurrentPort(); // Get the current port associated with the vehicle.
                this.calculateRemainingCapacity(container.getWeight()); // Update the remaining capacity of the vehicle after loading the container.
                this.getListOfContainers().add(container); // Add the container to the list of containers on the vehicle.
                // Update the count of containers of the same type on the vehicle.
                this.getContainerCount().put(container.getType(), this.getContainerCount().getOrDefault(container.getType(), 0) + 1);
                this.addNumberOfContainers(1); // Increment the total number of containers on the vehicle.
                container.setCurrentPort(null); // Set the current port of the container to null, indicating it's on the vehicle.
                port.getListOfContainers().remove(container); // Remove the container from the list of containers at the current port.
                port.addRemainingCapacity(container.getWeight()); // Increase the remaining capacity of the current port after removing the container.
                port.minusNumberOfContainer(1); // Decrease the count of containers at the current port.
                // Update the information of the port, vehicle, and container in the data manager.
                DataManager.updatePort(port);
                DataManager.updateVehicle(this);
                DataManager.updateContainer(container);
                // Show a success message indicating that the container is loaded onto the vehicle.
                JOptionPane.showMessageDialog(null, "Container is loaded onto the vehicle", "Success", JOptionPane.INFORMATION_MESSAGE);
                return true; // Return true to indicate successful loading.
            } else {
                // Show an error message if the container type is not compatible with the vehicle.
                JOptionPane.showMessageDialog(null, "Container type is not compatible", "Failed", JOptionPane.ERROR_MESSAGE);
                return false; // Return false to indicate loading failure due to incompatible container type.
            }
        } else {
            // Show an error message if there is not enough storage capacity in the vehicle for the container.
            JOptionPane.showMessageDialog(null, "Vehicle storage is not enough to contain this vehicle", "Failed", JOptionPane.ERROR_MESSAGE);
        }
        return false; // Return false to indicate loading failure due to insufficient capacity.
    }


    // Define an abstract method to check if a container is compatible with the truck.
    public abstract boolean isCompatible(Container container);

    // Override the calulateRequiredFuel method to calculate the required fuel for a trip to a destination port.
    @Override
    public double calculateRequiredFuel(Port destinatedPort) {
        double requiredFuel = 0;
        double distance = Port.calculateDistance(this.getCurrentPort(), destinatedPort);
        double fuelForDistanceOnly = distance * 2;
        for (Container c : getListOfContainers()) {
            requiredFuel += c.getFuelConsumption(Container.VehicleType.TRUCK) * distance;
        }
        requiredFuel += fuelForDistanceOnly;
        System.out.println(requiredFuel);
        return requiredFuel;
    }

    // Override the moveToPort method to move the truck to a destination port.
    @Override
    public void moveToPort(Port destinatedPort) {
        if (destinatedPort.isLandable()) {
            super.moveToPort(destinatedPort);
        } else {
            JOptionPane.showMessageDialog(null, "This Port doesn't support truck", "Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
