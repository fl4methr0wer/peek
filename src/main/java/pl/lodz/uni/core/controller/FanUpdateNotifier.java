package pl.lodz.uni.core.controller;

import pl.lodz.uni.core.Presenter;
import pl.lodz.uni.core.service.IFanService;

import java.util.ArrayList;
import java.util.List;

public class FanUpdateNotifier implements Notifier {

    private final IFanService fanService;
    private final List<Presenter> presenters = new ArrayList<>();
    private final int MAX_FAN_RPM = 6000;

    public FanUpdateNotifier(IFanService fanService) {
        this.fanService = fanService;
    }

    @Override
    public void registerPresenter(Presenter presenter) {
        presenters.add(presenter);
    }

    @Override
    public void notifyPresenter() {
        if (presenters.isEmpty()) return;

        int[] fansSpeeds = fanService.getFansSpeedInRPM();
        int maxIdx = Math.min(fansSpeeds.length, presenters.size());
        for (int i = 0; i < fansSpeeds.length && i < maxIdx; i++) {
            Presenter presenter = presenters.get(i);
            presenter.setName("Fan " + (i + 1) + ":");

            int progress = (int) ((fansSpeeds[i] / (double) MAX_FAN_RPM) * 100); // range of 0-100
            presenter.setProgress(progress);
            presenter.setValue(String.format("%d RPM", fansSpeeds[i]));
        }
    }
}
