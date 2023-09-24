package portmanagementsystem.models;

import java.io.Serial;
import java.io.Serializable;

// Define a class named BasicTruck that extends the Truck class and implements the Serializable interface.
public class BasicTruck extends Truck implements Serializable {

    // SerialVersionUID is used for version control during deserialization.
    @Serial
    private static final long serialVersionUID = 321L;

    // Constructor for the BasicTruck class, which takes parameters such as name, carrying capacity, fuel capacity, and current port.
    public BasicTruck(String name, double carryingCapacity, double fuelCapacity, Port currentPort) {
        // Call the constructor of the parent class (Truck) using super().
        super(name, carryingCapacity, fuelCapacity, currentPort);

        // Set the vehicle type for this BasicTruck instance.
        setVehicleType("BASIC TRUCK");
    }

    // Override the isCompatible method from the parent class (Truck).
    @Override
    public boolean isCompatible(Container container) {
        // Check if the container type is one of the following: OPEN_SIDE, OPEN_TOP, or DRY_STORAGE.
        return container.getType() == Container.ContainerType.OPEN_SIDE ||
                container.getType() == Container.ContainerType.OPEN_TOP ||
                container.getType() == Container.ContainerType.DRY_STORAGE;
    }
}

