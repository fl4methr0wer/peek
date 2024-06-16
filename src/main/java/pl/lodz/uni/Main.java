package pl.lodz.uni;

import javax.swing.*;

import pl.lodz.uni.core.SystemUsageService;
import pl.lodz.uni.core.SystemUsageServiceImpl;
import pl.lodz.uni.core.UpdateController;
import pl.lodz.uni.ui.RangePanel;

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
            cpuPanel.setName("CPU: ");
            mainPanel.add(cpuPanel);

            // Add the main panel to a scroll pane for scrolling
            JScrollPane scrollPane = new JScrollPane(mainPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            frame.add(scrollPane);

            // Adjust frame size and make it visible
            frame.pack();
            frame.setVisible(true);

            // Create the SystemUsageService implementation
            SystemUsageService systemUsageService = new SystemUsageServiceImpl();

            // Create the UpdateService
            UpdateController updateController = new UpdateController(systemUsageService);

            // Use the UpdateService to update the CPU panel periodically
            updateController.notifyCPUUsage(1000, cpuPanel);
        });
    }
}
