package pl.lodz.uni.core.controller;

import pl.lodz.uni.core.ProgressPresenter;
import pl.lodz.uni.core.service.IMemoryService;

import javax.swing.*;

public class MemoryUpdateController {

    private final IMemoryService service;

    public MemoryUpdateController(IMemoryService service) {
        this.service = service;
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
