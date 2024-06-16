package pl.lodz.uni.core;

import javax.swing.*;

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
}
