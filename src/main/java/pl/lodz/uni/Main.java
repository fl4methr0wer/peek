package pl.lodz.uni;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.Timer;

import pl.lodz.uni.core.*;
import pl.lodz.uni.core.controller.*;
import pl.lodz.uni.core.service.*;
import pl.lodz.uni.ui.RangePanel;
import pl.lodz.uni.ui.Window;

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
        window = new Window("System Usage");
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

            // Create update notifiers and UI panels
            createProcessorTempNotifierAndUIPresenter();
            createProcessorUsageNotifierAndUIPresenter();
            createMemoryNotifierAndUIPresenter();
            createFansNotifierAndUIPresenter();
            createBatteryNotifierAndUIPresenter();

            // Save reports button
            JButton saveButton = new JButton("Save Reports");
            saveButton.addActionListener(Main::handleSaveReports);
            mainPanel.add(saveButton);

            // configure Timer notifying Presenters
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

    private static void createProcessorTempNotifierAndUIPresenter() {
        RangePanel cpuTempPanel = new RangePanel();
        mainPanel.add(cpuTempPanel);

        ProcessorTempNotifier processorTempNotifier = new ProcessorTempNotifier(processorService);
        processorTempNotifier.registerPresenter(cpuTempPanel);
        notifierAggregate.add(processorTempNotifier);
    }

    private static void createProcessorUsageNotifierAndUIPresenter() {
        RangePanel cpuPercentagePanel = new RangePanel();
        mainPanel.add(cpuPercentagePanel);

        ProcessorUsageNotifier processorUsageNotifier = new ProcessorUsageNotifier(processorService);
        processorUsageNotifier.registerPresenter(cpuPercentagePanel);
        notifierAggregate.add(processorUsageNotifier);
    }

    private static void createMemoryNotifierAndUIPresenter() {
        RangePanel memoryPanel = new RangePanel();
        mainPanel.add(memoryPanel);

        MemoryUpdateNotifier memoryUpdateNotifier = new MemoryUpdateNotifier(memoryService);
        memoryUpdateNotifier.registerPresenter(memoryPanel);
        notifierAggregate.add(memoryUpdateNotifier);
    }

    private static void createFansNotifierAndUIPresenter() {
        List<Presenter> fanPanels = new ArrayList<>();

        FanUpdateNotifier fanUpdateNotifier = new FanUpdateNotifier(fanService);
        for (int i = 0; i < fanService.getAmountOfFans(); i++) {
            RangePanel fanPanel = new RangePanel();
            fanUpdateNotifier.registerPresenter(fanPanel);
            fanPanels.add(fanPanel);
        }

        for (Presenter fanPresenter : fanPanels) {
            mainPanel.add((RangePanel) fanPresenter);
        }
        notifierAggregate.add(fanUpdateNotifier);
    }

    private static void createBatteryNotifierAndUIPresenter() {
        Presenter batteryPanel = new RangePanel();
        mainPanel.add((RangePanel) batteryPanel);

        BatteryUpdateNotifier batteryController = new BatteryUpdateNotifier(batteryService);
        batteryController.registerPresenter(batteryPanel);
        notifierAggregate.add(batteryController);
    }
}