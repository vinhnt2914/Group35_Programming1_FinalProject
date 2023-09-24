package portmanagementsystem.gui;

import portmanagementsystem.DataManager;
import portmanagementsystem.models.Container;

import javax.swing.*;
import java.util.*;

public class CalculateContainerWeight implements PanelAction{
    private JPanel mainPanel;
    private JList<String> listView;
    private DefaultListModel<String> containerListModel;
    private HashMap<Container.ContainerType, Double> totalContainerWeight;


    public CalculateContainerWeight() {
        load();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    @Override
    public void load() {
        containerListModel = new DefaultListModel<>();
        // Initialize or reset the totalContainerWeight HashMap
        totalContainerWeight = new HashMap<>();
        // Iterate through the list of containers and calculate the total weight for each type
        for (Container container : DataManager.getContainerList()) {
            Container.ContainerType containerType = container.getType();
            double containerWeight = container.getWeight();
            // If the container type is already in the HashMap, add the weight to the existing total
            // Otherwise, create a new entry for the container type
            totalContainerWeight.put(containerType, totalContainerWeight.getOrDefault(containerType, 0.0) + containerWeight);
        }
        List<String> containerWeightStrings = new ArrayList<>();
        // Add content to the display string
        for (Map.Entry<Container.ContainerType, Double> entry : totalContainerWeight.entrySet()) {
            Container.ContainerType containerType = entry.getKey();
            double totalWeight = entry.getValue();
            String displayString = containerType + ": " + totalWeight;
            containerWeightStrings.add(displayString);
        }
        containerListModel.addAll(containerWeightStrings);
        listView.setModel(containerListModel);
    }
}
