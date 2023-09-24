package portmanagementsystem.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginFrame {
    private static JFrame mainFrame;
    private static CardLayout cardLayout;
    private static JPanel cardPanel;

    public LoginFrame() {
        // Create a Frame for logging in
        mainFrame = new JFrame("Port Management System");
        mainFrame.setSize(600, 500);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardPanel = new JPanel(new CardLayout());
        cardLayout = (CardLayout) cardPanel.getLayout();

        // Add the welcome panel into the frame
        WelcomeScreen welcomeScreen = new WelcomeScreen();
        cardPanel.add(welcomeScreen.getMainPanel(), "WelcomePanel");

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
        messagePanel.add(new JLabel("Group: Group 35"));
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

    public static JFrame getMainFrame() {
        return mainFrame;
    }
}
