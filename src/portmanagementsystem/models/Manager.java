package portmanagementsystem.models;

import java.io.*;

// Define a class named Manager that implements the Serializable interface.
public class Manager implements Serializable {

    // SerialVersionUID is used for version control during deserialization.
    @Serial
    private static final long serialVersionUID = 5L;

    // Define a constant for the counter file path.
    private static final String COUNTER_FILE = "src/data/managerCounter.txt";

    // Initialize the counter with the value loaded from the file.
    private static int counter = loadCounter();

    // Define instance variables for the Manager class.
    private String id;
    private String username;
    private String password;
    private Port controlPort;

    // Constructor for the Manager class.
    public Manager(String username, String password, Port controlPort) {
        // Generate a unique manager ID.
        this.id = generateManagerID();
        this.username = username;
        this.password = password;
        this.controlPort = controlPort;
        // Save the counter value to the file.
        Manager.saveCounter();
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

    // Generate a unique manager ID.
    private static String generateManagerID() {
        return "M-" + counter++;
    }

    // Getter and setter methods for instance variables.
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Port getControlPort() {
        return controlPort;
    }

    public void setControlPort(Port controlPort) {
        this.controlPort = controlPort;
    }

    public String getId() {
        return id;
    }

    // Override the toString method to provide a string representation of the manager.
    @Override
    public String toString() {
        return String.format("ID: %s, Username: %s, Password: %s, Control Port: {ID: %s, Name: %s}\n", getId(), getUsername(), getPassword(), getControlPort().getId(), getControlPort().getName());
    }

    // Override the equals method to compare managers based on their IDs.
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Manager manager = (Manager) obj;
        return id.equals(manager.id);
    }

    // Override the hashCode method to generate a hash code based on the manager's ID.
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
