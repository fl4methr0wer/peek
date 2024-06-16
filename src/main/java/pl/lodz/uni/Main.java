package pl.lodz.uni;


import pl.lodz.uni.ui.RangePanel;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create the main window frame
            JFrame frame = new JFrame("System Usage");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Create a panel to hold the RangePanels vertically
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

            // Create and add RangePanel instances
            RangePanel cpuPanel = new RangePanel();
            cpuPanel.setName("CPU:");
            cpuPanel.setValue("10.0 %");
            cpuPanel.setProgress(25); // Set progress (example value)
            mainPanel.add(cpuPanel);

            RangePanel memoryPanel = new RangePanel();
            memoryPanel.setName("Memory:");
            memoryPanel.setValue("10 MB");
            memoryPanel.setProgress(50); // Set progress (example value)
            mainPanel.add(memoryPanel);

            RangePanel diskPanel = new RangePanel();
            diskPanel.setName("Disk:");
            diskPanel.setValue("50 GB");
            diskPanel.setProgress(75); // Set progress (example value)
            mainPanel.add(diskPanel);

            RangePanel networkPanel = new RangePanel();
            networkPanel.setName("Network:");
            networkPanel.setValue("1.5 Mbps");
            networkPanel.setProgress(100); // Set progress (example value)
            mainPanel.add(networkPanel);

            // Add the main panel to a scroll pane for scrolling
            JScrollPane scrollPane = new JScrollPane(mainPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            frame.add(scrollPane);

            // Adjust frame size and make it visible
            frame.pack();
            frame.setVisible(true);
        });
    }
}
