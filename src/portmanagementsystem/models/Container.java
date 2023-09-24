package portmanagementsystem.models;

import java.io.*;
import java.util.Map;
import java.util.Objects;

import static java.util.Map.*;

// Define a class named Container that implements the Serializable interface.
public class Container implements Serializable {
    // SerialVersionUID is used for version control during deserialization.
    @Serial
    private static final long serialVersionUID = 2L;

    // Define a constant for the counter file path.
    private static final String COUNTER_FILE = "src/data/containerCounter.txt";

    // Initialize the counter with the value loaded from the file.
    private static int counter = loadCounter();

    // Define instance variables for Container.
    private String id;
    private double weight;
    private ContainerType type;
    private Port currentPort;

    // Define maps for consumption rates of different container types for ships and trucks.
    private static final Map<ContainerType, Double> SHIP_CONSUMPTION_RATES = Map.ofEntries(
            entry(ContainerType.DRY_STORAGE, 3.5),
            entry(ContainerType.OPEN_TOP, 2.8),
            entry(ContainerType.OPEN_SIDE, 2.7),
            entry(ContainerType.REFRIGERATED, 4.5),
            entry(ContainerType.LIQUID, 4.8)
    );
    private static final Map<ContainerType, Double> TRUCK_CONSUMPTION_RATES = Map.ofEntries(
            entry(ContainerType.DRY_STORAGE, 4.6),
            entry(ContainerType.OPEN_TOP, 3.2),
            entry(ContainerType.OPEN_SIDE, 3.2),
            entry(ContainerType.REFRIGERATED, 5.4),
            entry(ContainerType.LIQUID, 5.3)
    );

    // Define enum for ContainerType.
    public enum ContainerType {
        DRY_STORAGE,
        OPEN_TOP,
        OPEN_SIDE,
        REFRIGERATED,
        LIQUID
    }

    // Define enum for VehicleType.
    public enum VehicleType {
        SHIP,
        TRUCK
    }

    // Constructor for the Container class.
    public Container(double weight, ContainerType type, Port currentPort) {
        // Generate a unique container ID.
        this.id = generateContainerId();
        this.weight = weight;
        this.type = type;
        this.currentPort = currentPort;
        // Save the counter value to the file.
        saveCounter();
    }

    // Generate a unique container ID.
    private static String generateContainerId() {
        return "C-" + counter++;
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

    // Get the consumption rate based on the vehicle type (ship or truck).
    public double getConsumptionRate(VehicleType vehicleType) {
        if (vehicleType.equals(VehicleType.TRUCK))
            return TRUCK_CONSUMPTION_RATES.get(this.getType());
        else
            return SHIP_CONSUMPTION_RATES.get(this.getType());
    }

    // Calculate the fuel consumption based on the vehicle type and container weight.
    public double getFuelConsumption(VehicleType vehicleType) {
        return getConsumptionRate(vehicleType) * this.weight;
    }

    // Getter and setter methods for instance variables.
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public ContainerType getType() {
        return type;
    }

    public void setType(ContainerType type) {
        this.type = type;
    }

    public Port getCurrentPort() {
        return currentPort;
    }

    public void setCurrentPort(Port currentPort) {
        this.currentPort = currentPort;
    }

    // Override the equals method to compare containers based on their IDs.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Container container = (Container) o;
        return Objects.equals(id, container.id);
    }

    // Override the hashCode method to generate a hash code based on the container's ID.
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Override the toString method to provide a string representation of the container.
    @Override
    public String toString() {
        if (Objects.isNull(getCurrentPort())) {
            return String.format("ID: %s, Weight: %.2f, Type: %s, Transporting\n", getId(), getWeight(), getType());
        } else {
            return String.format("ID: %s, Weight: %.2f, Type: %s, Current Port: {ID: %s, Name: %s}\n", getId(), getWeight(), getType(), getCurrentPort().getId(), getCurrentPort().getName());
        }
    }
}
