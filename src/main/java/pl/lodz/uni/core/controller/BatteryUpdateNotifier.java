package pl.lodz.uni.core.controller;

import pl.lodz.uni.core.Presenter;
import pl.lodz.uni.core.service.IBatteryService;

public class BatteryUpdateNotifier implements Notifier {

    private final IBatteryService batteryService;
    private Presenter presenter;

    public BatteryUpdateNotifier(IBatteryService batteryService) {
        this.batteryService = batteryService;
    }

    @Override
    public void registerPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void notifyPresenter() {
        if (presenter == null) return;

        presenter.setName("Battery:");
        double chargePercents = batteryService.getRemainingCapacityPercent();
        double health = batteryService.getHealthPercent();
        String label = String.format("%.0f %%    health %.0f %%", chargePercents, health); // Corrected label format
        presenter.setProgress((int) chargePercents);
        presenter.setValue(label);
    }


}
