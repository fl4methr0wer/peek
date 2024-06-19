package pl.lodz.uni.core;

import javax.swing.*;
import java.util.Collection;
import java.util.List;

public class UpdateController {

    private final SystemUsageService service;

    public UpdateController(SystemUsageService service) {
        this.service = service;
    }

    public void notifyCPUUsage(int intervalMilliseconds, ProgressPresenter presenter) {
        presenter.setName("CPU");
        Timer timer = new Timer(intervalMilliseconds, e -> {
            double percents = service.getProcessorUsageInPercents();
            String percentsStringRepresentation = String.format("%.2f %%", percents);
            presenter.setValue(percentsStringRepresentation);
            presenter.setProgress((int) percents);
        });
        timer.start();
    }

    public void notifyCPUTemperature(int intervalMilliseconds, ProgressPresenter presenter) {
        presenter.setName("CPU temperature");
        Timer timer = new Timer(intervalMilliseconds, e -> {
            double temp = service.getProcessorTemp();
            presenter.setValue(String.format("%.2f C", temp));
            presenter.setProgress((int) temp);
        });
        timer.start();
    }

    public void notifyMemoryUsage(int intervalMilliseconds, ProgressPresenter presenter) {
        presenter.setName("RAM");
        Timer timer = new Timer(intervalMilliseconds, e -> {
            double percents = service.getRamUsageInPercents();
            String percentsStringRepresentation = String.format("%.2f %%", percents);
            presenter.setValue(percentsStringRepresentation);
            presenter.setProgress((int) percents);
        });
        timer.start();
    }

    public void notifyFanRPM(int intervalMilliseconds, List<ProgressPresenter> presenters) {
        int maxFanRPM = 6000;
        Timer timer = new Timer(intervalMilliseconds, e -> {
            int[] fansSpeeds = service.getFansSpeedInRPM();
            for (int i = 0; i < fansSpeeds.length && i < presenters.size(); i++) {
                ProgressPresenter presenter = presenters.get(i);
                int progress = (int) ((fansSpeeds[i] / (double) maxFanRPM) * 100); // Scale to 0-100
                presenter.setProgress(progress);
                presenter.setValue(String.format("%d RPM", fansSpeeds[i]));
            }
        });
        timer.start();
    }


}
