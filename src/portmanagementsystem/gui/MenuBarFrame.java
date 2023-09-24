package portmanagementsystem.gui;

import javax.swing.*;

// Abstract class for menu bar
public abstract class MenuBarFrame {
    private JMenuBar menuBar;

    public MenuBarFrame() {
        createMenuBar();
    }

    // This method is meant to be implemented by subclasses to create menu bars.
    private void createMenuBar() {
        // Actual menu bar creation should be implemented by subclasses.
    }

    // Get the menu bar instance.
    public JMenuBar getMenuBar() {
        return menuBar;
    }
}
