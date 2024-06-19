package pl.lodz.uni.core.controller;

import pl.lodz.uni.core.ProgressPresenter;
import pl.lodz.uni.core.service.IBatteryService;

public class BatteryUpdateController implements Notifier {

    private final IBatteryService batteryService;
    private ProgressPresenter presenter;

    public BatteryUpdateController(IBatteryService batteryService) {
        this.batteryService = batteryService;
    }

    @Override
    public void registerProgressPresenter(ProgressPresenter progressPresenter) {
        this.presenter = progressPresenter;
    }

    @Override
    public void notifyPresenter() {
        if (presenter == null)
            return;
        double chargePercents = batteryService.getRemainingCapacityPercent();
        double health = batteryService.getHealthPercent();
        String label = String.format("%.0f %%    health %.0f %%", chargePercents, health); // Corrected label format
        presenter.setProgress((int) chargePercents);
        presenter.setValue(label);
    }


}
