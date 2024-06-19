package pl.lodz.uni;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.Timer;

import pl.lodz.uni.core.*;
import pl.lodz.uni.core.controller.*;
import pl.lodz.uni.core.service.*;
import pl.lodz.uni.ui.RangePanel;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import java.io.File;

public class Main {
    private static JFrame window;
    private static JPanel mainPanel;
    private static IProcessorService processorService;
    private static IFanService fanService;
    private static IMemoryService memoryService;
    private static IBatteryService batteryService;
    private static List<Reporter> logReporters = new ArrayList<>();
    private static NotifierAggregate notifierAggregate = new NotifierAggregate();
    private static Timer timer;

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
        logReporters.add((Reporter) processorService);

        fanService = new FanService();
        logReporters.add((Reporter) fanService);

        memoryService = new MemoryService();
        logReporters.add((Reporter) memoryService);

        batteryService = new BatteryService();
        logReporters.add((Reporter) batteryService);
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
            memoryPanel.setName("RAM");
            mainPanel.add(memoryPanel);

            memoryUpdateController.registerProgressPresenter(memoryPanel);
            notifierAggregate.add(memoryUpdateController);

            // FANS
            FanUpdateController fanUpdateController = new FanUpdateController(fanService);

            int anountOfFans = fanService.getAmountOfFans();
            List<ProgressPresenter> fanPanels = new ArrayList<>();
            for (int i = 0; i < anountOfFans; i++) {
                RangePanel fanPanel = new RangePanel();
                fanUpdateController.registerProgressPresenter(fanPanel);
                fanPanels.add(fanPanel);
            }

            notifierAggregate.add(fanUpdateController);

            for (ProgressPresenter fanPresenter : fanPanels) {
                mainPanel.add((RangePanel) fanPresenter);
            }

            // BATTERY
            BatteryUpdateController batteryController = new BatteryUpdateController(batteryService);
            ProgressPresenter batteryPanel = new RangePanel();
            mainPanel.add((RangePanel) batteryPanel);
            notifierAggregate.add(batteryController);

            batteryController.registerProgressPresenter(batteryPanel);
            notifierAggregate.add(batteryController);

            // Save reports button
            JButton saveButton = new JButton("Save Reports");
            saveButton.addActionListener(Main::handleSaveReports);
            mainPanel.add(saveButton);

            timer = new Timer(1000, (e) -> notifierAggregate.notifyAllPresenters());
            timer.start();

            window.pack();
            window.setVisible(true);
        });
    }

    private static void handleSaveReports(ActionEvent e) {
        timer.stop();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Report");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        fileChooser.addChoosableFileFilter(filter);

        int userSelection = fileChooser.showSaveDialog(window);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            timer.restart();
            return;
        }

        File fileToSave = fileChooser.getSelectedFile();
        String filePath = fileToSave.getAbsolutePath();

        FileReportLogger reportLogger = new FileReportLogger(filePath);
        reportLogger.accept(logReporters);

        // Optionally show a message dialog after saving
        JOptionPane.showMessageDialog(window,
                "Report saved to " + filePath,
                "Reports saved",
                JOptionPane.INFORMATION_MESSAGE);
        timer.restart();
    }
}
