package portmanagementsystem.gui;

import portmanagementsystem.DataManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class AppFrame {
    private static JFrame mainFrame;
    private static CardLayout cardLayout;
    private static JPanel cardPanel;

    public AppFrame(MenuBarFrame menuBar) {
        createAppFrame(menuBar);
        // Load data into the app
        try {
            DataManager.readManagers();
            DataManager.readPorts();
            DataManager.readContainers();
            DataManager.readVehicles();
            DataManager.readTrips();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Delete trips that are older than 7 days
        // Comment this out if you don't want to delete
//        DataManager.deleteTripHistory();

    } 

    // Create the App Frame with Card Layout
    public void createAppFrame(MenuBarFrame menuBar) {
        mainFrame = new JFrame("Port Management System");
        mainFrame.setSize(600, 500);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainFrame.setJMenuBar(menuBar.getMenuBar());

        cardPanel = new JPanel(new CardLayout());
        cardLayout = (CardLayout) cardPanel.getLayout();

        int frameWidth = 800;
        int frameHeight = 600;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frameWidth) / 2;
        int y = (screenSize.height - frameHeight) / 2;

        // ... Add more panels to cardPanel here
        mainFrame.setSize(frameWidth, frameHeight);
        mainFrame.setLocation(x, y);
        mainFrame.add(cardPanel);
        mainFrame.setVisible(true);

        // Add a WindowListener to handle the exit action
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                showExitMessage();
            }
        });
    }

    private void showExitMessage() {
        // Create a custom JPanel to display the information
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new GridLayout(9, 1));

        // Add labels with the information
        messagePanel.add(new JLabel("Thank you for using our application!"));
        messagePanel.add(new JLabel("COSC2081 GROUP ASSIGNMENT"));
        messagePanel.add(new JLabel("CONTAINER PORT MANAGEMENT SYSTEM"));
        messagePanel.add(new JLabel("Instructors: Mr. Minh Vu & Dr. Phong Ngo"));
        messagePanel.add(new JLabel("Group: group27"));
        messagePanel.add(new JLabel("s3979366, Nguyen The Vinh"));
        messagePanel.add(new JLabel("s3978365, Pham Thanh Mai"));
        messagePanel.add(new JLabel("s3975154, Nguyen Ba Lam Quang Thai"));
        messagePanel.add(new JLabel("s3979425, Nguyen Thanh Tung"));

        // Show a JOptionPane with the custom message
        JOptionPane.showMessageDialog(
                mainFrame,
                messagePanel,
                "Goodbye =))",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public static JPanel getCardPanel() {
        return cardPanel;
    }

    public static CardLayout getCardLayout() {
        return cardLayout;
    }
}
