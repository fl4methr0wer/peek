package pl.lodz.uni;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import pl.lodz.uni.core.*;
import pl.lodz.uni.core.controller.BatteryUpdateController;
import pl.lodz.uni.core.controller.FanUpdateController;
import pl.lodz.uni.core.controller.MemoryUpdateController;
import pl.lodz.uni.core.controller.ProcessorUpdateController;
import pl.lodz.uni.core.service.*;
import pl.lodz.uni.ui.RangePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    private static JFrame window;
    private static JPanel mainPanel;
    private static IProcessorService processorService;
    private static IFanService fanService;
    private static IMemoryService memoryService;
    private static IBatteryService batteryService;
    private static List<Reporting> logReporters = new ArrayList<>();

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
        logReporters.add((Reporting) processorService);

        fanService = new FanService();
        logReporters.add((Reporting) fanService);

        memoryService = new MemoryService();
        logReporters.add((Reporting) memoryService);

        batteryService = new BatteryService();
        logReporters.add((Reporting) batteryService);
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

            // Save reports button
            JButton saveButton = new JButton("Save Reports");
            saveButton.addActionListener(Main::handleSaveReports);
            mainPanel.add(saveButton);


            window.pack();
            window.setVisible(true);
        });
    }

    private static void handleSaveReports(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Report");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        fileChooser.addChoosableFileFilter(filter);

        int userSelection = fileChooser.showSaveDialog(window);
        if (userSelection != JFileChooser.APPROVE_OPTION)
            return;
        File fileToSave = fileChooser.getSelectedFile();
        String filePath = fileToSave.getAbsolutePath();

        FileReportLogger reportLogger = new FileReportLogger(filePath);
        reportLogger.accept(logReporters);

        // Optionally show a message dialog after saving
        JOptionPane.showMessageDialog(window,
                "Report saved to " + filePath,
                "Reports saved",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
