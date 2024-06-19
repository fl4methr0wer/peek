package pl.lodz.uni.core.service;

import oshi.SystemInfo;
import oshi.hardware.PowerSource;

import java.util.List;

public class BatteryService implements IBatteryService {

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
}

