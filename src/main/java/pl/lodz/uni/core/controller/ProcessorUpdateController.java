package pl.lodz.uni.core.controller;

import pl.lodz.uni.core.ProgressPresenter;
import pl.lodz.uni.core.service.IProcessorService;

import javax.swing.*;

public class ProcessorUpdateController {

    private final IProcessorService service;

    public ProcessorUpdateController(IProcessorService service) {
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

}
