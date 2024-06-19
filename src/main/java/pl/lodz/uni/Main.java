package pl.lodz.uni;

import javax.swing.*;

import pl.lodz.uni.core.BatteryUpdateController;
import pl.lodz.uni.core.ProgressPresenter;
import pl.lodz.uni.core.service.*;
import pl.lodz.uni.core.UpdateController;
import pl.lodz.uni.ui.RangePanel;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame window = new JFrame("System Usage");
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Create a panel to hold the RangePanels vertically
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

            // Add the main panel to a scroll pane for scrolling
            JScrollPane scrollPane = new JScrollPane(mainPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            window.add(scrollPane);


            // Create the SystemUsageService implementation
            IProcessorService processorService = new ProcessorService();
            IFanService IFanService = new FanService();
            IMemoryService memoryService = new MemoryService();

            // Create the UpdateService
            UpdateController updateController = new UpdateController(processorService, IFanService, memoryService);

            // Create and add RangePanel instances
            RangePanel cpuPanel = new RangePanel();
            cpuPanel.setName("CPU: ");
            mainPanel.add(cpuPanel);
            // Use the UpdateService to update the CPU panel periodically
            updateController.notifyCPUUsage(1000, cpuPanel);

            RangePanel cpuTemp = new RangePanel();
            mainPanel.add(cpuTemp);
            updateController.notifyCPUTemperature(1000, cpuTemp);

            RangePanel memoryPanel = new RangePanel();
            memoryPanel.setName("RAM: ");
            mainPanel.add(memoryPanel);
            updateController.notifyMemoryUsage(1000, memoryPanel);

            int anountOfFans = IFanService.getAmountOfFans();
            List<ProgressPresenter> fanPanels = new ArrayList<>();
            for (int i = 0; i < anountOfFans; i++) {
                RangePanel fanPanel = new RangePanel();
                fanPanel.setName("Fan " +(i+1));
                fanPanels.add(fanPanel);
            }

            updateController.notifyFanRPM(1000, fanPanels);
            for (ProgressPresenter fanPresenter : fanPanels) {
                mainPanel.add((RangePanel) fanPresenter);
            }

            IBatteryService batteryService = new BatteryService();
            BatteryUpdateController batteryController = new BatteryUpdateController(batteryService);
            ProgressPresenter batteryPanel = new RangePanel();
            mainPanel.add((RangePanel) batteryPanel);
            batteryController.notify(1000, batteryPanel);

            // Adjust window size and make it visible
            window.pack();
            window.setVisible(true);
        });
    }
}
