package pl.lodz.uni.core.controller;

import pl.lodz.uni.core.ProgressPresenter;
import pl.lodz.uni.core.service.IFanService;

import javax.swing.*;
import java.util.List;

public class FanUpdateController {

    private final IFanService IFanService;

    public FanUpdateController(IFanService IFanService) {
        this.IFanService = IFanService;
    }


    public void notifyFanRPM(int intervalMilliseconds, List<ProgressPresenter> presenters) {
        int maxFanRPM = 6000;
        Timer timer = new Timer(intervalMilliseconds, e -> {
            int[] fansSpeeds = IFanService.getFansSpeedInRPM();
            for (int i = 0; i < fansSpeeds.length && i < presenters.size(); i++) {
                presenters.get(i).setName("Fan " + (i + 1));
                ProgressPresenter presenter = presenters.get(i);
                int progress = (int) ((fansSpeeds[i] / (double) maxFanRPM) * 100); // Scale to 0-100
                presenter.setProgress(progress);
                presenter.setValue(String.format("%d RPM", fansSpeeds[i]));
            }
        });
        timer.start();
    }
}
