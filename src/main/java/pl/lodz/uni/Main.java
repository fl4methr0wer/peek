package pl.lodz.uni;

import javax.swing.*;
import javax.swing.Timer;

import pl.lodz.uni.core.*;
import pl.lodz.uni.core.controller.*;
import pl.lodz.uni.core.service.*;
import pl.lodz.uni.ui.RangePanel;
import pl.lodz.uni.ui.SaveReportHandler;
import pl.lodz.uni.ui.Window;

import java.util.ArrayList;
import java.util.List;


public class Main {
    private static JFrame window;
    private static JPanel mainPanel;
    private static IProcessorService processorService;
    private static IFanService fanService;
    private static IMemoryService memoryService;
    private static IBatteryService batteryService;
    private static SaveReportHandler reportHandler;
    private static NotifierAggregate notifierAggregate = new NotifierAggregate();
    private static Timer timer;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            configureWindow();
            configureMainPanel();
            configureUpdateTimer();
            configureServices();
            configureLogReporter();

            // Create update notifiers and UI panels
            createProcessorTempNotifierAndUIPresenter();
            createProcessorUsageNotifierAndUIPresenter();
            createMemoryNotifierAndUIPresenter();
            createFansNotifierAndUIPresenter();
            createBatteryNotifierAndUIPresenter();

            // Save reports button
            if (reportHandler != null) {
                JButton saveButton = new JButton("Save Reports");
                saveButton.addActionListener(reportHandler::handleSaveReports);
                mainPanel.add(saveButton);
            }

            window.pack();
            window.setVisible(true);
        });
    }

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

    private static void configureUpdateTimer() {
        timer = new Timer(1000, (e) -> notifierAggregate.notifyAllPresenters());
        timer.start();
    }

    private static void configureServices() {
        processorService = new ProcessorService();
        fanService = new FanService();
        memoryService = new MemoryService();
        batteryService = new BatteryService();
    }

    private static void configureLogReporter() {
        reportHandler = new SaveReportHandler(window);
        reportHandler.registerReporter((Reporter) processorService);
        reportHandler.registerReporter((Reporter) fanService);
        reportHandler.registerReporter((Reporter) memoryService);
        reportHandler.registerReporter((Reporter) batteryService);
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