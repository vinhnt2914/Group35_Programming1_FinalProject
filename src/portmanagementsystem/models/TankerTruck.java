package portmanagementsystem.models;

import java.io.Serial;
import java.io.Serializable;

// Define a class named TankerTruck that extends the Truck class and implements the Serializable interface.
public class TankerTruck extends Truck implements Serializable {

    // SerialVersionUID is used for version control during deserialization.
    @Serial
    private static final long serialVersionUID = 323L;

    // Constructor for the TankerTruck class.
    public TankerTruck(String name, double carryingCapacity, double fuelCapacity, Port currentPort) {
        // Call the constructor of the superclass (Truck) and pass the provided parameters.
        super(name, carryingCapacity, fuelCapacity, currentPort);
        // Set the vehicle type to "TANKER TRUCK."
        setVehicleType("TANKER TRUCK");
    }

    // Override the isCompatible method to determine if this truck can transport a given container.
    @Override
    public boolean isCompatible(Container container) {
        // Check if the container type is "LIQUID."
        return container.getType() == Container.ContainerType.LIQUID;
    }
}
