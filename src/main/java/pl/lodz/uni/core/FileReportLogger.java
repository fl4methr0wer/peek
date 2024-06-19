package pl.lodz.uni.core;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class FileReportLogger implements ReportLogger {

    private final String filePath;

    public FileReportLogger(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void accept(List<Reporting> reporters) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("System status report");
            writer.newLine();
            writer.write(LocalDateTime.now().toString());
            writer.newLine();
            writer.newLine();
            for (Reporting reporter : reporters) {
                writer.write(reporter.report());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
