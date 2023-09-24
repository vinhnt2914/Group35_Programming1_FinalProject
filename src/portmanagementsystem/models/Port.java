package portmanagementsystem.models;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Define a class named Port that implements the Serializable interface.
public class Port implements Serializable {

    // SerialVersionUID is used for version control during deserialization.
    @Serial
    private static final long serialVersionUID = 1L;

    // Define constants for Earth radius and the counter file path.
    private static final double EARTH_RADIUS_KM = 6371.0;
    private static final String COUNTER_FILE = "src/data/portCounter.txt";

    // Initialize the counter with the value loaded from the file.
    private static int counter = loadCounter();

    // Define instance variables for the Port class.
    private String id;
    private String name;
    private double latitude;
    private double longitude;
    private double storingCapacity;
    private boolean landingAbility;
    private int numberOfContainers = 0;
    private int numberOfVehicles = 0;
    private List<Container> listOfContainers;
    private List<Vehicle> listOfVehicles;
    private Manager manager;
    private double remainingCapacity;

    // Constructor for the Port class.
    public Port(String name, double latitude, double longitude, double storingCapacity, boolean landingAbility) {
        // Generate a unique port ID.
        this.id = generatePortId();
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.storingCapacity = storingCapacity;
        this.landingAbility = landingAbility;
        this.listOfContainers = new ArrayList<>();
        this.listOfVehicles = new ArrayList<>();
        this.remainingCapacity = storingCapacity;
        // Save the counter value to the file.
        Port.saveCounter();
    }

    // Check if containers can be loaded into the port.
    public boolean canLoadContainers() {
        return true;
    }

    // Generate a unique port ID.
    private static String generatePortId() {
        return "P-" + counter++;
    }

    // Calculate the distance between two ports using their latitude and longitude.
    public static double calculateDistance(Port port1, Port port2) {
        double lat1 = Math.toRadians(port1.getLatitude());
        double lon1 = Math.toRadians(port1.getLongitude());
        double lat2 = Math.toRadians(port2.getLatitude());
        double lon2 = Math.toRadians(port2.getLongitude());

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
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

    // Override the equals method to compare ports based on their IDs.
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Port port = (Port) obj;
        return id.equals(port.id);
    }

    // Override the hashCode method to generate a hash code based on the port's ID.
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    // Getter and setter methods for instance variables.
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getStoringCapacity() {
        return storingCapacity;
    }

    public void setStoringCapacity(double storingCapacity) {
        this.storingCapacity = storingCapacity;
    }

    public boolean isLandable() {
        return landingAbility;
    }

    public void setLandingAbility(boolean landingAbility) {
        this.landingAbility = landingAbility;
    }

    public int getNumberOfContainers() {
        return numberOfContainers;
    }

    public void setNumberOfContainers(int numberOfContainers) {
        this.numberOfContainers = numberOfContainers;
    }

    public void addNumberOfContainer(int number) {
        this.setNumberOfContainers(this.getNumberOfContainers() + number);
    }

    public void minusNumberOfContainer(int number) {
        this.setNumberOfContainers(this.getNumberOfContainers() - number);
    }

    public int getNumberOfVehicles() {
        return numberOfVehicles;
    }

    public void setNumberOfVehicles(int numberOfVehicles) {
        this.numberOfVehicles = numberOfVehicles;
    }

    public void addNumberOfVehicle(int number) {
        this.setNumberOfVehicles(this.getNumberOfVehicles() + number);
    }

    public void minusNumberOfVehicle(int number) {
        this.setNumberOfVehicles(this.getNumberOfVehicles() - number);
    }

    public List<Container> getListOfContainers() {
        return listOfContainers;
    }

    public List<Vehicle> getListOfVehicles() {
        return listOfVehicles;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public double getRemainingCapacity() {
        return remainingCapacity;
    }

    public void setRemainingCapacity(double remainingCapacity) {
        this.remainingCapacity = remainingCapacity;
    }

    public void calculateRemainingCapacity(double amount) {
        setRemainingCapacity(getRemainingCapacity() - amount);
    }

    public void addRemainingCapacity(double amount) {
        setRemainingCapacity(getRemainingCapacity() + amount);
    }

    // Get a string representation of the containers in the port.
    public String getContainersAsString() {
        StringBuilder result = new StringBuilder();
        for (Container container : listOfContainers) {
            result.append(container.getId()).append(", "); // Append container ID and a comma
        }
        // Remove the trailing comma and space, if there are containers
        if (result.length() > 0) {
            result.delete(result.length() - 2, result.length());
        }
        return result.toString();
    }

    // Get a string representation of the vehicles in the port.
    public String getVehiclesAsString() {
        StringBuilder result = new StringBuilder();
        for (Vehicle vehicle : listOfVehicles) {
            result.append(vehicle.getId()).append(", "); // Append vehicle ID and a comma
        }
        // Remove the trailing comma and space, if there are vehicles
        if (result.length() > 0) {
            result.delete(result.length() - 2, result.length());
        }
        return result.toString();
    }

    // Get a string representation of the manager's ID.
    public String getManagerIDAsString() {
        if (Objects.isNull(getManager())) {
            return "none";
        } else {
            return String.format("{%s}", getManager().getId());
        }
    }

    // Override the toString method to provide a string representation of the port.
    @Override
    public String toString() {
        return String.format("ID: %s, Name: %s, Latitude: %s, Longitude: %s, StoringCap: %.2f, RemainCap: %.2f, Landing: %s, Number Of Container: %s, Number Of Vehicle: %s, Containers: {%s}, Vehicle: {%s}, Manager: %s",
                getId(),
                getName(),
                getLatitude(),
                getLongitude(),
                getStoringCapacity(),
                getRemainingCapacity(),
                isLandable(),
                getNumberOfContainers(),
                getNumberOfVehicles(),
                getContainersAsString(),
                getVehiclesAsString(),
                getManagerIDAsString());
    }
}
