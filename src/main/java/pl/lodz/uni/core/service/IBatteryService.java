package pl.lodz.uni.core.service;

public interface IBatteryService {
    double getRemainingCapacityPercent();
    int getCycleCount();
    double getHealthPercent();
}
