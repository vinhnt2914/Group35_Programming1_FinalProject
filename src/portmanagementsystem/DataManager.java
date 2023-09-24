package portmanagementsystem;

import portmanagementsystem.models.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataManager {
    // Lists for storing various data types
    private static List<Container> containerList = new ArrayList<>();
    private static List<Port> portList = new ArrayList<>();
    private static List<Vehicle> vehicleList = new ArrayList<>();
    private static List<Trip> tripList = new ArrayList<>();
    private static List<Manager> managerList = new ArrayList<>();

    // Write the list of containers to a file
    public static void writeContainers() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("src/data/containers.obj");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(DataManager.getContainerList());
        objectOutputStream.close();
        System.out.println("Containers written successfully");
    }

    // Read the list of containers from a file and update the DataManager's container list
    public static void readContainers() throws IOException {
        List<Container> containersToRead;
        File file = new File("src/data/containers.obj");
        if (!file.exists()) {
            // File does not exist, no action needed
            return;
        }
        FileInputStream fileInputStream = new FileInputStream("src/data/containers.obj");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        try {
            containersToRead = (List<Container>) objectInputStream.readObject();
            System.out.println("Containers read!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Overwrite data of ContainerList with the loaded data
        DataManager.getContainerList().clear();
        DataManager.getContainerList().addAll(containersToRead);
    }

    // Update the data of a specific container and save it back to the file
    public static void updateContainer(Container container) {
        int index = DataManager.getContainerList().indexOf(container);
        if (index != -1) {
            DataManager.getContainerList().set(index, container);
            try {
                DataManager.writeContainers();
                System.out.println("Container update success");
            } catch (IOException e) {
                System.out.println("Container update fail");
                throw new RuntimeException(e);
            }
        }
    }

    // Write the list of ports to a file
    public static void writePorts() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("src/data/ports.obj");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(DataManager.getPortList());
        objectOutputStream.close();
        System.out.println("Ports written successfully");
    }

    // Read the list of ports from a file and update the DataManager's port list
    public static void readPorts() throws IOException {
        List<Port> portsToRead;
        File file = new File("src/data/ports.obj");
        if (!file.exists()) {
            // File does not exist, no action needed
            return;
        }
        FileInputStream fileInputStream = new FileInputStream("src/data/ports.obj");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        try {
            portsToRead = (List<Port>) objectInputStream.readObject();
            System.out.println("Ports read!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Overwrite data of PortList with the loaded data
        DataManager.getPortList().clear();
        DataManager.getPortList().addAll(portsToRead);
    }

    // Update the data of a specific port and save it back to the file
    public static void updatePort(Port port) {
        int index = DataManager.getPortList().indexOf(port);
        if (index != -1) {
            DataManager.getPortList().set(index, port);
            try {
                DataManager.writePorts();
                System.out.println("Port update successfully");
            } catch (IOException e) {
                System.out.println("Port update unsuccessfully");
                throw new RuntimeException(e);
            }
        }
    }

    // Write the list of vehicles to a file
    public static void writeVehicles() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("src/data/vehicles.obj");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(DataManager.getVehicleList());
        objectOutputStream.close();
        System.out.println("Vehicles written successfully");
    }

    // SIMILAR CONCEPT IS APPLIED TO OTHER FUNCTION WITH THE SAME NAME


    public static void readVehicles() throws IOException{
        List<Vehicle> vehiclesToRead;
        File file = new File("src/data/vehicles.obj");
        if (!file.exists()) {
            // File does not exist
            return;
        }
        FileInputStream fileInputStream = new FileInputStream("src/data/vehicles.obj");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        try {
            vehiclesToRead = (List<Vehicle>) objectInputStream.readObject();
            System.out.println("Vehicles read!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Overwrite data of ContainerList
        DataManager.getVehicleList().clear();
        DataManager.getVehicleList().addAll(vehiclesToRead);
    }

    public static void updateVehicle(Vehicle vehicle) {
        int index = DataManager.getVehicleList().indexOf(vehicle);
        if (index != -1) {
            DataManager.getVehicleList().set(index, vehicle);
            try {
                DataManager.writeVehicles();
                System.out.println("Vehicle update successfully");
            } catch (IOException e) {
                System.out.println("Vehicle update unsuccessfully");
                throw new RuntimeException(e);
            }
        }
    }

    public static void writeTrips() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("src/data/trips.obj");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(DataManager.getTripList());
        objectOutputStream.close();
        System.out.println("Trips written successfully");
    }

    public static void readTrips() throws IOException{
        List<Trip> tripsToRead;
        File file = new File("src/data/trips.obj");
        if (!file.exists()) {
            // File does not exist
            return;
        }
        FileInputStream fileInputStream = new FileInputStream("src/data/trips.obj");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        try {
            tripsToRead = (List<Trip>) objectInputStream.readObject();
            System.out.println("Trips read!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Overwrite data of ContainerList
        DataManager.getTripList().clear();
        DataManager.getTripList().addAll(tripsToRead);
    }

    public static void updateTrip(Trip trip) {
        int index = DataManager.getTripList().indexOf(trip);
        if (index != -1) {
            DataManager.getTripList().set(index, trip);
            try {
                DataManager.writeTrips();
                System.out.println("Trip update successfully");
            } catch (IOException e) {
                System.out.println("Trip update unsuccessfully");
                throw new RuntimeException(e);
            }
        }
    }

    public static void writeManagers() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("src/data/managers.obj");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(DataManager.getManagerList());
        objectOutputStream.close();
        System.out.println("Managers written successfully");
    }

    public static void readManagers() throws IOException{
        List<Manager> managersToRead;
        File file = new File("src/data/managers.obj");
        if (!file.exists()) {
            // File does not exist
            return;
        }
        FileInputStream fileInputStream = new FileInputStream("src/data/managers.obj");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        try {
            managersToRead = (List<Manager>) objectInputStream.readObject();
            System.out.println("Managers read!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Overwrite data of ContainerList
        DataManager.getManagerList().clear();
        DataManager.getManagerList().addAll(managersToRead);
    }

    public static void updateManager(Manager manager) {
        int index = DataManager.getManagerList().indexOf(manager);
        if (index != -1) {
            DataManager.getManagerList().set(index, manager);
            try {
                DataManager.writeManagers();
                System.out.println("Manager update successfully");
            } catch (IOException e) {
                System.out.println("Manager update unsuccessfully");
                throw new RuntimeException(e);
            }
        }
    }

    // Calculate the total fuel consumption per day from the list of trips.
    public static Map<LocalDate, Double> calculateTotalFuelConsumptionPerDay() {
        // Create a map to store the total fuel consumption per day.
        Map<LocalDate, Double> totalFuelConsumptionMap = new HashMap<>();

        // Iterate through the list of trips.
        for (Trip trip : tripList) {
            // Get the departure date and fuel consumption of the trip.
            LocalDate departureDate = trip.getDepartureDate();
            double fuelConsumption = trip.getFuelConsumption();

            // Update the total fuel consumption for the departure date in the map.
            totalFuelConsumptionMap.put(departureDate, totalFuelConsumptionMap.getOrDefault(departureDate, 0.0) + fuelConsumption);
        }

        return totalFuelConsumptionMap;
    }

    // Delete old trips from the list that are more than 7 days old and update the file.
    public static void deleteTripHistory() {
        try {
            // Get the current date.
            LocalDate currentDate = LocalDate.now();
            // Create an iterator to iterate through the list of trips.
            Iterator<Trip> tripIterator = DataManager.getTripList().iterator();
            // Iterate through the trips.
            while (tripIterator.hasNext()) {
                Trip trip = tripIterator.next();
                LocalDate arrivalDate = trip.getArrivalDate();
                // Calculate the number of days difference between the current date and departure date.
                int daysDifference = Math.abs(currentDate.until(arrivalDate).getDays());
                // Check if the trip is more than 7 days old and remove it using the iterator.
                if (daysDifference > 7) {
                    tripIterator.remove(); // Remove the current trip using the iterator
                    System.out.println("Trip deleted");
                }
            }
            // Write the updated list of trips to the file.
            DataManager.writeTrips();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Container> getContainerList() {
        return containerList;
    }

    public static List<Port> getPortList() {
        return portList;
    }

    public static List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public static List<Trip> getTripList() {
        return tripList;
    }

    public static List<Manager> getManagerList() {
        return managerList;
    }
}
