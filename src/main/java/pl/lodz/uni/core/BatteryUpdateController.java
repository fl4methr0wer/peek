package pl.lodz.uni.core;

import pl.lodz.uni.core.service.IBatteryService;

import javax.swing.*;

public class BatteryUpdateController {

    private final IBatteryService batteryService;

    public BatteryUpdateController(IBatteryService batteryService) {
        this.batteryService = batteryService;
    }

    public void notify(int intervalMilliseconds, ProgressPresenter presenter) {
        presenter.setName("Power");
        Timer timer = new Timer(intervalMilliseconds, e -> {
            double chargePercents = batteryService.getRemainingCapacityPercent();
            double health = batteryService.getHealthPercent();
            String label = String.format("%.0f %%    health %.0f %%", chargePercents, health); // Corrected label format
            presenter.setProgress((int) chargePercents);
            presenter.setValue(label);
        });
        timer.start();
    }


}
