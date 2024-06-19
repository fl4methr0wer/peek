package pl.lodz.uni.core.service;

import oshi.SystemInfo;
import oshi.hardware.PowerSource;
import pl.lodz.uni.core.Reporting;

import java.util.List;

public class BatteryService implements IBatteryService, Reporting {

    private final SystemInfo systemInfo;

    public BatteryService() {
        systemInfo = new SystemInfo();
    }

    @Override
    public double getRemainingCapacityPercent() {
        List<PowerSource> powerSources = systemInfo.getHardware().getPowerSources();
        PowerSource powerSource = powerSources.get(0);
        return powerSource.getRemainingCapacityPercent() * 100;
    }

    @Override
    public int getCycleCount() {
        return systemInfo.getHardware().getPowerSources().get(0).getCycleCount();
    }

    @Override
    public double getHealthPercent() {
        PowerSource battery = systemInfo.getHardware().getPowerSources().get(0);
        int design = battery.getDesignCapacity();
        int currentCapacity = battery.getMaxCapacity();
        return ((double) currentCapacity / (double) design) * 100.0;
    }

    @Override
    public String report() {
        PowerSource battery = systemInfo.getHardware().getPowerSources().get(0);
        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append("Battery Report:\n");
        reportBuilder.append(String.format("Remaining Capacity: %.2f%%\n", getRemainingCapacityPercent()));
        reportBuilder.append(String.format("Health: %.2f%%\n", getHealthPercent()));
        reportBuilder.append(String.format("Factory Capacity: %d mWh\n", battery.getDesignCapacity()));
        reportBuilder.append(String.format("Current Capacity: %d mWh\n", battery.getMaxCapacity()));
        return reportBuilder.toString();
    }
}

