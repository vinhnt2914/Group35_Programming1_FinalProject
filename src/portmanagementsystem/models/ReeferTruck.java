package portmanagementsystem.models;

import java.io.Serial;
import java.io.Serializable;

// Define a class named ReeferTruck that extends the Truck class and implements the Serializable interface.
public class ReeferTruck extends Truck implements Serializable {

    // SerialVersionUID is used for version control during deserialization.
    @Serial
    private static final long serialVersionUID = 322L;

    // Constructor for the ReeferTruck class.
    public ReeferTruck(String name, double carryingCapacity, double fuelCapacity, Port currentPort) {
        // Call the constructor of the superclass (Truck) and pass the provided parameters.
        super(name, carryingCapacity, fuelCapacity, currentPort);
        // Set the vehicle type to "REEFER TRUCK."
        setVehicleType("REEFER TRUCK");
    }

    // Override the isCompatible method to determine if this truck can transport a given container.
    @Override
    public boolean isCompatible(Container container) {
        // Check if the container type is "REFRIGERATED."
        return container.getType() == Container.ContainerType.REFRIGERATED;
    }
}

