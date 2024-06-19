package pl.lodz.uni;

import javax.swing.*;

import pl.lodz.uni.core.*;
import pl.lodz.uni.core.service.*;
import pl.lodz.uni.ui.RangePanel;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static JFrame window;
    private static JPanel mainPanel;
    private static IProcessorService processorService;
    private static IFanService fanService;
    private static IMemoryService memoryService;
    private static IBatteryService batteryService;

    private static void configureWindow() {
        window = new JFrame("System Usage");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static void configureMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        window.add(scrollPane);
    }

    private static void configureServices() {
        processorService = new ProcessorService();
        fanService = new FanService();
        memoryService = new MemoryService();
        batteryService = new BatteryService();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            configureWindow();
            configureMainPanel();
            configureServices();

            // CPU
            ProcessorUpdateController processorUpdateController = new ProcessorUpdateController(processorService);
            RangePanel cpuPercentagePanel = new RangePanel();
            mainPanel.add(cpuPercentagePanel);
            processorUpdateController.notifyCPUUsage(1000, cpuPercentagePanel);

            RangePanel cpuTempPanel = new RangePanel();
            mainPanel.add(cpuTempPanel);
            processorUpdateController.notifyCPUUsage(1000, cpuTempPanel);


            // RAM
            MemoryUpdateController memoryUpdateController = new MemoryUpdateController(memoryService);

            RangePanel memoryPanel = new RangePanel();
            mainPanel.add(memoryPanel);
            memoryUpdateController.notifyMemoryUsage(1000, memoryPanel);

            // FANS
            FanUpdateController fanUpdateController = new FanUpdateController(fanService);

            int anountOfFans = fanService.getAmountOfFans();
            List<ProgressPresenter> fanPanels = new ArrayList<>();
            for (int i = 0; i < anountOfFans; i++) {
                RangePanel fanPanel = new RangePanel();
                fanPanels.add(fanPanel);
            }

            fanUpdateController.notifyFanRPM(1000, fanPanels);
            for (ProgressPresenter fanPresenter : fanPanels) {
                mainPanel.add((RangePanel) fanPresenter);
            }

            // BATTERY
            BatteryUpdateController batteryController = new BatteryUpdateController(batteryService);
            ProgressPresenter batteryPanel = new RangePanel();
            mainPanel.add((RangePanel) batteryPanel);
            batteryController.notify(1000, batteryPanel);


            window.pack();
            window.setVisible(true);
        });
    }
}
