package pl.lodz.uni.ui;

import pl.lodz.uni.core.FileReportLogger;
import pl.lodz.uni.core.ReportReceiver;
import pl.lodz.uni.core.controller.Reporter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SaveReportHandler {

    private final JFrame window;
    private final List<Reporter> logReporters = new ArrayList<>();

    public SaveReportHandler(JFrame window) {
        this.window = window;
    }

    public void registerReporter(Reporter reporter) {
        logReporters.add(reporter);
    }

    public void handleSaveReports(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Report");

        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Text Files", "txt");
        fileChooser.addChoosableFileFilter(fileFilter);

        int userSelection = fileChooser.showSaveDialog(window);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File fileToSave = fileChooser.getSelectedFile();
        String filePath = fileToSave.getAbsolutePath();

        ReportReceiver reportLogger = new FileReportLogger(filePath);
        reportLogger.accept(logReporters);

        JOptionPane.showMessageDialog(window,
                "Report saved to " + filePath,
                "Reports saved",
                JOptionPane.INFORMATION_MESSAGE);
    }
}

