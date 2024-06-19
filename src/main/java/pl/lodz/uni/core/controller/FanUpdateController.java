package pl.lodz.uni.core.controller;

import pl.lodz.uni.core.ProgressPresenter;
import pl.lodz.uni.core.service.IFanService;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class FanUpdateController implements Notifier {

    private final IFanService fanService;
    private final List<ProgressPresenter> presenters = new ArrayList<>();
    private final int MAX_FAN_RPM = 6000;

    public FanUpdateController(IFanService fanService) {
        this.fanService = fanService;
    }

    @Override
    public void registerProgressPresenter(ProgressPresenter progressPresenter) {
        presenters.add(progressPresenter);
    }

    @Override
    public void notifyPresenter() {
        int[] fansSpeeds = fanService.getFansSpeedInRPM();
        int maxIdx = Math.min(fansSpeeds.length, presenters.size());
        for (int i = 0; i < fansSpeeds.length && i < maxIdx; i++) {
            ProgressPresenter presenter = presenters.get(i);
            int progress = (int) ((fansSpeeds[i] / (double) MAX_FAN_RPM) * 100); // range of 0-100
            presenter.setProgress(progress);
            presenter.setValue(String.format("%d RPM", fansSpeeds[i]));
        }
    }
}
