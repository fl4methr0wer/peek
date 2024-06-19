package pl.lodz.uni.core;

import pl.lodz.uni.core.service.IFanService;
import pl.lodz.uni.core.service.IMemoryService;
import pl.lodz.uni.core.service.IProcessorService;
import javax.swing.*;
import java.util.List;

public class UpdateController {

    private final IProcessorService processorService;
    private final IFanService IFanService;
    private final IMemoryService IMemoryService;

    public UpdateController(IProcessorService processorService, IFanService IFanService, IMemoryService IMemoryService) {
        this.processorService = processorService;
        this.IFanService = IFanService;
        this.IMemoryService = IMemoryService;
    }

    public void notifyCPUUsage(int intervalMilliseconds, ProgressPresenter presenter) {
        presenter.setName("CPU");
        Timer timer = new Timer(intervalMilliseconds, e -> {
            double percents = processorService.getProcessorUsageInPercents();
            String percentsStringRepresentation = String.format("%.2f %%", percents);
            presenter.setValue(percentsStringRepresentation);
            presenter.setProgress((int) percents);
        });
        timer.start();
    }

    public void notifyCPUTemperature(int intervalMilliseconds, ProgressPresenter presenter) {
        presenter.setName("CPU temperature");
        Timer timer = new Timer(intervalMilliseconds, e -> {
            double temp = processorService.getProcessorTemp();
            presenter.setValue(String.format("%.2f C", temp));
            presenter.setProgress((int) temp);
        });
        timer.start();
    }

    public void notifyMemoryUsage(int intervalMilliseconds, ProgressPresenter presenter) {
        presenter.setName("RAM");
        Timer timer = new Timer(intervalMilliseconds, e -> {
            double percents = IMemoryService.getRamUsageInPercents();
            String percentsStringRepresentation = String.format("%.2f %%", percents);
            presenter.setValue(percentsStringRepresentation);
            presenter.setProgress((int) percents);
        });
        timer.start();
    }

    public void notifyFanRPM(int intervalMilliseconds, List<ProgressPresenter> presenters) {
        int maxFanRPM = 6000;
        Timer timer = new Timer(intervalMilliseconds, e -> {
            int[] fansSpeeds = IFanService.getFansSpeedInRPM();
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
