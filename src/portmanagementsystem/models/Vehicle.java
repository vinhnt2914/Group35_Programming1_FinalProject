package portmanagementsystem.models;

import portmanagementsystem.DataManager;

import javax.swing.*;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

// Define an abstract class named Vehicle that implements the Serializable and VehicleAction interfaces.
public abstract class Vehicle implements Serializable, VehicleAction {

    // SerialVersionUID is used for version control during deserialization.
    @Serial
    private static final long serialVersionUID = 3L;

    // Define instance variables for the Vehicle class.
    protected String id;
    private String vehicleType;
    private String name;
    private double currentFuel;
    private double carryingCapacity;
    private double remainingCapacity;
    private double fuelCapacity;
    private Port currentPort;
    private int numberOfContainers;
    private List<Container> listOfContainers;
    private Map<Container.ContainerType, Integer> containerCount;

    // Constructor for the Vehicle class.
    public Vehicle(String name, double carryingCapacity, double fuelCapacity, Port currentPort) {
        this.name = name;
        this.currentFuel = fuelCapacity;
        this.carryingCapacity = carryingCapacity;
        this.remainingCapacity = carryingCapacity;
        this.fuelCapacity = fuelCapacity;
        this.currentPort = currentPort;
        this.listOfContainers = new ArrayList<>();
        this.containerCount = new HashMap<>();
    }

    // Method to move the vehicle to a destination port.
    public void moveToPort(Port destinatedPort) {
        double requiredFuel = this.calculateRequiredFuel(destinatedPort);
        if (this.getCurrentPort().equals(destinatedPort)) {
            System.out.println("Already at port!");
            JOptionPane.showMessageDialog(null, "Vehicle is already at port", "Cannot move to Port", JOptionPane.ERROR_MESSAGE);
        } else if (requiredFuel < this.getCurrentFuel()) {
            this.setCurrentFuel(this.getCurrentFuel() - requiredFuel);
            Trip trip = new Trip(this, LocalDate.now(), this.currentPort, destinatedPort, requiredFuel);
            DataManager.getTripList().add(trip);
            // Remove the vehicle from the old port.
            Port oldPort = this.getCurrentPort();
            oldPort.getListOfVehicles().remove(this);
            oldPort.minusNumberOfVehicle(1);
            // Set new vehicle current port and update the port
            this.setCurrentPort(destinatedPort);
            destinatedPort.getListOfVehicles().add(this);
            destinatedPort.addNumberOfVehicle(1);
            // Update info into files
            DataManager.updateVehicle(this);
            DataManager.updatePort(oldPort);
            DataManager.updatePort(destinatedPort);
            JOptionPane.showMessageDialog(null, "Vehicle moves to the port", "Success", JOptionPane.INFORMATION_MESSAGE);
            try {
                DataManager.writeTrips();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Not enough fuel to move to port");
            JOptionPane.showMessageDialog(null, "Vehicle doesn't have enough fuel", "Cannot move to Port", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to refuel the vehicle.
    public void refuel() {
        this.setCurrentFuel(this.getFuelCapacity());
        JOptionPane.showMessageDialog(null, "Vehicle has been refueled", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    // Method to unload containers from the vehicle.
    public void unloadContainers() {
        if (getListOfContainers().isEmpty()) {
            JOptionPane.showMessageDialog(null, "There are no containers on the vehicle",
                    "Cannot unload containers", JOptionPane.ERROR_MESSAGE);
        } else {
            System.out.println("DataManager container list: " + DataManager.getContainerList());

            Port port = this.getCurrentPort();

            for (Container container : DataManager.getContainerList()) {
                if (this.getListOfContainers().contains(container)) {
                    if (port.getRemainingCapacity() >= container.getWeight()) {
                        getListOfContainers().remove(container); // remove container from the vehicle
                        getContainerCount().put(container.getType(), getContainerCount().get(container.getType()) - 1); // remove container from the container type count
                        minusNumberOfContainer(1); // minus container count

                        container.setCurrentPort(port); // Reset the current Port of the container
                        DataManager.updateContainer(container); // Update the container info

                        port.getListOfContainers().add(container); // Add container to new port
                        port.addNumberOfContainer(1);
                        port.calculateRemainingCapacity(container.getWeight()); // Reduce port remaining capacity

                        System.out.println("Container " + container.getId() + " port set to " + port.getId());
                    }
                }
            }

            DataManager.updateVehicle(this);
            DataManager.updatePort(port);
        }
    }

    // Abstract method to calculate the required fuel for a trip to a destination port.
    public abstract double calculateRequiredFuel(Port destinatedPort);

    // Getter and setter for the 'name' field.
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and setter for the 'currentFuel' field.
    public double getCurrentFuel() {
        return currentFuel;
    }

    public void setCurrentFuel(double currentFuel) {
        this.currentFuel = currentFuel;
    }

    // Method to add fuel to the current fuel level.
    public void addCurrentFuel(double amount) {
        this.setCurrentFuel(getCurrentFuel() + amount);
    }

    // Getter and setter for the 'carryingCapacity' field.
    public double getCarryingCapacity() {
        return carryingCapacity;
    }

    public void setCarryingCapacity(double carryingCapacity) {
        this.carryingCapacity = carryingCapacity;
    }

    // Getter and setter for the 'fuelCapacity' field.
    public double getFuelCapacity() {
        return fuelCapacity;
    }

    public void setFuelCapacity(double fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    // Getter for the 'listOfContainers' field.
    public List<Container> getListOfContainers() {
        return listOfContainers;
    }

    // Getter for the 'containerCount' field.
    public Map<Container.ContainerType, Integer> getContainerCount() {
        return containerCount;
    }

    // Getter and setter for the 'currentPort' field.
    public Port getCurrentPort() {
        return currentPort;
    }

    public void setCurrentPort(Port currentPort) {
        this.currentPort = currentPort;
    }

    // Getter and setter for the 'vehicleType' field.
    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    // Getter for the 'id' field.
    public String getId() {
        return id;
    }

    // Getter and setter for the 'remainingCapacity' field.
    public double getRemainingCapacity() {
        return remainingCapacity;
    }

    public void setRemainingCapacity(double remainingCapacity) {
        this.remainingCapacity = remainingCapacity;
    }

    // Method to calculate remaining capacity after loading/unloading containers.
    public void calculateRemainingCapacity(double amount) {
        setRemainingCapacity(getRemainingCapacity() - amount);
    }

    // Method to add remaining capacity after loading/unloading containers.
    public void addRemainingCapacity(double amount) {
        setRemainingCapacity(getRemainingCapacity() + amount);
    }

    // Getter and setter for the 'numberOfContainers' field.
    public int getNumberOfContainers() {
        return numberOfContainers;
    }

    public void setNumberOfContainers(int numberOfContainers) {
        this.numberOfContainers = numberOfContainers;
    }

    // Method to add a number of containers.
    public void addNumberOfContainers(int number) {
        this.setNumberOfContainers(this.getNumberOfContainers() + number);
    }

    // Method to subtract a number of containers.
    public void minusNumberOfContainer(int number) {
        this.setNumberOfContainers(this.getNumberOfContainers() - number);
    }

    // Method to get a comma-separated string of container IDs.
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

    // Override the toString method to provide a string representation of the object.
    @Override
    public String toString() {
        return String.format("ID: %s, Type: %s, Name: %s, Current Fuel: %.2f, " +
                        "Fuel Capacity: %.2f, Carrying Capacity: %.2f, Remaining Capacity: %.2f, " +
                        "Current Port: {ID: %s, Name: %s}, Number of Container: %s, Containers: {%s}, Container Of Type: %s",
                getId(), getVehicleType(), getName(), getCurrentFuel(),
                getFuelCapacity(), getCarryingCapacity(), getRemainingCapacity(),
                getCurrentPort().getId(),
                getCurrentPort().getName(), getNumberOfContainers(),
                getContainersAsString(),
                getContainerCount());
    }

    // Override the equals method to compare objects based on their IDs.
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Vehicle vehicle = (Vehicle) obj;
        return id.equals(vehicle.id);
    }

    // Override the hashCode method to generate a hash code based on the ID.
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

